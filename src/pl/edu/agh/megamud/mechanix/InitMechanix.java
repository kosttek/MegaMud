package pl.edu.agh.megamud.mechanix;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.ATR;

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
	
	//TODO
	static public void attack(Creature attacker, Creature Defender){
		Defender.addDamage(10);
	}
	
	static public String creatureAttr[] = {Attribute.DEXTERITY,Attribute.STRENGTH};
	
	static public boolean isCreatureAttribute(Attribute attr){
		for(String str : creatureAttr)
			if(str.equals(attr.getName()))
				return true;
		return false;
	}
}
