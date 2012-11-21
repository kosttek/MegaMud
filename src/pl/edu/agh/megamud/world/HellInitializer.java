/*******************************************************************************
 * Copyright (c) 2012, AGH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package pl.edu.agh.megamud.world;

import java.sql.SQLException;

import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.base.LocationBase;

public class HellInitializer {

	static private String moduleName;
	public static Location hw1, hw2, hw3;

	private static String getId() {
		return moduleName;
	}

	private static Location prepareLocation(String name, String desc)
			throws SQLException {
		Location loc1 = new Location();

		loc1.setName(name).setDescription(desc).setModule(getId());

		LocationBase.createDao().create(loc1);

		return loc1;
	}

	public static void init(String moduleName) throws SQLException {
		HellInitializer.moduleName = moduleName;

		initInfernalWell();
	}

	public static void initInfernalWell() throws SQLException {
		hw1 = prepareLocation(
				"infernalWell1",
				"You find yourself in a huge cave. Air here is suffocating and you can barely see anything. "
						+ "It looks like you woke up here.");
		hw2 = prepareLocation("infernalWell1",
				"The stone under your feet is very slippery.");
		hw3 = prepareLocation("infernalWell1",
				"It looks like the corridor is rising towards the east.");

		
	}
}
