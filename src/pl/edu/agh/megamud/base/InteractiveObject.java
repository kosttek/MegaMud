package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.Interpreter;

public interface InteractiveObject {
	public Interpreter getInterpreter();
	public void setInterpreter(Interpreter interpreter);
}
