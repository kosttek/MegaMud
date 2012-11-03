package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import pl.edu.agh.megamud.dao.Item;

/**
 * A "creature", object/person that we can interact with.
 */
public class Creature extends ItemHolder{
	/**
	 * Its name.
	 */
	protected String name;
	/**
	 * Its brain.
	 */
	protected Controller controller=null;
	/**
	 * Its current location. MUST be one.
	 */
	protected Location location=null;
	
	/**
	 * Class ID from database.
	 */
	protected String klass;
	protected int hp=100;
	protected int maxHp=100;

	protected int level=0;
	protected int exp=0, expNeeded=0;
	protected Map<String,Long> attributes=new HashMap<String,Long>();
	protected List<Modifier> modifiers=new LinkedList<Modifier>();
	
	public void setName(String name) {
		this.name = name;
	}
	public void setInitialLocation(Location location) {
		this.location = location;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
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
	public void setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
	}
	public List<Modifier> getModifiers() {
		return modifiers;
	}
	public int getMaxHp() {
		return maxHp;
	}
	public String getKlass() {
		return klass;
	}
	public void setKlass(String propClass) {
		this.klass = propClass;
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
	public Map<String, Long> getAttributes() {
		return attributes;
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
	
	/**
	 * Executed upon "login" command or after creating a NPC (manually). Binds a creature to a controller and shares its commands.
	 */
	public void connect(Controller controller){
		this.controller=controller;
		controller.setCreature(this);
		
		for(Command cmd:getAllCommands()){
			controller.addCommand(cmd);
		}
	}
	/**
	 * Disconnects this creature from the controller.
	 */
	public void disconnect(){
		for(Command cmd:getAllCommands()){
			controller.removeCommand(cmd);
		}
		this.controller=null;
	}
	
	public final Location getLocation(){
		return location;
	}
	
	/**
	 * Moves creature to other location. Optionally provide an exit name for notifications.
	 * @todo Write this to database.
	 */
	public final void setLocation(Location exit,String exitName){
		if(location!=null){
			for(Command cmd:location.getAllCommands()){
				removeCommand(cmd);
				controller.removeCommand(cmd);
			}
			
			location.onRemoveCreature(this,exitName);
		}
		
		location=exit;
		
		if(exit!=null){
			for(Command cmd:location.getAllCommands()){
				addCommand(cmd);
				controller.addCommand(cmd);
			}
			location.onAddCreature(this);
		}
	}
	
	/**
	 * Tries to move to a specified exit.
	 * @return true, if succedeed. 
	 */
	public boolean moveTo(String exit){
		Map<String,Location> exits=location.getExits();
		if(exits.containsKey(exit)){
			setLocation(exits.get(exit),exit);
			return true;
		}
		return false;
			
	}
	
	/**
	 * Use this to give an experience points to a creature. This can change creature's level (when after change exp>=expNeeded).
	 * @todo change level, change stats, force class change upon some level/block change
	 * @todo Write this to database
	 */
	public void giveExp(int num){
		this.exp+=num;
		while(this.exp>=this.expNeeded){
			//TODO sensowne wartoci?
			this.exp-=this.expNeeded;
			this.expNeeded*=1.2;
			this.level++;
		}
	}
	
	/**
	 * Use this to give or take HP points.
	 * @todo dying
	 * @todo Write this to database
	 */
	public void giveHp(int num){
		this.hp+=num;
		if(this.hp<=0){
			//TODO die
		}
		if(this.hp>=this.maxHp){
			this.hp=this.maxHp;
		}
	}
	
	/**
	 * Adds a temporary attributes modifier.
	 * @todo Write this to database
	 */
	public void addModifier(Modifier m){
		this.modifiers.add(m);
		m.onBegin();
	}
	
	/**
	 * Removes a temporary attributes modifier.
	 * @todo Write this to database
	 */
	public void removeModifier(Modifier m){
		this.modifiers.remove(m);
		m.onStop();
	}
	
	/**
	 * Generates a temporary (at a moment of generation) attributes of a creature. Map attribute-id -> value.
	 * Calculation:
	 * - get base attributes of a creature;
	 * - apply modifiers;
	 * - return the result.
	 */	
	protected Map<String,Long> generateAttributes(){
		Map<String,Long> cur=new HashMap<String,Long>();
		cur.putAll(attributes);
		
		for(ListIterator<Modifier> i=modifiers.listIterator();i.hasNext();){
			Modifier m=i.next();
			if(!m.modify(this, cur)){
				m.onStop();
				i.remove();
			}
		}
		return cur;
	}
	
	/**
	 * Use this to send a message to a creature.
	 */
	public void write(String s){
		controller.write(s);
	}
	
	public void onItemAppear(Item i,ItemHolder from){
		getController().onItemAppear(i, from);
	}
	public void onItemDisappear(Item i,ItemHolder to){
		getController().onItemDisappear(i, to);
	}
}
