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

import org.slf4j.Logger; 
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;


@Component
public class TenantContext implements ApplicationContextAware {
  
  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(TenantContext.class);
  
  /** The current tenant. */
  private static ThreadLocal<Object> currentTenant = new ThreadLocal<>();
  
  /** The multitenant data source configuration. */
  private static MultitenantDataSourceConfiguration multitenantDataSourceConfiguration;
  
  /**
   * Gets the tenant lookup key from thread-bound context.
   *
   *
   * @return the current tenant lookup key, <code> Tenants.READ </code> if null.
   */
  public static Object getCurrentTenant() {
    final Object tenant = currentTenant.get() == null ? Tenants.READ : currentTenant.get(); 
    if(LOGGER.isDebugEnabled()){
    	LOGGER.debug("Getting current for lookup key i.e  [" + tenant + "] ");
    }
    return tenant;
  }
  
  /**
   * Sets the tenant lookup key to thread-bound context.
   *
   * @param tenant the new current tenant
   */
  
  public static void setCurrentTenant(final Object tenant) {
    if (!multitenantDataSourceConfiguration.isTenantEnabled(tenant)) {
      throw new IllegalStateException("Tenant [] not configured.");
    }
    if(LOGGER.isDebugEnabled()){
    	LOGGER.debug("Setting current tenant lookup key i.e  [" + tenant + "] ");
    }
    currentTenant.set(tenant);
  }
  
  /**
   * Sets the application context.
   *
   * @param applicationContext the new application context
   */
  /*
   * (non-Javadoc)
   *
   * @see
   * org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework
   * .context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(final ApplicationContext applicationContext) {
    TenantContext.multitenantDataSourceConfiguration =
        applicationContext.getBean(MultitenantDataSourceConfiguration.class);
  }
}
