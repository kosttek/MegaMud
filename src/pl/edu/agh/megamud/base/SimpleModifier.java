package pl.edu.agh.megamud.base;

import java.util.Map;

/*
 * This class implements a most simple Modifier that can be used. It changes a specified creature's attribute by adding/removing points. E.g. POWER +5.
 * After a specified time, the modifier will wear off.
 */
public class SimpleModifier implements Modifier{
	private Creature creature;
	private String name;
	private String stat;
	private long diff;
	private Behaviour beh=null;
	
	/*
	 * We need this to wear the modifiers effect of after some time.
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
	
	public SimpleModifier(Creature creature,String name,String stat,long diff,long delay){
		this.creature=creature;
		this.name=name;
		this.stat=stat;
		this.diff=diff;
		if(delay>0){
			beh=new SelfDestructBehaviour(creature,delay);
			beh.put();
		}
		
		creature.addModifier(this);
	}
	
	public Creature getCreature(){
		return creature;
	}
	
	public Behaviour selfDestruct(){
		return beh;
	}
	
	public String getName(){
		return name;
	}
	
	/*
	 * @todo What if there is no such attribute?
	 */
	public boolean modify(Creature c,Map<String,Long> attrs){
		if(attrs.containsKey(stat)){
			Long l=attrs.get(stat);
			attrs.put(stat,new Long(l.longValue()+diff));
		}
		return true;
	}

	public void onBegin() {
		creature.write("You have been given a modifier "+stat+" "+(diff>0 ? "+" : "")+diff+"!!!");
	}

	public void onStop() {
		creature.write("Modifier "+stat+" "+(diff>0 ? "+" : "")+diff+" expired.");		
	}
}
