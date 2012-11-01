/**
marcinko
*/
package pl.edu.agh.megamud.base;


/**
 * Abstraction of a "action" that a creature can do.
 **/
public abstract class Behaviour {
	/**
	 * An owner of this behaviour. Behaviour will work on this creature.
	 */
	protected Object owner;
	/**
	 * Delay after which behaviour will run.
	 */
	protected long delay;
	/**
	 * Time this behaviour will be run.
	 */
	protected long nextTime;
	
	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
	
	public long getNextTime(){
		return nextTime;
	}

	public Behaviour(Object o,long delay){
		this.owner=o;
		this.delay=delay;
	}
	
	public Object getOwner(){
		return owner;
	}
	/**
	 * To be implemented by subclasses - what actually Behavior does.
	 */
	protected abstract void action();
	
	/**
	 * Initialize a behaviour.
	 */
	public final Behaviour init(){
		put();
		return this;
	}
	
	/**
	 * Use this to call an action on a behaviour.
	 */
	public void makeAction(){
		action();
	}
	
	/**
	 * Use this in lower classes to (re-)initialize a behaviour.
	 */
	protected final void put(){
		nextTime=EventManager.getInstance().put(new Long(delay), this);
	}
}


