package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import pl.edu.agh.megamud.module.*;

/*
 * Collection of commands. Hosted in a map (string => list<commands>) - commands with the same name. 
 */
public abstract class CommandCollector {
	private Map<String,List<Command>> map = new HashMap<String, List<Command>>();
	public final List<Command> findCommands(String name){
		return map.get(name);
	}
	
	protected final void removeCommand(Command cmd){
		List<Command> v=map.get(cmd.getName());
		if(v==null)
			return;
		v.remove(cmd);
		if(v.size()==0)
			map.remove(cmd.getName());
	}
	
	protected final void addCommand(Command cmd){
		List<Command> v=map.get(cmd.getName());
		if(v==null){
			v=new LinkedList<Command>();
			map.put(cmd.getName(), v);
		}
		v.add(cmd);
	}
	
	public final List<Command> getAllCommands(){
		List<Command> l=new LinkedList<Command>();
		for(Iterator<Entry<String,List<Command>>> i=map.entrySet().iterator();i.hasNext();){
			Entry<String,List<Command>> e=i.next();
			for(Iterator<Command> c=e.getValue().iterator();c.hasNext();)
				l.add(c.next());
		}
		return l;
	}
}

