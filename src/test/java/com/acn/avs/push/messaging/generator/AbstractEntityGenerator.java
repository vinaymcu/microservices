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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.acn.avs.push.messaging.generator.EntityGenerator; 


/**
 * Basic abstract implementation of the EntityGenerator interface. Extend this class and build the
 * lists of valid, invalid and depedency entities.
 * @author Happy.Dhingra
 *
 * @param <T>
 */
public abstract class AbstractEntityGenerator<T> implements EntityGenerator<T> {

	/** valid. */
	private List<T> valid;

	/** invalid. */
	private List<T> invalid;

	/** dependencies. */
	private List<Object> dependencies;

	/**
	 * Creates a new AbstractEntityGenerator object.
	 */
	public AbstractEntityGenerator() {

		reset();
	}

	/* (non-Javadoc)
	 * @see com.acn.avs.stb.Generator.EntityGenerator#getEntityClass()
	 */
	public abstract Class<T> getEntityClass();

	/**
 * Resets all lists.
 */
	public void reset() {
		valid = new ArrayList<T>();
		invalid = new ArrayList<T>();
		dependencies = new ArrayList<Object>();
		buildLists();
	}

	/**
	 * Implement this method to build lists of valid and invalid objects as well
	 * as dependencies using addValid(), addInvalid() and addDepedencies().
	 */
	protected abstract void buildLists();

	/**
 * Builds a list of valid objects.
 */
	protected void buildValid() {

	}

	/**
 * Builds a list of valid objects.
 */
	protected void buildInvalid() {

	}

	/**
 * Adds an object to the list of valid objects.
 *
 * @param obj the obj
 */
	protected void addValid(T obj) {

		valid.add(obj);
	}

	/**
 * Returns a list containing valid objects.
 *
 * @return valid
 */
	public List<T> getValid() {
		if (valid == null || valid.size() == 0)
			buildValid();

		return valid;
	}

	/**
 * Adds an object to the list of invalid objects.
 *
 * @param obj the obj
 */
	protected void addInvalid(T obj) {

		invalid.add(obj);
	}

	/**
 * Returns a list containing invalid objects.
 *
 * @return invalid
 */
	public List<T> getInvalid() {
		if (invalid == null || invalid.size() == 0)
			buildInvalid();

		return invalid;
	}

	/**
 * Adds an object to the list of depedencies.
 *
 * @param obj the obj
 */
	protected void addDependency(Object obj) {

		if (obj instanceof Collection<?>) {
			Collection<?> col = (Collection<?>) obj;

			for (Object item : col) {
				dependencies.add(item);
			}
		} else {
			dependencies.add(obj);
		}

	}

	/**
 * Returns a list containing dependencies.
 *
 * @return dependencies
 */
	public List<Object> getDependencies() {
		return dependencies;
	}

	/**
 * Gets the dynamic PK id.
 *
 * @param precision the precision
 * @return the dynamic PK id
 */
	public Long getDynamicPKId(int precision) {
		RandomData rd = new RandomData();
		return rd.randomLong(precision);
	}
} // end class AbstractEntityGenerator
