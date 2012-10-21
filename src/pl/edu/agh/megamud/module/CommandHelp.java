package pl.edu.agh.megamud.module;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Controller;

public class CommandHelp implements Command {
	public String getName(){
		return "help";
	}
	public boolean interprete(Controller user, String command) {
		String out="Known commands:";
		for(String cmd : user.getAllCommands().keySet())
			out+=" "+cmd;
		user.write(out);
		return true;
	}

}
