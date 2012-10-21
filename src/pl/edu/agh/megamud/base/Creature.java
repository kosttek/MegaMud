package pl.edu.agh.megamud.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;


public class Creature extends CommandCollector{
	protected String name;
	protected Controller controller=null;
	protected Location location=null;
	
	protected String propClass;
	protected int hp=100;
	protected int level=0;
	protected int exp=0, expNeeded=0;
	protected Map<String,Long> propAttributes=new HashMap<String,Long>();
	protected List<Modifier> modifiers=new LinkedList<Modifier>();
	
	public void setHp(int hp) {
		this.hp = hp;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public void setExpNeeded(int expNeeded) {
		this.expNeeded = expNeeded;
	}
	public String getPropClass() {
		return propClass;
	}
	public void setPropClass(String propClass) {
		this.propClass = propClass;
	}
	public int getHp() {
		return hp;
	}
	public int getLevel() {
		return level;
	}
	public int getExp() {
		return exp;
	}
	public int getExpNeeded() {
		return expNeeded;
	}
	public Map<String, Long> getPropAttributes() {
		return propAttributes;
	}
	
	public final Controller getController(){
		return controller;
	}
	public final String getName(){
		return name;
	}
	
	public Creature(String name){
		this.name=name;
	}
	
	public void connect(Controller controller){
		this.controller=controller;
		controller.setCreature(this);
		
		for(String cmd:getAllCommands().keySet()){
			controller.addCommand(cmd);
		}
	}
	public void disconnect(){
		for(String key:getAllCommands().keySet()){
			controller.removeCommand(key);
		}
		this.controller=null;
	}
	
	public final Location getLocation(){
		return location;
	}
	
	public final void setLocation(Location exit,String exitName){
		if(location!=null){
			for(String key:location.getAllCommands().keySet()){
				removeCommand(key);
				controller.removeCommand(key);
			}
			
			location.removeCreature(this,exitName);
		}
		
		location=exit;
		
		if(exit!=null){
			for(String cmd:exit.getAllCommands().keySet()){
				addCommand(cmd);
				controller.addCommand(cmd);
			}
			location.putCreature(this);
		}
	}
	
	public boolean moveTo(String exit){
		Map<String,Location> exits=location.getExits();
		if(exits.containsKey(exit)){
			setLocation(exits.get(exit),exit);
			return true;
		}
		return false;
			
	}
	
	public void giveExp(int num){
		this.exp+=num;
		if(this.exp>=this.expNeeded){
			//TODO sensowne wartoci?
			this.exp-=this.expNeeded;
			this.expNeeded*=1.2;
			this.level++;
		}
	}
	public void giveHp(int num){
		this.hp+=num;
		if(this.hp<=0){
			//TODO die
		}
	}
	public void addModifier(Modifier m){
		this.modifiers.add(m);
		m.onBegin();
	}
	public void removeModifier(Modifier m){
		this.modifiers.remove(m);
		m.onStop();
	}
	protected Map<String,Long> generateAttributes(){
		Map<String,Long> cur=new HashMap<String,Long>();
		cur.putAll(propAttributes);
		
		for(ListIterator<Modifier> i=modifiers.listIterator();i.hasNext();){
			Modifier m=i.next();
			if(!m.modify(this, cur)){
				m.onStop();
				i.remove();
			}
		}
		return cur;
	}
	public void write(String s){
		controller.write(s);
	}
}
