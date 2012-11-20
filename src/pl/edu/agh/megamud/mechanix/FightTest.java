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
		Location loc= GameServer.getInstance().getStartLocation();

		Weapon sword = new Weapon("sword", "little rusty sword");
		setAttributeForTest(sword,Attribute.DAMAGE, 3L);

		
		Creature c = new Creature("c1").setHp(100);
		c.connect(new NPCController());
		setAttributeForTest(c,Attribute.STRENGTH, 10L);
		Mechanix.initEquipment(c);
		
		c.getItems().put(sword.getName(),sword);
		c.equip(sword);
		
		FightBehaviour fb1 = new FightBehaviour(c);
		c.addBehaviour(fb1);
		
		Creature c2 = new Creature("c2").setHp(30);
		c2.connect(new NPCController());
		setAttributeForTest(c2,Attribute.STRENGTH, 10L);
		
		FightBehaviour fb2 = new FightBehaviour(c2);
		c.addBehaviour(fb2);
		c.setLocation(loc, null);
		c2.setLocation(loc, null);
		Mechanix.attack(c, c2);
		
		Assert.assertEquals( 17,c2.getHp());
		

	}

	private void setAttributeForTest(Creature creature, String attrname , Long value){
		for(Attribute attr : creature.getAttributes().keySet()){
			if(attr.getName().equals(attrname)){
				creature.getAttributes().put(attr, value);
				return;
			}
		}
		Attribute newAttr = new Attribute();
		newAttr.setName(attrname);
		creature.getAttributes().put(newAttr, value);
	}
	private void setAttributeForTest(Item item, String attrname , Long value){
		for(Attribute attr : item.getAttributes().keySet()){
			if(attr.getName().equals(attrname)){
				item.getAttributes().put(attr, value);
				return;
			}
		}
		Attribute newAttr = new Attribute();
		newAttr.setName(attrname);
		item.getAttributes().put(newAttr, value);
	}
}
