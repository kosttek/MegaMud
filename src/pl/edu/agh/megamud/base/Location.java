package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashMap;

public class Location {
	//TODO do zmiany na getery setery
	public HashMap<String,Location> locations = new HashMap<String,Location>();
	public ArrayList<Creature> creatures = new ArrayList<Creature>();
	private String description;
	
	
	public ArrayList<User> getUsersInThisLocation(){
		ArrayList<User> users = new ArrayList<User>();
		for(Creature cr : creatures){
			if(cr.parent != null)
				users.add(cr.parent);
		}
		return users;
	}
	
	public void putCreature(Creature creature){
		creatures.add(creature);
		creature.currentLocation = this;
	}
	
	public void changeCreatureLocation(Creature creature , String where){
		if(!creatures.contains(creature))
			return;
		creatures.remove(creature);
		Location newLoc = locations.get(where);
		newLoc.putCreature(creature);
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
