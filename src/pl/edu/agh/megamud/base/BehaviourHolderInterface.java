package pl.edu.agh.megamud.base;

import java.util.List;

public interface BehaviourHolderInterface {
	public List<? extends Behaviour> getBehaviourList();

	public void setBehaviourList(List<? extends Behaviour> list);

	public void addBehaviour(Behaviour behaviour);

	public void removeBehaviour(Behaviour behaviour);

	public List<Behaviour> getBehaviourByType(Class<? extends Behaviour> clazz);

}
