package pl.edu.agh.megamud.mechanix;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;

public class CommandHit extends Command {
	public static final String commandString = "hit";

	public String getName() {
		return commandString;
	}

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;
		FightBehaviour fightBehaviour = (FightBehaviour) user.getCreature()
				.getBehaviourByType(FightBehaviour.class).get(0);
		Creature opponent = user.getCreature().getLocation().getCreatures()
				.get(command);
		System.out.println("Fight: " + fightBehaviour + " op " + opponent);
		if (fightBehaviour != null && opponent != null) {
			fightBehaviour.setOpponent(opponent);
			fightBehaviour.init();
			return true;
		}
		return false;
	}

}
