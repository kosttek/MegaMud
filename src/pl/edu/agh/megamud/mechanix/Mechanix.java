/*******************************************************************************
 * Copyright (c) 2012, AGH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package pl.edu.agh.megamud.mechanix;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	 * 
	 * @param itemHolder
	 */
	public static void initEquipment(ItemHolder itemHolder) {
		List<Class<? extends ItemToWorn>> list = new ArrayList<Class<? extends ItemToWorn>>();
		list.add(Hand.class);
		list.add(Head.class);
		list.add(Body.class);

		itemHolder.setEquipmentTypes(list);
	}

	public static void attack(Creature attacker, Creature defender) {
		Long stregth = attacker.getAttributeValue(Attribute.STRENGTH);
		Long dexterity = attacker.getAttributeValue(Attribute.DEXTERITY);
		if (stregth == null) {
			System.out.println("null !! panie null!! nie ma sily !!");
			return;
		}

		if(dexterity == null){
			dexterity = 10L;
		}
		
		
		
		int blockDmg = new Long(block(attacker, defender, dexterity)).intValue();
		int weaponDamage = 0;
		if (attacker.getEquipment().containsKey(Hand.class)) {
			ItemToWorn item = attacker.getEquipment().get(Hand.class);
			if (item != null) {
				Long tempDamageL;
				tempDamageL = item.getAttributeValue(Attribute.DAMAGE);
				weaponDamage = tempDamageL == null ? 0 : tempDamageL.intValue();
				
			}
		}

		int damage = stregth.intValue() + weaponDamage;
		damage = damage - blockDmg;
		if(damage <0)
			damage = 0;
		defender.addDamage(damage);
	}
	
	public static Long block(Creature att, Creature def, Long dex){
		Random rand = new Random();
		int randInt = rand.nextInt(2);
		if(randInt == 0){
			att.write("Your opponent has blocked "+dex+" points of damage!");
			def.write("You blocked "+dex+" points of damage!");
			return dex;
		}
		return 0L;
	}

	public static String creatureAttr[] = { Attribute.DEXTERITY,
			Attribute.STRENGTH };

	public static boolean isCreatureAttribute(Attribute attr) {
		for (String str : creatureAttr)
			if (str.equals(attr.getName()))
				return true;
		return false;
	}
}
