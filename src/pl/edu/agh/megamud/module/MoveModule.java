package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.module.interfaces.Interprete;

public class MoveModule implements Interprete {

	@Override
	public void interprete(User user, String command) {
		commandMove(user, command);
		
	}

	private void commandMove(User user, String command) {
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
		}
	}
	
	
	

}