package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.mechanix.FightBehaviour;

public class CreatureFactory {

	public static Creature getRat() {
		final Creature rat = new Creature("rat").setLevel(1).setHp(34);

		rat.initAtribute(Attribute.findByName(Attribute.STRENGTH));
		rat.setAttribute(Attribute.STRENGTH, 5L);

		rat.addBehaviour(new FightBehaviour(rat));
		new CyclicBehaviour(rat, 5000L) {
			protected void action() {
				if (rat.getHp() > 0) {
					rat.getController().interpreteCommand("say", "*squeak*");
				}
			}
		}.init();

		return rat;
	}
}
