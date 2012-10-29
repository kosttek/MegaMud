package pl.edu.agh.megamud.base;

import java.util.Map;

/*
 * A temporary modifier to creature's attributes.
 * Works like that:
 * - creature has basic attributes/stats
 * - creature gets a modifier, in a fight or as a result of an item
 * - everytime attributes are needed (= nearly always), a modifier can modify temporary stats of a creature
 * - it can eventually self destruct (if for example it is a 30sec modifier)
 * Examples: an apple can give +5 strength for 5 minutes.
 */
public class SimpleModifier implements Modifier{
	private Creature creature;
	private String name;
	private String stat;
	private long diff;
	private Behaviour beh=null;
	
	private class SelfDestructBehaviour extends Behaviour{
		public SelfDestructBehaviour(Creature o, long delay) {
			super(o, delay);
			System.out.println("!!!!");
		}

		protected void action(){
			System.out.println("action");
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
