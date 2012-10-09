package pl.edu.agh.megamud.mockdata;

import java.util.ArrayList;

import pl.edu.agh.megamud.base.Location;

public class MockLocations1 {
	public static ArrayList<Location> createLocations(){
		Location loc1 = new Location();
		Location loc2 = new Location();
		Location loc3 = new Location();
		
		loc1.setDescription("pokoj 1");
		loc2.setDescription("pokoj 2");
		loc3.setDescription("pokoj 3");
		
		loc1.locations.put("drzwi", loc2);
		loc2.locations.put("drzwi", loc1);
		loc2.locations.put("schody", loc3);
		loc3.locations.put("schody", loc2);
		
		ArrayList<Location> result= new ArrayList<Location>();
		result.add(loc1);
		result.add(loc2);
		result.add(loc3);
		
		return result;
	}
}
