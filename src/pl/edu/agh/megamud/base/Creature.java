package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.Interpreter;

public class Creature implements InteractiveObject {
	public User parent = null;
	public Location currentLocation;
	public String name;
	private Interpreter interpreter= new Interpreter();
	
	@Override
	public Interpreter getInterpreter() {
		return interpreter;
	}

	@Override
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;		
	}
}
