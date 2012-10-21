package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Creature extends CommandCollector implements Agent {
	protected String name;
	protected Controller controller=null;
	protected Location location=null;
	protected List<Behaviour> behaviourList = new ArrayList<Behaviour>();
	
	public final Controller getController(){
		return controller;
	}
	public final String getName(){
		return name;
	}
	
	public Creature(String name){
		this.name=name;
	}
	
	public void connect(Controller controller){
		this.controller=controller;
		controller.setCreature(this);
		
		for(String cmd:getAllCommands().keySet()){
			controller.addCommand(cmd);
		}
	}
	public void disconnect(){
		for(String key:getAllCommands().keySet()){
			controller.removeCommand(key);
		}
		this.controller=null;
	}
	
	public final Location getLocation(){
		return location;
	}
	
	public final void setLocation(Location exit,String exitName){
		if(location!=null){
			for(String key:exit.getAllCommands().keySet()){
				removeCommand(key);
				controller.removeCommand(key);
			}
			
			location.removeCreature(this,exitName);
		}
		
		location=exit;
		
		if(exit!=null){
			for(String cmd:exit.getAllCommands().keySet()){
				addCommand(cmd);
				controller.addCommand(cmd);
			}
			location.putCreature(this);
		}
	}
	
	public boolean moveTo(String exit){
		Map<String,Location> exits=location.getExits();
		if(exits.containsKey(exit)){
			setLocation(exits.get(exit),exit);
			return true;
		}
		return false;
			
	}
	
	public final List<Behaviour> getBehaviourList() {
		return behaviourList;
	}

	public final void setBehaviourList(List<Behaviour> behaviourList) {
		this.behaviourList = behaviourList;
	}
}
