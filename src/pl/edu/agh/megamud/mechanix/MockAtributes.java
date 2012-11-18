package pl.edu.agh.megamud.mechanix;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.dao.Attribute;

public class MockAtributes {
	public static void setAttributesToCreature(Creature creature){
		Map<String,Long> map= new HashMap<String,Long>();
		for(String attrString : Attribute.attrs){
			map.put(attrString, 10L);
		}
		creature.setAttributes(map);
	}
}
