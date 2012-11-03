package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.mockdata.MockCommands1;

/**
 * A location in our world. Location has its description, exits and creatures inside.
 */
public class Location extends CommandCollector {
	private Map<String,Location> exits = new HashMap<String,Location>();
	private Map<String,Creature> creatures = new HashMap<String,Creature>();
	private String description;
	
	public Location(String description){
		this.description=description;
		
		addCommand(MockCommands1.getBasicCommand("look"));
		addCommand(MockCommands1.getBasicCommand("goto"));
		addCommand(MockCommands1.getBasicCommand("say"));
		addCommand(MockCommands1.getBasicCommand("take"));
	}
	
	public final Map<String,Location> getExits(){
		return exits;
	}
	
	public final Map<String,Creature> getCreatures(){
		return creatures;
	}
	
	/**
	 * Use this for location initialisation - add an exit.
	 */
	public final void addExit(String name,Location loc){
		exits.put(name, loc);
	}
	
	/**
	 * Executed after a creature entered the room. Notifies other creatures and sends the "look" command result.
	 */
	public void onAddCreature(Creature creature){
		creature.controller.write(prepareLook());
		creatures.put(creature.getName(),creature);
		
		for(Creature c:creatures.values())
			if(c!=creature)
				c.controller.onEnter(creature);
	}
	
	/**
	 * Executed after a creature left this room. Notifies other creatures.
	 */
	public void onRemoveCreature(Creature creature,String usedExit){
		creatures.remove(creature.getName());
		for(Creature c:creatures.values())
			if(c!=creature)
				c.controller.onLeave(creature,usedExit);
	}
	
	/**
	 * Executed after a creature "says" something.
	 */
	public void onCreatureSay(Creature creature,String s){
		for(Creature c:creatures.values())
			c.controller.onSayInLocation(creature,s);
	}
	
	public void onItemTransfer(ItemHolder oldOwner,ItemHolder newOwner,Item i){
		for(Creature c:creatures.values())
			c.controller.onItemTransfer(oldOwner,newOwner,i);
	}
	
	public void onItemAppear(Item i,ItemHolder from){
		for(Creature c:creatures.values())
			c.controller.onItemTransfer(from,this,i);
	}
	
	public void onItemDisappear(Item i,ItemHolder to){
		for(Creature c:creatures.values())
			c.controller.onItemTransfer(this,to,i);
	}
	
	public String getDescription() {
		return description;
	}
	
	/**
	 * Result for command "look". Contains location description, all exists and other creatures.
	 */
	public final String prepareLook(){
		String desc = "You are in "+getDescription()+".\r\n";
		desc+="Possible exits: ";
		
		int cnt=exits.size();
		for(String locationPointer : exits.keySet())
			desc+= locationPointer+(cnt>0 ? ", " : "");
		desc+="\r\n";
		

		for(Item i : items.values())
			desc+="Here is "+ i.getName()+" - "+i.getDescription()+"\r\n";
		desc+="\r\n";
		
		for(Creature creature : creatures.values())
			desc+="Here is "+ creature.getName()+", a LV"+creature.getLevel()+" "+creature.getKlass()+".\r\n";
		
		return desc;
	}

}
