package pl.edu.agh.megamud.base;

import java.util.Iterator;
import java.util.List;

import pl.edu.agh.megamud.dao.Item;


/**
 * Abstraction of a "controller" of a creature. Controler is a brain for a creature. Receives various events, can control creature's behaviour and initiates commands.
 **/
public abstract class Controller extends CommandCollector{
	/**
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
	
	/**
	 * Method to run a command. Provide a command line from user.
	 */
	public boolean interpreteCommand(String commandString){
		String cmd=commandString.trim();
		String[] arr=cmd.split(" ");
		String firstWord = arr[0];
		
		String args2=arr.length>1 ? cmd.substring(firstWord.length()+1) : "";
		
		return interpreteCommand(firstWord,args2);
	}
	
	/**
	 * Method to run a command. Provide a command name and arguments. Use this even in NPC class, since due to modular nature of MegaMUD some commands may not be available to user at a specific moment, or other commands may be more appriopriate than default ones.
	 * @return true, if any actual command was run.
	 */
	public boolean interpreteCommand(String cmd,String args){
		List<Command> cmd2=findCommands(cmd);
		if(cmd2!=null)
			for(Iterator<Command> i=cmd2.iterator();i.hasNext();){
				Command c=i.next();
				if(c.interprete(this, args))
					return true;
			}
		write("Unknown command, type help for known commands.");
		return false;
	}
	
	/**
	 * Use this to send a message to controller.
	 */
	public void write(String txt){}
	
	/** In-location events.*/
	public void onEnter(Creature otherCreature){}
	public void onLeave(Creature otherCreature,String usedExit){}
	public void onSayInLocation(Creature otherCreature,String message){}
	/**
	 * Abstract event for all item dis/appearances:
	 * - item magically appeared in a location or creature's inventory;
	 * -                disappeared                                   ;
	 * - a creature gave an item to other creature (especially: _we_ gave (from), or _we_ were given (to));
	 * - someone dropped an item (to=Location);
	 * - someone picked an item (from=Location).
	 * Here we will get notifications from the Location.
	 */
	public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){}
	/** Inherited from ItemHolder. */
	public void onItemAppear(Item i,ItemHolder from){}
	public void onItemDisappear(Item i,ItemHolder to){}
}
