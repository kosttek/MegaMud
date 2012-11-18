package pl.edu.agh.megamud.base;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import com.j256.ormlite.dao.ForeignCollection;

import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;
import pl.edu.agh.megamud.mechanix.MockAtributes;

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
	 * In-database representation.
	 */
	protected PlayerCreature dbCreature;
	
	protected Profession profession=Profession.DEFAULT;
	private int level;
	private int hp;
	private int expNeeded;
	private int exp;
	
	public PlayerCreature getDbCreature() {
		return dbCreature;
	}
	
	public void setDbCreature(PlayerCreature dbCreature) {
		this.dbCreature = dbCreature;
		
		this.profession=dbCreature.getProfession();
		this.level=dbCreature.getLevel();
		this.hp=dbCreature.getHp();
		this.exp=dbCreature.getExp();
		this.expNeeded=dbCreature.getExp_needed();
		
		ForeignCollection<CreatureAttribute> creatureAttributes =  dbCreature.getCreatureAttributes();

		if(creatureAttributes==null || creatureAttributes.isEmpty()){
			MockAtributes.setAttributesToCreature(this);
		}
		//TODO get atributes form database and set them to creature !
	}
	
	// @todo integrate with db
	private Map<String,Long> attributes=new HashMap<String,Long>();
	protected List<Modifier> modifiers=new LinkedList<Modifier>();
	
	public Creature setName(String name) {
		this.name=name;
		if(this.dbCreature!=null){
			this.dbCreature.setName(name);
			this.commit();
		}
		return this;
	}
	
	public void commit(){
		if(this.dbCreature==null)
			return;
		try{
			PlayerCreature.createDao().update(this.dbCreature);
		}catch(SQLException e){
			e.printStackTrace();
		}
	}
	public Creature setHp(int hp) {
		this.hp=hp;
		if(this.dbCreature!=null){
			this.dbCreature.setHp(hp);
			this.commit();
		}
		return this;
	}
	public Creature setLevel(int level) {
		this.level=level;
		if(this.dbCreature!=null){
			this.dbCreature.setLevel(level);
			this.commit();
		}
		return this;
	}
	public Creature setExp(int exp) {
		this.exp=exp;
		if(this.dbCreature!=null){
			this.dbCreature.setHp(exp);
			this.commit();
		}
		return this;
	}
	public Creature setExpNeeded(int expNeeded) {
		this.expNeeded=expNeeded;
		if(this.dbCreature!=null){
			this.dbCreature.setExp_needed(expNeeded);
			this.commit();
		}
		return this;
	}
	
	public Creature setModifiers(List<Modifier> modifiers) {
		this.modifiers = modifiers;
		return this;
	}
	public List<Modifier> getModifiers() {
		return this.modifiers;
	}
	
	public Creature setProfession(Profession profession) {
		this.profession=profession;
		if(this.dbCreature!=null){
			this.dbCreature.setProfession(profession);
			this.commit();
		}
		return this;
	}
	
	public int getHp() {
		return this.hp;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public int getExp() {
		return this.exp;
	}
	public int getExpNeeded() {
		return this.expNeeded;
	}
	public Map<String, Long> getAttributes() {
		return this.attributes;
	}
	public void setAttributes(Map<String,Long> attributes) {
		this.attributes = attributes;
	}
	public final Controller getController(){
		return this.controller;
	}
	public String getName(){
		return this.name;
	}
	public Profession getProfession(){
		return this.profession;
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
	public void setLocation(Location exit,String exitName){
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
		//@todo write to db
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
		int exp=this.getExp();
		int expn=this.getExpNeeded();
		int lev=this.getLevel();
		
		exp+=num;
		while(exp>=expn){
			//TODO sensowne wartoci?
			exp-=expn;
			expn*=1.2;
			lev++;
		}
		
		this.setExp(exp);
		this.setExpNeeded(expn);
		this.setLevel(lev);
		this.commit();
	}
	
	/**
	 * Use this to give or take HP points.
	 * @todo dying
	 * @todo Write this to database
	 */
	public void giveHp(int num){
		int hp=this.getHp();
		hp+=num;
		if(hp<=0){
			//TODO die
		}
		this.setHp(hp);
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
		cur.putAll(getAttributes());
		
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
