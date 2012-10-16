package pl.edu.agh.megamud.module;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.InteractiveObject;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.utils.StringCommandTuple;

public class CommandSay implements Command {
	
	@Override
	public List<StringCommandTuple> install(InteractiveObject interactiveObject) {
		List <StringCommandTuple> result = new ArrayList<StringCommandTuple>();
		result.add(new StringCommandTuple("say",this));
		return result;
	}

	@Override
	public boolean interprete(User user, String command) {
		String [] args = command.trim().split(" ");
		if (args[0].equalsIgnoreCase("say")) {
			String outString = new String();
			for(int i = 1 ; i < args.length ; i++)
				outString+= args[i]+" ";
			ArrayList<User> users = user.player.currentLocation.getUsersInThisLocation() ;
			for(User userLoc :users)
				if(user != userLoc)
					userLoc.out.println(user.player.name+" said: "+outString);
			return true;
		}
		return false;
	}

}
