package pl.edu.agh.megamud.base;

import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of a command to be executed by a controller.
 */
public abstract class Command {
	/**
	 * Used to keep track, who uses us. 
	 */
	private List<CommandCollector> collectors=new LinkedList<CommandCollector>();
	
	/**
	 * Use this to install this command.
	 * @param collector
	 */
	public final void installTo(CommandCollector collector){
		synchronized(this){
			collectors.add(collector);
			collector.addCommand(this);
		}
	}

	/**
	 * Use this to uninstall this command.
	 * @param collector
	 */
	public final void uninstallFrom(CommandCollector collector){
		synchronized(this){
			collectors.remove(collector);
			collector.removeCommand(this);
		}
	}
	
	public final void uninstall(){
		synchronized(this){
			for(CommandCollector c:collectors){
				c.removeCommand(this);
			}
			collectors.clear();
		}
	}
	
	public List<CommandCollector> getCollectors(){
		synchronized(this){
			return collectors;
		}
	}
	
	/*
	 * Short name that identified this command. E.g. exit, help. Note that many commands can have similar name - which will be executed is distinguished by interprete() result.
	 */
	public abstract String getName();
	/*
	 * Run the command.
	 * @returns true, if:
	 * - user is allowed to run this command (he is an administrator or similar);
	 * - some preconditions are met, e.g. user is an location, holds an item, etc. This is used to distinguish whether this command is specific and "needed" by a creature. See getName().
	 * - command succedeed.
	 * Otherwise false.
	 */
	public abstract boolean interprete(Controller user, String command);
}
