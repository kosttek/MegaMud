package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.CommandsCollection;

public class Creature implements InteractiveObject {
	public User parent = null;
	public Location currentLocation;
	public String name;
	private CommandsCollection interpreter= new CommandsCollection();
	
	@Override
	public CommandsCollection getInterpreter() {
		return interpreter;
	}

	@Override
	public void setInterpreter(CommandsCollection interpreter) {
		this.interpreter = interpreter;		
	}
}
