package pl.edu.agh.megamud.world;

import java.sql.SQLException;

import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.Portal;
import pl.edu.agh.megamud.dao.base.LocationBase;
import pl.edu.agh.megamud.dao.base.PortalBase;

public class CaveInitializer {
	
	static private String moduleName;
	static public Location A1,
							B1, B2, B3,
							C3, C5, C6, C7,
							D1, D2, D3, D4, D5, D7,
							E1, E5, E6, E7,
							F1;
	
	private static String getId(){ return moduleName; }
	
	private static Location prepareLocation(String name,String desc) throws SQLException{
		Location loc1 = new Location();
		
		loc1.setName(name)
			.setDescription(desc)
			.setModule(getId());
		
		LocationBase.createDao().create(loc1);
		
		return loc1;
	}
	
	private static void preparePortal(String from,String to,String name) throws SQLException{
		Location loc1 = LocationBase.createDao()
				.queryBuilder()
					.where().eq("name", from)
					.and().eq("module", getId())
				.query().
				get(0);
		Location loc2 = LocationBase.createDao()
				.queryBuilder()
					.where().eq("name", to)
					.and().eq("module", getId())
				.query()
				.get(0);
		
		Portal p = new Portal();
		p.setDestination(loc2);
		p.setEntry(loc1);
		p.setName(name);
		PortalBase.createDao().create(p);
	}	
	
	public static void init(String moduleName) throws SQLException{
		CaveInitializer.moduleName = moduleName;
		
		initWesternCorridor();
	}
	
	public static void initWesternCorridor() throws SQLException{
		A1 = prepareLocation("start",
				"You find yourself in a huge cave. Air here is suffocating and you can barely see anything. " +
				"It looks like you woke up here.");
		B1 = prepareLocation("b1","The stone under your feet is very slippery.");
		B2 = prepareLocation("b2","It looks like the corridor is rising towards the east.");
		B3 = prepareLocation("b3","In this small cave there is a small campfire in the middle.");
		C3 = prepareLocation("c3", "It is so dark here, you think you are blind.");
		D3 = prepareLocation("d3", "This small cave is where three narrow passages meet.");
		D2 = prepareLocation("d2", "You have to crawl to keep moving. You are afraid you may get stuck.");
		D1 = prepareLocation("d1", 
				"The tunnel turns slightly here. It's small and you have to watch out not to hit yourself on the head.");
		E1 = prepareLocation("e1", "This part of the cave is relatively big. You can hear water dripping.");
		F1 = prepareLocation("f1", 
				"This is a small room, with a hole in the middle. Oddly enough there is a hole in the middle with" +
				"a sinister glow coming from within. If you decide to descend, you won't be able to return.");
		
		A1.connectTwoWay(B1, "south", "north");
		B1.connectTwoWay(B2, "east", "west");
		B2.connectTwoWay(B3, "east", "west");
		B3.connectTwoWay(C3, "south", "north");
		C3.connectTwoWay(D3, "south", "north");
		D3.connectTwoWay(D2, "west", "east");
		D2.connectTwoWay(D1, "west", "east");
		D1.connectTwoWay(E1, "south", "north");
		E1.connectTwoWay(F1, "south", "north");
	}
}
