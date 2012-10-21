package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.CommandsCollection;

public interface InteractiveObject {
	public CommandsCollection getCommandCollection();
	public void getCommandCollection(CommandsCollection interpreter);
}
