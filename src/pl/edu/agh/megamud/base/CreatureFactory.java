package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.mechanix.FightBehaviour;

public class CreatureFactory {

	public static Creature getRat(){
		Creature rat = new Creature("rat")
			.setLevel(1)
			.setHp(34);
	
		rat.addBehaviour(new FightBehaviour(rat));
		return rat;
	}
}
