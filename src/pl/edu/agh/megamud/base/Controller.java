package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.GameServer;


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
		GameServer.getInstance().initCreature(creature);
	}
	
	public void disconnect(){
		creature.setLocation(null,null);
	}
	
	/*
	 * Method to "interprete" a command. Use it instead of browsing other classes' sources.
	 */
	public boolean interpreteCommand(String commandString){
		String cmd=commandString.trim();
		String[] arr=cmd.split(" ");
		String firstWord = arr[0];
		
		String args2=arr.length>1 ? cmd.substring(firstWord.length()+1) : "";
		
		return interpreteCommand(firstWord,args2);
	}
	
	public boolean interpreteCommand(String cmd,String args){
		Command cmd2=findCommand(cmd);
		if(cmd2!=null && cmd2.interprete(this, args))
			return true;
		write("Unknown command, type help for known commands.");
		return false;
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
