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
package com.acn.avs.push.messaging.config;

import java.io.FileNotFoundException;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.ResourceUtils;

 /***
 * PushMessagingApplication 
 *
 */
@EnableTransactionManagement
@EntityScan(basePackages = { "com.acn.avs.push.messaging.entity" })
@EnableJpaRepositories(basePackages = { "com.acn.avs.push.messaging.repository" })
@SpringBootApplication(scanBasePackages = { "com.acn.avs.push.messaging" })
@EnableFeignClients(basePackages = { "com.acn.avs.push.messaging.client" })
public class PushMessagingApplication {
	
	/** LOGGER */
	private static final Logger LOGGER = Logger.getLogger(PushMessagingApplication.class);

	/** ConfigurationProperties instance */
	@Autowired
	ConfigurationProperties config;

	/**
	 * messageSource getting ReloadableResourceBundleMessageSource
	 * @return messageSource
	 */
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("classpath:messages/message");
		return messageSource;
	}

	/**
	 * messageSourceAccessor
	 * @return
	 */
	@Bean
	public MessageSourceAccessor messageSourceAccessor() {
		return new MessageSourceAccessor(messageSource(), Locale.ENGLISH);
	}
    
	/** 
	 * main method of PushMessagingApplication
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(PushMessagingApplication.class, args);
	}

	@PostConstruct
	public void init() {
		LOGGER.info("Initializing PushMessagingApplication  .. ");

	}
	
	/**
	 * Adds SSL connector in Tomcat embedded Servlet connector factory.
	 *
	 * @return EmbeddedServletContainerFactory
	 * @throws FileNotFoundException
	 */
	@Bean
	@ConditionalOnProperty(name = "ssl.enabled", havingValue = "true")
	public EmbeddedServletContainerFactory servletContainer() throws FileNotFoundException {
		TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = new TomcatEmbeddedServletContainerFactory();
		tomcatEmbeddedServletContainerFactory.addAdditionalTomcatConnectors(createSslConnector());
		return tomcatEmbeddedServletContainerFactory;
	}
	
	/**
	 * creates SSL connector.
	 *
	 * @return Connector
	 * @throws FileNotFoundException
	 */
	private Connector createSslConnector() throws FileNotFoundException {
		Connector connector = new Connector(Http11NioProtocol.class.getName());
		Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();
		connector.setPort(config.getSslPort());
		connector.setSecure(true);
		connector.setScheme("https");
		protocol.setSSLEnabled(true);
		protocol.setKeyAlias(config.getSslKeystoreAlias());
		protocol.setKeystorePass(config.getSslKeystorePassword());
		protocol.setKeystoreFile(ResourceUtils.getFile(config.getSslKeystoreFilePath()).getAbsolutePath());
		protocol.setSslProtocol(config.getSslProtocol());
		LOGGER.info("SSLConnector bind with sslPort: " + config.getSslPort() + ", keystoreAlias: " + config.getSslKeystoreAlias()
				+ ", sslProtocol: " + config.getSslProtocol() + ", keystoreFilePath: " + config.getSslKeystoreFilePath());
		return connector;
	}
}
