package pl.edu.agh.megamud.mechanix;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.itemtype.Body;
import pl.edu.agh.megamud.base.itemtype.Hand;
import pl.edu.agh.megamud.base.itemtype.Head;
import pl.edu.agh.megamud.dao.Attribute;

public class InitMechanix {
	/**
	 * Inited when player logged succesful
	 * @param itemHolder
	 */
	static public void initEquipment(ItemHolder itemHolder){
		List<Class> list  = new ArrayList<Class>();
		list.add(Hand.class);
		list.add(Head.class);
		list.add(Body.class);
		
		itemHolder.setEquipmentTypes(list);
	}
	
	//TODO grab attacker's & defender's stats.
	public static void attack(Creature attacker, Creature defender){
		Map<Attribute, Long> attackerAttrs = attacker.generateAttributes();
		Map<Attribute, Long> defenderAttrs = defender.generateAttributes();
		defender.addDamage(10);
	}
}
