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
package com.acn.avs.push.messaging.tenant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

/**
 * Enable support for Multitenant DataSource.
 *
 * @author Sumit.Sharma
 * @since 1.0
 *
 */
@Configuration
@EnableConfigurationProperties
public class MultitenantDataSourceConfiguration {

  /** The readonly. */
  @Value(value = "${pushmessaging.multitenancy.read-only:false}")
  private boolean readonly;

  /** The application context. */
  @Autowired
  private ApplicationContext applicationContext;

  /** The resolved data sources. */
  private final Map<Object, Object> resolvedDataSources = new ConcurrentHashMap<>();

  /**
   * Checks if is tenant enabled.
   *
   * @param tenant the tenant
   * @return true, if is tenant enabled
   */
  public boolean isTenantEnabled(final Object tenant) {
    return resolvedDataSources.containsKey(tenant);
  }

  /**
   * Multitenancy datasource bean initialization .
   *
   * @return the data source
   */
  @Bean
  @Primary
  @DependsOn(value = { "readDataSource", "writeDataSource" })
  public DataSource multitenantDataSource() {
    if (!readonly) {
      resolvedDataSources.put(Tenants.WRITE, applicationContext.getBean("writeDataSource"));
    }
    resolvedDataSources.put(Tenants.READ, applicationContext.getBean("readDataSource"));
    final MultitenantDataSource dataSource = new MultitenantDataSource();
    dataSource.setDefaultTargetDataSource(resolvedDataSources.get(Tenants.READ));
    dataSource.setTargetDataSources(resolvedDataSources);
    return dataSource;
  }

  /**
   * Read only datasource bean initialization. Make sure Read only data source configuration
   * properties is added in .yml/.properties file with following hierarchy
   * <ul>
   * <li>spring:
   * <ul>
   * <li>multitenancy:
   * <ul>
   * <li>read
   * <ul>
   * <li>url: db url</li>
   * <li>username: username</li>
   * <li>password: ****</li>
   * <li>driver-class-name: com.xxx.xxx.xDriver</li>
   * <li>Other Pool Properties</li>
   * </ul>
   * </li>
   * </ul>
   * </li>
   * </ul>
   * </li>
   *
   * @return the Read only datasource
   */
  @Bean(name = "readDataSource")
  @ConfigurationProperties(prefix = "pushmessaging.multitenancy.read")
  public DataSource readDataSource() {
    return DataSourceBuilder.create().build();
  }

  /**
   * Read/Write datasource bean initialization. Make sure R/W data source configuration properties
   * is added in .yml/.properties file with following hierarchy
   * <ul>
   * <li>spring:
   * <ul>
   * <li>multitenancy:
   * <ul>
   * <li>write
   * <ul>
   * <li>url: db url</li>
   * <li>username: username</li>
   * <li>password: ****</li>
   * <li>driver-class-name: com.xxx.xxx.xDriver</li>
   * <li>Other Pool Properties</li>
   * </ul>
   * </li>
   * </ul>
   * </li>
   * </ul>
   * </li>
   *
   * @return the Read/Write datasource
   */

  @Bean(name = "writeDataSource")
  @ConditionalOnProperty(value = "pushmessaging.multitenancy.read-only", havingValue = "false")
  @ConfigurationProperties(prefix = "pushmessaging.multitenancy.write")
  public DataSource writeDataSource() {
    return DataSourceBuilder.create().build();
  }

}
