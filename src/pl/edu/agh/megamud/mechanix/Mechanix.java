package pl.edu.agh.megamud.mechanix;

import java.util.ArrayList;
import java.util.List;

import javax.smartcardio.ATR;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.itemtype.Body;
import pl.edu.agh.megamud.base.itemtype.Hand;
import pl.edu.agh.megamud.base.itemtype.Head;
import pl.edu.agh.megamud.base.itemtype.ItemToWorn;
import pl.edu.agh.megamud.dao.Attribute;

public class Mechanix {
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
		Long stregth = attacker.getAttributeValue(Attribute.STRENGTH);
		if(stregth == null){ System.out.println("null !! panie null!! nie ma sily !!"); return;}
		
		int weaponDamage=0 ;
		if(attacker.getEquipment().containsKey(Hand.class)){
			ItemToWorn item = attacker.getEquipment().get(Hand.class);
			if(item != null){
				Long tempDamageL ;
				tempDamageL = item.getAttributeValue(Attribute.DAMAGE);
				weaponDamage = tempDamageL==null? 0 : tempDamageL.intValue();
			}
		}	
		
		int damage = stregth.intValue() + weaponDamage;
		Defender.addDamage(damage);
	}
	
	static public String creatureAttr[] = {Attribute.DEXTERITY,Attribute.STRENGTH};
	
	static public boolean isCreatureAttribute(Attribute attr){
		for(String str : creatureAttr)
			if(str.equals(attr.getName()))
				return true;
		return false;
	}
}
