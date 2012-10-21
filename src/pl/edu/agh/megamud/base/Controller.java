package pl.edu.agh.megamud.base;


/**
 * Abstraction of a "controller" of a creature.
 **/
public abstract class Controller extends CommandCollector{
	/*
	 * Our creature. Placed after log-in (if real player) or after controller creation (if NPC).
	 */
	protected Creature creature=null;
	
	public Controller(){
	}
	
	public final Creature getCreature(){
		return creature;
	}
	
	public void setCreature(Creature creature){
		this.creature=creature;
	}
	
	public void disconnect(){
		creature.setLocation(null,null);
	}
	
	
	/*
	 * Use this to send a message to controller.
	 */
	public void write(String txt){}
	
	/* In-location events.*/
	public void onEnter(Creature otherCreature){}
	public void onLeave(Creature otherCreature,String usedExit){}
	public void onSay(Creature otherCreature,String message){}
}
