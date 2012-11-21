package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandAskOldman extends Command {

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;

		user.write("you asked about " + command);
		return true;
	}

	public String getName() {
		return "ask";
	}
}
