package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.CommandsCollection;

public class Creature implements InteractiveObject, Agent {
	public User parent = null;
	public Location currentLocation;
	public String name;
	private CommandsCollection interpreter= new CommandsCollection();
	private List<Behaviour> behaviourList = new ArrayList<Behaviour>();
	
	@Override
	public CommandsCollection getCommandCollection() {
		return interpreter;
	}

	@Override
	public void getCommandCollection(CommandsCollection interpreter) {
		this.interpreter = interpreter;		
	}

	public List<Behaviour> getBehaviourList() {
		return behaviourList;
	}

	public void setBehaviourList(List<Behaviour> behaviourList) {
		this.behaviourList = behaviourList;
	}
}
