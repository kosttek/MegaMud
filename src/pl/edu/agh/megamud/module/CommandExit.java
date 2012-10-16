package pl.edu.agh.megamud.module;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.InteractiveObject;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.utils.StringCommandTuple;

public class CommandExit implements Command {
	
	@Override
	public List<StringCommandTuple> install(InteractiveObject interactiveObject) {
		List <StringCommandTuple> result = new ArrayList<StringCommandTuple>();
		result.add(new StringCommandTuple("exit",this));
		return result;
	}

	@Override
	public boolean interprete(User user, String command) {
		if (command.trim().equalsIgnoreCase("exit")) {
			user.player.currentLocation.creatures.remove(user.player);
			user.setExitServer(true);
			return true;
		}
		return false;
	}

}
