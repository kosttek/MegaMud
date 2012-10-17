/**
marcinko
*/
package pl.edu.agh.megamud.base;

public class CyclicBehaviour extends Behaviour {
	private boolean done;
	private long cyclicDelay;
	@Override
	public void makeAction(){
		action();
		if(!isDone())
			put(getCyclicDelay());
	}
	public long getCyclicDelay() {
		return cyclicDelay;
	}
	public void setCyclicDelay(long cyclicDelay) {
		this.cyclicDelay = cyclicDelay;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
}


