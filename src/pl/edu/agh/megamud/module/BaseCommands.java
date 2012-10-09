package pl.edu.agh.megamud.module;

import java.util.ArrayList;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.module.interfaces.Interprete;

public class BaseCommands implements Interprete {

	@Override
	public void interprete(User user, String command) {
		commandExit(user, command);
		commandLook(user, command);
		commandSay(user, command);
	}

	private void commandExit(User user, String command) {
		if (command.trim().equalsIgnoreCase("exit")) {
			user.player.currentLocation.creatures.remove(user.player);
			user.setExitServer(true);
		}
	}
	
	private void commandLook(User user, String command) {
		if (command.trim().equalsIgnoreCase("look")) {
			String desc = prepareDescription(user.player.currentLocation);
			user.out.println(desc);
		}
	}
	
	private void commandSay(User user, String command) {
		String [] args = command.trim().split(" ");
		if (args[0].equalsIgnoreCase("say")) {
			String outString = new String();
			for(int i = 1 ; i < args.length ; i++)
				outString+= args[i]+" ";
			ArrayList<User> users = user.player.currentLocation.getUsersInThisLocation() ;
			for(User userLoc :users)
				if(user != userLoc)
					userLoc.out.println(user.player.name+" said: "+outString);
		}
	}
	
	private String prepareDescription(Location location){
		String desc = location.getDescription()+"\n";
		for(Creature creature :location.creatures)
			desc+= creature.name+", ";
		desc+="\n";
		for(String locationPointer :location.locations.keySet())
			desc+= locationPointer+", ";
		
		return desc;
	}
	
	

}

