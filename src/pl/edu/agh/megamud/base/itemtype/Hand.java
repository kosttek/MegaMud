package pl.edu.agh.megamud.base.itemtype;

import pl.edu.agh.megamud.dao.CreatureItem;

public class Hand extends ItemToWorn {

	public Hand(CreatureItem it) {
		super(it);
		// TODO Auto-generated constructor stub
	}
	
	public Hand(String name,String description){
		super(name,description);
	}
}
