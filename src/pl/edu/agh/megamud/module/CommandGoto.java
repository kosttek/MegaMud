package pl.edu.agh.megamud.module;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.InteractiveObject;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.utils.StringCommandTuple;

public class CommandGoto implements Command {
	
	@Override
	public List<StringCommandTuple> install(InteractiveObject interactiveObject) {
		List <StringCommandTuple> result = new ArrayList<StringCommandTuple>();
		result.add(new StringCommandTuple("goto",this));
		return result;
	}

	@Override
	public boolean interprete(User user, String command) {
		String [] args = command.trim().split(" ");
		if (args[0].equalsIgnoreCase("goto")) {
			Creature player = user.player;
			if(player.currentLocation.locations.containsKey(args[1])){
				
				for(User userLoc :player.currentLocation.getUsersInThisLocation())
					if(user != userLoc)
						userLoc.out.println(user.player.name+" poszedl "+args[1]);
				player.currentLocation.changeCreatureLocation(player, args[1]);
				for(User userLoc :player.currentLocation.getUsersInThisLocation())
					if(user != userLoc)
						userLoc.out.println(user.player.name+" przybyl ");
			}
			return true;
		}
		return false;
	}

}
