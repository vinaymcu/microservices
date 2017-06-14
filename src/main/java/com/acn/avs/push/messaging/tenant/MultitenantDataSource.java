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

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * MultitenantDataSource {@link javax.sql.DataSource} implementation that routes
 * {@link #getConnection()} calls to one of various target DataSources based on a lookup key. The
 * latter is usually (but not necessarily) determined through some thread-bound transaction context.
 *
 * @author Sumit.Sharma
 */
public class MultitenantDataSource extends AbstractRoutingDataSource {
  
  /**
   * Determine the current lookup key. This will typically be implemented to check a thread-bound
   * transaction context.
   * <p>
   * Allows for arbitrary keys. The returned key needs to match the stored lookup key type, as
   * resolved by the {@link #resolveSpecifiedLookupKey} method.
   *
   * @return the object
   */
  @Override
  protected Object determineCurrentLookupKey() {
    return TenantContext.getCurrentTenant();
  }
}
