package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.itemtype.ItemToWorn;

public class CommandEquip extends Command {
	public static final String commandString = "equip";
	public String getName(){
		return commandString;
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		
		Item item = user.getCreature().getItems().get(command);
		if(item == null || !(item instanceof ItemToWorn))
			return false;
		
		user.getCreature().equip((ItemToWorn)item);
		return true;
	}
	
}
