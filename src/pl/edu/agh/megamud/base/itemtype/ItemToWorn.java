package pl.edu.agh.megamud.base.itemtype;

import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.dao.CreatureItem;

public class ItemToWorn extends Item {

	public ItemToWorn(CreatureItem it) {
		super(it);
		// TODO Auto-generated constructor stub
	}

	public ItemToWorn(String name, String description) {
		super(name, description);
	}

	@Override
	public boolean canBeGivenTo(ItemHolder owner) {
		return true;
	}
}
