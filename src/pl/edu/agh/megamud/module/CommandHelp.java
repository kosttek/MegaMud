package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandHelp extends Command {
	public String getName() {
		return "help";
	}

	public boolean interprete(Controller user, String command) {
		String out = "Known commands:";
		for (Command cmd : user.getAllCommands())
			out += " <" + cmd.getName() + ">";
		user.write(out);
		return true;
	}

}
