/**
marcinko
*/
package pl.edu.agh.megamud.mockdata;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;

public class BallBounceBehaviour extends CyclicBehaviour{
	public BallBounceBehaviour(Creature o,long delay) {
		super(o,delay);
	}

	public void action() {
		Creature owner = (Creature) getOwner();
		Controller con=owner.getController();
		
		boolean b;
		if(owner.getItems().size()==0){
			b=con.interpreteCommand("take", "ball");
		}else{
			b=con.interpreteCommand("drop", "ball");
		}
	}
}


