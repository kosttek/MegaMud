/**
marcinko
*/
package pl.edu.agh.megamud.mockdata;

import pl.edu.agh.megamud.base.Agent;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Controller;

public class SayinNutsBehaviour extends CyclicBehaviour{
	public SayinNutsBehaviour(Agent o) {
		super(o);
	}

	public void action() {
		Creature owner = (Creature) getOwner();
		for(Controller user : owner.getLocation().getUsers()){
			user.write(owner.getName()+" says: megusta!");
		}
		
	}
}


