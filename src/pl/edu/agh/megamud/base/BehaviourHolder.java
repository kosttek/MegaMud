package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class BehaviourHolder implements BehaviourHolderInterface {
	Set<Behaviour> list = new HashSet<Behaviour>();
	
	@Override
	public List<Behaviour> getBehaviourList() {
		return new ArrayList<Behaviour>(list);
	}

	@Override
	public void setBehaviourList(List<Behaviour> list) {
		this.list = new HashSet<Behaviour>(list);
	}

	@Override
	public void addBehaviour(Behaviour behaviour) {
		list.add(behaviour);
		
	}

	@Override
	public List<Behaviour> getBehaviourByType(Class<Behaviour> clazz) {
		Set<Behaviour> result = new HashSet<Behaviour>();
		for (Behaviour behaviour : list){
			if(behaviour.getClass().isAssignableFrom(clazz)){
				result.add(behaviour);
			}
		}
		return new ArrayList<Behaviour>(result) ;
	}

	@Override
	public void removeBehaviour(Behaviour behaviour) {
		list.remove(behaviour);
	}

}
