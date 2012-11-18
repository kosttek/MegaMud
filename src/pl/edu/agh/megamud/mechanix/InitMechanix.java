package pl.edu.agh.megamud.mechanix;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.itemtype.Body;
import pl.edu.agh.megamud.base.itemtype.Hand;
import pl.edu.agh.megamud.base.itemtype.Head;

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
	
	//TODO
	static public void attack(Creature attacker, Creature Defender){
		Defender.addDamage(10);
	}
}
