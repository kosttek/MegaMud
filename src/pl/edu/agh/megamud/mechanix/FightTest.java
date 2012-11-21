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

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.itemtype.Weapon;
import pl.edu.agh.megamud.dao.Attribute;

public class FightTest {

	@Before
	public void setUp() throws Exception {
		GameServer.getInstance();
	}

	@After
	public void tearDown() throws Exception {

	}

	@Test
	public void test() {
		Location loc = GameServer.getInstance().getStartLocation();

		Weapon sword = new Weapon("sword", "little rusty sword");
		setAttributeForTest(sword, Attribute.DAMAGE, 3L);

		Creature c = new Creature("c1").setHp(100);
		c.connect(new NPCController());
		setAttributeForTest(c, Attribute.STRENGTH, 10L);
		Mechanix.initEquipment(c);

		c.getItems().put(sword.getName(), sword);
		c.equip(sword);

		FightBehaviour fb1 = new FightBehaviour(c);
		c.addBehaviour(fb1);

		Creature c2 = new Creature("c2").setHp(30);
		c2.connect(new NPCController());
		setAttributeForTest(c2, Attribute.STRENGTH, 10L);

		FightBehaviour fb2 = new FightBehaviour(c2);
		c.addBehaviour(fb2);
		c.setLocation(loc, null);
		c2.setLocation(loc, null);
		Mechanix.attack(c, c2);

		Assert.assertEquals(17, c2.getHp());

	}

	private void setAttributeForTest(Creature creature, String attrname,
			Long value) {
		for (Attribute attr : creature.getAttributes().keySet()) {
			if (attr.getName().equals(attrname)) {
				creature.getAttributes().put(attr, value);
				return;
			}
		}
		Attribute newAttr = new Attribute();
		newAttr.setName(attrname);
		creature.getAttributes().put(newAttr, value);
	}

	private void setAttributeForTest(Item item, String attrname, Long value) {
		for (Attribute attr : item.getAttributes().keySet()) {
			if (attr.getName().equals(attrname)) {
				item.getAttributes().put(attr, value);
				return;
			}
		}
		Attribute newAttr = new Attribute();
		newAttr.setName(attrname);
		item.getAttributes().put(newAttr, value);
	}
}
