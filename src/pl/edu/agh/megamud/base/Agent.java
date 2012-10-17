/**
marcinko
*/
package pl.edu.agh.megamud.base;

import java.util.List;

public interface Agent {
	public List<Behaviour> getBehaviourList();
	public void setBehaviourList(List<Behaviour> behaviourList); 
}


