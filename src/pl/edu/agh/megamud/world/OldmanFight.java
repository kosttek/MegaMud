package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.BehaviourHolderInterface;
import pl.edu.agh.megamud.mechanix.FightBehaviour;

public class OldmanFight extends FightBehaviour {

	public OldmanFight(BehaviourHolderInterface o) {
		super(o);
	}
	
	@Override
	protected void action() {
		getOpponentFightBehaviour().setActive(false);
		getOpponent().getController().write("Do not hit me !");
	}

}
