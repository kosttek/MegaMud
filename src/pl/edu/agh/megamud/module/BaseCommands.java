package pl.edu.agh.megamud.module;

import java.util.ArrayList;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.module.interfaces.Interprete;

public class BaseCommands implements Interprete {

	@Override
	public void interprete(User user, String command) {


		commandSay(user, command);
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
	

	
	

}

