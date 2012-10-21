package pl.edu.agh.megamud.mockdata;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.InteractiveObject;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.utils.StringCommandTuple;

public class CommandKick implements Command {
	InteractiveObject owner ;
	@Override
	public List<StringCommandTuple> install(InteractiveObject interactiveObject) {
		owner = interactiveObject;
		List <StringCommandTuple> result = new ArrayList<StringCommandTuple>();
		result.add(new StringCommandTuple("kick",this));
		return result;
	}

	@Override
	public boolean interprete(User user, String command) {
		String [] args = command.trim().split(" ");
		Creature creature =(Creature) owner;
		if (args[0].equalsIgnoreCase("kick") && args[1].equalsIgnoreCase(creature.name)) {
			for (Behaviour beh :creature.getBehaviourList())
				//It takse only first behaviour which fits so when its more then one it always set done true to this first
				if(beh instanceof SayinNutsBehaviour){ 
					((SayinNutsBehaviour) beh).setDone(true);
					messageToAllUsers(creature, user);
					return true;
				}
		}
		return false;
	}
	
	private void messageToAllUsers(Creature creature, User kicker){
		Location loc = creature.currentLocation;
		for (User user :loc.getUsersInThisLocation()){
			if(user == kicker)
				user.out.println(creature.name+" patrzy na Ciebie ze strachem w oczach, juz pewnie nic nie powie");
			else{
				user.out.println(creature.name+" patrzy na "+ kicker.player.name +" ze strachem w oczach, juz pewnie nic nie powie");
			}
		}
	}

}