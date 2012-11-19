package pl.edu.agh.megamud.base;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import pl.edu.agh.megamud.dao.Attribute;

/**
 * This class implements a most simple Modifier that can be used. It changes a specified creature's attribute by adding/removing points. E.g. POWER +5.
 * After a specified time, the modifier will wear off.
 */
public class SimpleModifier implements Modifier{
	private Creature creature;
	private String name;
	private String stat;
	private long diff;
	private Behaviour beh=null;
	
	/**
	 * We need this behaviour to wear the modifiers effect of after some time.
	 */
	private class SelfDestructBehaviour extends Behaviour{
		public SelfDestructBehaviour(Creature o, long delay) {
			super(o, delay);
		}

		protected void action(){
			Creature creature=(Creature)getOwner();
			creature.removeModifier(SimpleModifier.this);
		}
	}
	
	public SimpleModifier(Creature creature,String stat,long diff,long delay){
		this.creature=creature;
		this.name=""+stat+" "+(diff>0 ? "+" : "")+diff+"";
		this.stat=stat;
		this.diff=diff;
		if(delay>0){
			beh=new SelfDestructBehaviour(creature,delay);
			beh.put();
		}
	}
	
	public Creature getCreature(){
		return creature;
	}
	
	public Behaviour willSelfDestruct(){
		return beh;
	}
	
	public String getName(){
		return name;
	}
	
	/**
	 * @todo What if there is no such attribute?
	 */
	public boolean modify(Creature c,Map<Attribute,Long> attrs){
		for(Iterator<Entry<Attribute,Long>> set=attrs.entrySet().iterator();set.hasNext();){
			Entry<Attribute,Long> next=set.next();
			Attribute a=next.getKey();
			if(a.getName().equals(stat)){
				Long l=next.getValue();
				next.setValue(new Long(l.longValue()+diff));
				return true;
			}
		}return true;
	}

	public void onBegin() {
		creature.write("You have been given a modifier "+stat+" "+(diff>0 ? "+" : "")+diff+"!!!");
	}

	public void onStop() {
		creature.write("Modifier "+stat+" "+(diff>0 ? "+" : "")+diff+" expired.");		
	}
}
