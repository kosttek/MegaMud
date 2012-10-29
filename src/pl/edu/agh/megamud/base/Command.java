package pl.edu.agh.megamud.base;

public interface Command {
	public String getName();
	public boolean interprete(Controller user, String command);
}
