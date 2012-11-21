package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.itemtype.ItemToUse;
import pl.edu.agh.megamud.base.itemtype.ItemToWorn;

public class CommandUse extends Command {
	public static final String commandString = "use";

	public String getName() {
		return commandString;
	}

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;

		Item item = user.getCreature().getItems().get(command);
		if (item == null || !(item instanceof ItemToUse))
			return false;

		 ((ItemToUse)item).use();
		return true;
	}

}
