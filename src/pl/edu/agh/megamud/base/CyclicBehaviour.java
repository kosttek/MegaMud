/**
marcinko
*/
package pl.edu.agh.megamud.base;

public class CyclicBehaviour extends Behaviour {
	private boolean done=false;
	private long cyclicDelay;
	@Override
	public void makeAction(){
		System.out.println(this+" "+done);
		if(!isDone()){
			action();
			put(getCyclicDelay());
		}
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
		System.out.println(done);
		this.done = done;
	}
}


