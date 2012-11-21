package pl.edu.agh.megamud.module;

import java.util.Map;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.itemtype.ItemToWorn;

public class CommandUnequip extends Command {
	public static final String commandString = "unequip";

	public String getName() {
		return commandString;
	}

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;
		Map<Class<? extends ItemToWorn>, ItemToWorn> eq = user.getCreature()
				.getEquipment();
		for (Class<? extends ItemToWorn> clazz : eq.keySet()) {
			if (eq.get(clazz).getName().equals(command)) {
				user.getCreature().unequip(eq.get(clazz));
				return true;
			}
		}
		return false;
	}

}
