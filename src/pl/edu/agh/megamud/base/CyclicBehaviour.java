/**
marcinko
*/
package pl.edu.agh.megamud.base;

/*
 * A behaviour run cyclicly - every specified time.
 */
public abstract class CyclicBehaviour extends Behaviour {
	public CyclicBehaviour(Object o,long delay) {
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


