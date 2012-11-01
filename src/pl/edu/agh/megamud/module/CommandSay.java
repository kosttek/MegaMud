package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandSay extends Command {
	public String getName(){
		return "say";
	}
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		String outString=command;
		user.getCreature().getLocation().onCreatureSay(user.getCreature(), outString);
		return true;
	}

}
