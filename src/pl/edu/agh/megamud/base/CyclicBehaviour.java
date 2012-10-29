/**
marcinko
*/
package pl.edu.agh.megamud.base;

public abstract class CyclicBehaviour extends Behaviour {
	public CyclicBehaviour(Creature o,long delay) {
		super(o,delay);
	}
	
	private boolean done;
	
	public void makeAction(){
		action();
		if(!isDone())
			put();
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}


