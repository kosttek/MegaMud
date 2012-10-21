package pl.edu.agh.megamud.mockdata;

import java.util.ArrayList;

import pl.edu.agh.megamud.base.Location;

public class MockLocations1 {
	public static ArrayList<Location> createLocations(){
		Location loc1 = new Location("pokoj 1");
		Location loc2 = new Location("pokoj 2");
		Location loc3 = new Location("pokoj 3");
		
		loc1.addExit("drzwi", loc2);
		loc2.addExit("drzwi", loc1);
		loc2.addExit("schody", loc3);
		loc3.addExit("schody", loc2);
		
		ArrayList<Location> result= new ArrayList<Location>();
		result.add(loc1);
		result.add(loc2);
		result.add(loc3);
		
		return result;
	}
}
