/**
marcinko
*/
package pl.edu.agh.megamud.mockdata;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.User;

public class SayinNutsBehaviour extends CyclicBehaviour{
	@Override
	public void action() {
		Creature owner = (Creature) getOwner();
		for(User user : owner.currentLocation.getUsersInThisLocation()){
			user.out.println(owner.name+" says: megusta!");
		}
		
	}
}


