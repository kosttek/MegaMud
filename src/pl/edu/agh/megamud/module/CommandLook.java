package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;

public class CommandLook implements Command {

	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		
		String desc = user.getCreature().getLocation().prepareLook();
		user.write(desc);
		return true;
	}

	public String getName() {
		return "look";
	}
}
