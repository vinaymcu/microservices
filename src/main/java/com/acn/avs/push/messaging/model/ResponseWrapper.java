
package com.acn.avs.push.messaging.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseWrapper {

	/**
	 * It will be valorized only in case of generic Error, that is only in case
	 * the MS will not able to execute the requested interface, for example in
	 * case of the DataBase is down(ACN_300).
	 *
	 */
	private String resultCode;
	/**
	 * Description Error. Example: 300-GENERIC ERROR. In the other case it will
	 * be not present.
	 *
	 */
	private String resultDescription;

	private List<ResultObject> resultObject = new ArrayList<ResultObject>();
	/**
	 * GM Time in Milliseconds
	 *
	 */
	private Long systemTime;

	/**
	 * It will be valorized only in case of generic Error, that is only in case
	 * the MS will not able to execute the requested interface, for example in
	 * case of the DataBase is down(ACN_300).
	 *
	 * @return The resultCode
	 */
	public String getResultCode() {
		return resultCode;
	}

	/**
	 * It will be valorized only in case of generic Error, that is only in case
	 * the MS will not able to execute the requested interface, for example in
	 * case of the DataBase is down(ACN_300).
	 *
	 * @param resultCode
	 *            The resultCode
	 */
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	/**
	 * Description Error. Example: 300-GENERIC ERROR. In the other case it will
	 * be not present.
	 *
	 * @return The resultDescription
	 */
	public String getResultDescription() {
		return resultDescription;
	}

	/**
	 * Description Error. Example: 300-GENERIC ERROR. In the other case it will
	 * be not present.
	 *
	 * @param resultDescription
	 *            The resultDescription
	 */
	public void setResultDescription(String resultDescription) {
		this.resultDescription = resultDescription;
	}

	/**
	 *
	 * @return The resultObject
	 */
	public List<ResultObject> getResultObject() {
		return resultObject;
	}

	/**
	 *
	 * @param resultObject
	 *            The resultObject
	 */
	public void setResultObject(List<ResultObject> resultObject) {
		this.resultObject = resultObject;
	}

	/**
	 * GM Time in Milliseconds
	 *
	 * @return The systemTime
	 */
	public Long getSystemTime() {
		return systemTime;
	}

	/**
	 * GM Time in Milliseconds
	 *
	 * @param systemTime
	 *            The systemTime
	 */
	public void setSystemTime(Long systemTime) {
		this.systemTime = systemTime;
	}

	public static ResponseWrapper error(String errorCode, String message) {
		ResponseWrapper response = new ResponseWrapper();
		response.resultCode = errorCode;
		response.resultDescription = message;
		response.systemTime = System.currentTimeMillis();
		return response;
	}

}
