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

/**
 * @author surendra.kumar
 *
 */
public interface SearchCriteriaMapper {
  
  /**
   * Map property.
   *
   * @param criteria the criteria
   * @return the string
   */
  String searchByMapProperty(String criteria);
  
  /**
   * 
   * @param criteria
   * @return
   */
  String sortMapProperty(String criteria);
  
}
