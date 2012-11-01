package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Controller;

public class CommandGoto extends Command {
	public String getName(){
		return "goto";
	}
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		
		Creature player = user.getCreature();
		if(!player.moveTo(command))
			user.write("No such exit!");
		return true;
	}

}
