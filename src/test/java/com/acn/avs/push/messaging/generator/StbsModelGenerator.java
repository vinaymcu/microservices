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

import com.acn.avs.push.messaging.model.Stb;
import com.acn.avs.push.messaging.model.Stbs;

/**
 * Entity Object generator for HardwareVersion
 * 
 * @author Singh.Saurabh
 *
 */
public class StbsModelGenerator extends AbstractEntityGenerator<Stbs> {

	@Override
	public Class<Stbs> getEntityClass() {
		return Stbs.class;
	}

	@Override
	protected void buildLists() {
		RandomData randomData = new RandomData();
		
		// 0
		withRandomValues(randomData);
	}

	/**
	 * HardwareVersion with random values
	 * 
	 * @param randomData
	 */
	private void withRandomValues(RandomData randomData) {
		Stbs stbs=new Stbs();
		Stb stb=new Stb();
		stb.setMACAddress(randomData.randomString(5));
		stbs.getStb().add(stb);
		addValid(stbs);
		
		Stbs stbs2=new Stbs();
		Stb stb2=new Stb();
		stbs2.getStb().add(stb2);
		addInvalid(stbs2);
	}

	

}
