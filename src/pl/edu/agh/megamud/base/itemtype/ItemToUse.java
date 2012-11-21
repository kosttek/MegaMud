package pl.edu.agh.megamud.base.itemtype;

import pl.edu.agh.megamud.base.Item;

public class ItemToUse extends Item {

	public ItemToUse(String name, String description) {
		super(name, description);
	}
	public boolean use(){
		return false;
	}
}
