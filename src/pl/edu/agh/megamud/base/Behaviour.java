/**
marcinko
*/
package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.EventManager;

/**
 * Abstraction of a "action" that a creature can do.
 **/
public abstract class Behaviour {
	protected Agent owner;
	
	public Behaviour(Agent o){
		this.owner=o;
	}
	
	public Agent getOwner(){
		return owner;
	}
	/**
	 * To be implemented by subclasses - what actually Behavior does.
	 */
	protected abstract void action();
	
	public void init(Long delay){
		put(delay);
	}
	public void makeAction(){
		action();
	}
	public void put(Long delay){
		EventManager.getInstance().put(delay, this);
	}
}


