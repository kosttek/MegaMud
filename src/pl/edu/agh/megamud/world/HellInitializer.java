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
