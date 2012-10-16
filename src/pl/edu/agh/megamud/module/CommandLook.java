package pl.edu.agh.megamud.module;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.InteractiveObject;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.utils.StringCommandTuple;

public class CommandLook implements Command {

	@Override
	public List<StringCommandTuple> install(InteractiveObject interactiveObject) {
		List<StringCommandTuple> result = new ArrayList<StringCommandTuple>();
		if(interactiveObject instanceof User){
			result.add(new StringCommandTuple("look",this));
		}
		return  result;
	}

	@Override
	public boolean interprete(User user, String command) {
		if (command.trim().equalsIgnoreCase("look")) {
			String desc = prepareDescription(user.player.currentLocation);
			user.out.println(desc);
			return true;
		}
		return false;
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
