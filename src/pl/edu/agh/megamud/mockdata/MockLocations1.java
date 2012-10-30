package pl.edu.agh.megamud.mockdata;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.SimpleItem;

public class MockLocations1 {
	public static List<Location> createLocations(){
		Location loc1 = new Location("pokoj 1");
		Location loc2 = new Location("pokoj 2");
		Location loc3 = new Location("pokoj 3");
		
		loc1.addExit("drzwi", loc2);
		loc2.addExit("drzwi", loc1);
		loc2.addExit("schody", loc3);
		loc3.addExit("schody", loc2);
		
		List<Location> result= new LinkedList<Location>();
		result.add(loc1);
		result.add(loc2);
		result.add(loc3);
		
		new CyclicBehaviour(loc2,1000L){
			protected void action() {
				Location c=(Location)owner;
				
				if(c.getItems().containsKey("apple"))
					return;
				
				SimpleItem it=new SimpleItem("apple","Precious, golden apple.");
				it.giveTo(c);
			}
		}.init();
		
		return result;
	}
}
