package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandKill extends Command {

	public boolean interprete(Controller user, String command) {
		System.exit(0);
		return true;
	}

	public String getName() {
		return "kill";
	}
}
