package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandSay implements Command {
	public String getName(){
		return "say";
	}
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		String outString=command;
		user.getCreature().getLocation().sayCreature(user.getCreature(), outString);
		return true;
	}

}
