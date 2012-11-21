package pl.edu.agh.megamud.base.itemtype;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.dao.Attribute;


public class HealItem extends ItemToUse{

	public HealItem(String name, String description) {
		super(name, description);
	}
	@Override
	public boolean use(){	
		((Creature)owner).addDamage(-getAttributeValue(Attribute.HEAL).intValue());
		return true;
	}
}
