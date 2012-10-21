package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.module.*;

/*
 * Collection of commands. Hosted in a map (string => list<commands>) - commands with the same name. 
 */
public abstract class CommandCollector {
	private Map<String,Command> map = new HashMap<String, Command>();
	private static Map<String,Command> knownCommands;
	
	static{
		
		knownCommands=new HashMap<String,Command>();
		knownCommands.put("exit",new CommandExit());
		knownCommands.put("goto",new CommandGoto());
		knownCommands.put("help",new CommandHelp());
		knownCommands.put("info",new CommandInfo());
		knownCommands.put("login",new CommandLogin());
		knownCommands.put("look",new CommandLook());
		knownCommands.put("say",new CommandSay());
		
		knownCommands.put("kill",new CommandKill());
	}
	
	public final Command findCommand(String name){
		return map.get(name);
	}
	
	protected final void removeCommand(String cmd){
		map.remove(cmd);
	}
	
	protected final void addCommand(String name){
		Command cmd=knownCommands.get(name);
		if(cmd!=null){
			map.put(name, cmd);
		}
	}
	
	public final Map<String,Command> getAllCommands(){
		return map;
	}
}

