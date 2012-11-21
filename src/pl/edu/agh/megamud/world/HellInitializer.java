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
	public static Location iw1, iw2, iw3;
	public static Location 
		A3,
		B3, B4,
		C4,
		D1, D4, D5, D6,
		E1, E2, E3, E4, E6,
		F3,
		G3;
							
	
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
		initMainCorridor();
		initWestDeadEnd();
		
	}

	public static void initInfernalWell() throws SQLException {
		iw1 = prepareLocation(
				"infernalWell1",
				"You start climbing down the hole. It is very dangerous. You start having doubts, but it's impossible " +
				"climb up.");
		iw2 = prepareLocation(
				"infernalWell2",
				"You keep descending down the hole. Suddenly you trip and start falling... You fear you may die.");
		iw3 = prepareLocation(
				"infernalWell3",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");

		CaveInitializer.F1.connectOneWay(iw1, "down");
		iw1.connectOneWay(iw2, "down");
		iw2.connectOneWay(iw3, "down");
	}
	
	public static void initMainCorridor() throws SQLException{
		G3 = prepareLocation(
				"iG3",
				"You start climbing down the hole. It is very dangerous. You start having doubts, but it's impossible " +
				"climb up.");
		F3 = prepareLocation(
				"iF3",
				"You keep descending down the hole. Suddenly you trip and start falling... You fear you may die.");
		E3 = prepareLocation(
				"iE3",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		E4 = prepareLocation(
				"iE4",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		D4 = prepareLocation(
				"iD4",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		C4 = prepareLocation(
				"iC4",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		B4 = prepareLocation(
				"iB4",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		B3 = prepareLocation(
				"iB3",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		A3 = prepareLocation(
				"iA3",
				"You keep falling into the infernal abbyss... You prepare for imminent death.");
		
		iw3.connectOneWay(G3, "down");
		
		G3.connectTwoWay(F3, "north", "south");
		F3.connectTwoWay(E3, "north", "south");
		E3.connectTwoWay(E4, "east", "west");
		E4.connectTwoWay(D4, "north", "south");
		D4.connectTwoWay(C4, "north", "south");
		C4.connectTwoWay(B4, "north", "south");
		B4.connectTwoWay(B3, "west", "east");
		B3.connectTwoWay(A3, "portal", "portal");
	}
	
	public static void initWestDeadEnd() throws SQLException{
		E2 = prepareLocation(
				"iE2",
				"You start climbing down the hole. It is very dangerous. You start having doubts, but it's impossible " +
				"climb up.");		
		E1 = prepareLocation(
				"iE1",
				"You start climbing down the hole. It is very dangerous. You start having doubts, but it's impossible " +
				"climb up.");
		D1 = prepareLocation(
				"iD1",
				"You start climbing down the hole. It is very dangerous. You start having doubts, but it's impossible " +
				"climb up.");
		
		E3.connectTwoWay(E2, "west", "east");
		E2.connectTwoWay(E1, "west", "east");
		E1.connectTwoWay(D1, "north", "south");
	}
}
