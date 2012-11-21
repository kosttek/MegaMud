package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.mechanix.FightBehaviour;

public class CreatureFactory {

	public static enum Creatures { RAT, GOBLIN }
	public static String RAT = "rat";
	public static String GOBLIN = "goblin";
	
	public static Creature getRat() {
		final Creature rat = new Creature(RAT).setLevel(1).setHp(34);

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
	
	public static Creature getGoblin(){
		final Creature goblin = new Creature(GOBLIN)
			.setLevel(1)
			.setHp(40);

        goblin.initAtribute(Attribute.findByName(Attribute.STRENGTH));
        goblin.setAttribute(Attribute.STRENGTH, 7L);	

		goblin.addBehaviour(new FightBehaviour(goblin));
		new CyclicBehaviour(goblin, 5000L) {
			protected void action() {
				if (goblin.getHp() > 0){
					goblin.getController().interpreteCommand("say", "Silence! I kill you!");
				}
			}
		}.init();
		
		return goblin;
	}	
	
	public static Creature getCreature(String creature){
		if (creature.equals(GOBLIN)){
			return CreatureFactory.getGoblin();
		} else {
			return CreatureFactory.getRat();
		}
	}
}
