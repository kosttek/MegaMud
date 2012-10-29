/**
marcinko
*/
package pl.edu.agh.megamud.mockdata;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;

public class SayinNutsBehaviour extends CyclicBehaviour{
	public SayinNutsBehaviour(Creature o,long delay) {
		super(o,delay);
	}

	public void action() {
		Creature owner = (Creature) getOwner();
		owner.getController().interpreteCommand("say","yeehaaa");
	}
}


