package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandAskOldman extends Command {
	Oldman brain;
	public CommandAskOldman(Oldman brain) {
		this.brain = brain;
	}

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;
		
		String result = brain.ask(command);
		if(result == null)
			result = "I do not have answer to that.";
		user.write(result);
		
		return true;
	}

	public String getName() {
		return "ask";
	}
}
