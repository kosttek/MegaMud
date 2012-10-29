package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.mockdata.MockCommands1;

/*
 * A location in our world.
 */
public class Location extends CommandCollector {
	private HashMap<String,Location> exits = new HashMap<String,Location>();
	private List<Creature> creatures = new ArrayList<Creature>();
	private String description;
	
	public Location(String description){
		this.description=description;
		
		addCommand(MockCommands1.getBasicCommand("look"));
		addCommand(MockCommands1.getBasicCommand("goto"));
		addCommand(MockCommands1.getBasicCommand("say"));
	}
	
	public final Map<String,Location> getExits(){
		return exits;
	}
	
	/*
	 * Gets all creatures controlled by real players. 
	 */
	public final ArrayList<Controller> getUsers(){
		ArrayList<Controller> users = new ArrayList<Controller>();
		for(Creature cr : creatures){
			if(cr.controller instanceof PlayerController)
				users.add(cr.controller);
		}
		return users;
	}
	
	/*
	 * Use this for location initialisation - add an exit.
	 */
	public final void addExit(String name,Location loc){
		exits.put(name, loc);
	}
	
	/*
	 * Executed after a creature entered the room. Notifies other creatures and sends the "look" command result.
	 */
	public void putCreature(Creature creature){
		creature.controller.write(prepareLook());
		creatures.add(creature);
		
		for(Creature c:creatures)
			if(c!=creature)
				c.controller.onEnter(creature);
	}
	
	/*
	 * Executed after a creature left this room. Notifies other creatures.
	 */
	public void removeCreature(Creature creature,String usedExit){
		creatures.remove(creature);
		for(Creature c:creatures)
			if(c!=creature)
				c.controller.onLeave(creature,usedExit);
	}
	
	/*
	 * Executed after a creature "says" something.
	 */
	public void sayCreature(Creature creature,String s){
		for(Creature c:creatures)
			c.controller.onSay(creature,s);
	}
	
	public String getDescription() {
		return description;
	}
	
	/*
	 * Result for command "look". Contains location description, all exists and other creatures.
	 */
	public final String prepareLook(){
		String desc = "You are in "+getDescription()+".\n";
		desc+="Possible exits: ";
		for(String locationPointer : exits.keySet())
			desc+= locationPointer+", ";
		desc+="\n";
		for(Creature creature : creatures)
			desc+="Here is "+ creature.name+".\n";
		
		return desc;
	}

}
