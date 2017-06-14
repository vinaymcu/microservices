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

package com.acn.avs.push.messaging.generator;

import java.util.List;

/**
 * This interface is the preferred way to supply tests with valid invalid and dependent entities.
 * Implement this interface or use extend AbstractEntityGenerator for use with GeneratedJpaTestCase
 *
 * @author singh.saurabh
 * @version 1.0
 * @param <T> the generic type
 * @date June 3, 2014
 */

public interface EntityGenerator<T> {

	/**
	 * Returns the Entity class used by this EntityGenerator.
	 * 
	 * @return String
	 */
	Class<T> getEntityClass();

	/**
	 * Returns a list of valid entities.
	 * 
	 * @return List
	 */
	List<T> getValid();

	/**
	 * Returns a list of invalid entities.
	 * 
	 * @return List
	 */
	List<T> getInvalid();

	/**
	 * Returns a list of dependent entities.
	 * 
	 * @return List
	 */
	List<Object> getDependencies();

	/**
	 * Reset the state of the generator.
	 */
	void reset();

} // end interface EntityGenerator
