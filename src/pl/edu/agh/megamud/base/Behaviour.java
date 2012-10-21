/**
marcinko
*/
package pl.edu.agh.megamud.base;


/**
 * Abstraction of a "action" that a creature can do.
 **/
public abstract class Behaviour {
	protected Creature owner;
	protected long delay;
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	public Behaviour(Creature o,long delay){
		this.owner=o;
		this.delay=delay;
	}
	
	public Creature getOwner(){
		return owner;
	}
	/**
	 * To be implemented by subclasses - what actually Behavior does.
	 */
	protected abstract void action();
	
	public final Behaviour init(){
		put();
		return this;
	}
	public void makeAction(){
		action();
	}
	public final void put(){
		EventManager.getInstance().put(new Long(delay), this);
	}
}


