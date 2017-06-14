/***************************************************************************
 * Copyright (C) Accenture
 *
 * The reproduction, transmission or use of this document or its contents is not permitted without
 * prior express written consent of Accenture. Offenders will be liable for damages. All rights,
 * including but not limited to rights created by patent grant or registration of a utility model or
 * design, are reserved.
 *
 * Accenture reserves the right to modify technical specifications and features.
 *
 * Technical specifications and features are binding only insofar as they are specifically and
 * expressly agreed upon in a written contract.
 *
 **************************************************************************/
package com.acn.avs.push.messaging.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/***
 * This class validates JSON request against JSON Schema. During validation read
 * JSON data from request and load JSON schema then compares, if result returns
 * success then JSON request data is valid else not valid.
 * 
 * @author Kumar.Rajesh
 *
 */
@Component
public final class JsonRequestValidator {

	/** LOGGER */
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonRequestValidator.class);

	/** schemaMap */
	private Map<String, Schema> schemaMap;

	@Value("${server.port:-1}")
	private String port;

	/**
	 * This method validates (as object, integer, size, required, enum etc.)
	 * JSON request data against JSON schema. If JSON request data is invalid
	 * then throws validationException.
	 * 
	 * @param jsonObject
	 * @param jsonFileName
	 * @param schemaFolderName
	 *            return List
	 * @return
	 */
	public <T> String validate(T jsonObject, String jsonFileName, String schemaFolderName) {
		String rval = "";
		try {
			String jsonData = getRequestBody(jsonObject);
			Schema schema = getSchema(jsonFileName, schemaFolderName);
			LOGGER.debug("schema = {}", schema);
			schema.validate(new JSONObject(jsonData));
		} catch (IOException io) {
			LOGGER.error("IOException = {} " + io);
			rval = io.getMessage();
		} catch (ValidationException validationException) {
			rval = validationException.toString();
			LOGGER.error("Json schema violation = {} " + validationException.toJSON());
			LOGGER.error("Validation Exception = {} " + validationException);
		}
		return rval;
	}

	/**
	 * Gets JSON data from requested JSON object.
	 * 
	 * @param eventUpdateRequest
	 * @return
	 */
	private <T> String getRequestBody(T jsonObject) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		String jsonData = "";
		try {
			jsonData = mapper.writeValueAsString(jsonObject);
		} catch (JsonGenerationException exception) {
			throw exception;
		} catch (JsonMappingException exception) {
			throw exception;
		} catch (IOException exception) {
			throw exception;
		}
		LOGGER.debug("Requested json data {} ", jsonData);
		return jsonData;
	}

	/**
	 * Gets schema from collection. If not exist then read schema and put in
	 * collection then return.
	 * 
	 * @param jsonFile
	 * @param schemaFolderName
	 * @return
	 * @throws IOException
	 */
	private Schema getSchema(String jsonFile, String schemaFolderName) throws IOException {
		Schema schema = null;
		if(schemaMap == null) {
			schemaMap = new ConcurrentHashMap<String, Schema>();
		}
		schema = schemaMap.get(jsonFile);
		if (schema == null) {
			schema = loadJsonSchema(schemaFolderName, jsonFile);
			schemaMap.put(jsonFile, schema);
		}
		return schema;
	}

	/**
	 * @param jsonFileName
	 * @return
	 * @throws IOException
	 */
	private Schema loadJsonSchema(String folderName, String jsonFileName) throws IOException {
		Schema schema = null;
		String dirWithFile = folderName + jsonFileName;
		/** For test case it is -1 */
		if ("-1".equals(port)) {
			schema = SchemaLoader.builder().resolutionScope(new ClassPathResource(folderName).getURI())
					.schemaJson(new JSONObject(
							new JSONTokener(JsonRequestValidator.class.getResourceAsStream(dirWithFile))))
					.build().load().build();
		} else {
			String url = "http://localhost:" + port + "/schema/";
			schema = SchemaLoader.builder().resolutionScope(url)
					.schemaJson(new JSONObject(
							new JSONTokener(JsonRequestValidator.class.getResourceAsStream(dirWithFile))))
					.build().load().build();
		}
		return schema;
	}

	/**
	 * Reads JSON data from InputStream and convert into String then return it.
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public String getStringFromInputStream(InputStream is) throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException exception) {
			
			throw exception;
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException exception) {
					LOGGER.error("IOException",exception);
					// Ignore
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Converts String to InputStream and return it.
	 * 
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public InputStream stringToStream(String str) throws UnsupportedEncodingException {
		return new ByteArrayInputStream(str.getBytes("UTF-8"));
	}
}
