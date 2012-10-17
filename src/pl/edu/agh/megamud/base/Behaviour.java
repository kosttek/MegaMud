/**
marcinko
*/
package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.EventManager;

public class Behaviour {
	private  Agent owner;
	public void action(){
		
	}
	public void init(Long delay){
		put(delay);
	}
	public void makeAction(){
		action();
	}
	public void put(Long delay){
		EventManager.getInstance().put(delay, this);
	}
	public Agent getOwner() {
		return owner;
	}
	public void setOwner(Agent owner) {
		this.owner = owner;
	}
}


