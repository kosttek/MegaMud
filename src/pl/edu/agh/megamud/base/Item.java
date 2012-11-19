package pl.edu.agh.megamud.base;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.ItemAttribute;
import pl.edu.agh.megamud.dao.PlayerCreature;

/**
 * Physical, existing item.
 * @author Tomasz
 *
 */
public class Item implements BehaviourHolderInterface{
	/**
	 * In-database representation of this item.
	 */
	protected CreatureItem creatureItem=null;
	
	/**
	 * In-game owner of this item.
	 */
	protected ItemHolder owner=null;
	
	private Map<Attribute,Long> attributes=new HashMap<Attribute,Long>();
	
	protected String name;
	protected String description;
	
	private BehaviourHolder behaviourHolder = new BehaviourHolder();
	
	public Long getAttributeValue(String x){
		if(attributes.containsKey(x))
			return attributes.get(x);
		return null;
	}
	
	public void setAttribute(String x,Long val){
		for(Iterator<Entry<Attribute,Long>> set=attributes.entrySet().iterator();set.hasNext();){
			Entry<Attribute,Long> next=set.next();
			Attribute a=next.getKey();
			if(a.getName().equals(x)){
				next.setValue(val);
				try{
					ItemAttribute.createDao().deleteBuilder().where().eq("attribute",a).and().eq("item", this.creatureItem.getItem()).query();
					
					ItemAttribute ne=new ItemAttribute();
					ne.setAttribute(a);
					ne.setItem(this.creatureItem.getItem());
					ne.setLevel(1);
					ne.setValue(val.intValue());
					ItemAttribute.createDao().create(ne);
					
					System.out.println(""+x+" = "+val);
				}catch(SQLException e){
					e.printStackTrace();
				}
				return;
			}
		}
		//@todo save to db
	}
	
	public Item(CreatureItem it){
		setCreatureItem(it);
	}
	
	public CreatureItem getCreatureItem(){
		return this.creatureItem;
	}
	
	public void setCreatureItem(CreatureItem it){
		this.creatureItem=it;
		
		this.name=it.getItem().getName();
		this.description=it.getItem().getDescription();
		
		List<Attribute> attrs;
		try {
			attrs = Attribute.createDao().queryForAll();
			for(Iterator<Attribute> i=attrs.iterator();i.hasNext();){
				Attribute a=i.next();
				System.out.println(a);
				List<ItemAttribute> found=ItemAttribute.createDao().queryBuilder().where().eq("attribute",a).and().eq("item", it).query();
				System.out.println("    "+found);
				if(found.size()>0){
					ItemAttribute first=found.get(0);
					this.attributes.put(a,first.getValue().longValue());
				}else{
					this.attributes.put(a,Long.valueOf(0L));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Item(String name,String description){
		this.name=name;
		this.description=description;
	}
	
	public ItemHolder getOwner() {
		return owner;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
	
	/**
	 * Use this to move this item to other item holder (creature or location) - especially from/to null, when item is magically (dis)appearing. 
	 * @todo Write this to database.
	 * @param owner
	 * @return True, if given.
	 */	
	public boolean giveTo(ItemHolder newOwner){
		ItemHolder oldOwner=owner;
		
		if(!canBeGivenTo(newOwner)){
			return false;
		}
		
		if(oldOwner!=null){
			oldOwner.removeItem(this);
			oldOwner.onItemDisappear(this,newOwner);
		}
	
		if(creatureItem!=null){
			creatureItem.setCreature(null);
			// @todo
			//creatureItem.setLocation(null);
		}
		
		this.owner=newOwner;
		
		if(newOwner!=null){
			newOwner.addItem(this);
			newOwner.onItemAppear(this,oldOwner);
			
			if(creatureItem!=null){
				if(newOwner instanceof Location){
					// @todo No location in dao.
					//creatureItem.setLocation((Location)newOwner);
				}else{
					// @todo Import it
					PlayerCreature pc=((Creature)newOwner).getDbCreature();
					if(pc!=null){
						creatureItem.setCreature(pc);
					}
				}
			}
		}
		

		// @todo commit
		
		return true;
	}	
	
	/**
	 * Internal check, whether this item can be held by a creature. It can depend on its stats (class, some attribute's value) or other held items.
	 * @param owner
	 */
	public boolean canBeGivenTo(ItemHolder owner){
		return false; // TODO Add some logic.
	}
	@Override
	public List<Behaviour> getBehaviourList() {
		return behaviourHolder.getBehaviourList();
	}

	@Override
	public void setBehaviourList(List<Behaviour> list) {
		behaviourHolder.setBehaviourList(list);
	}

	@Override
	public void addBehaviour(Behaviour behaviour) {
		behaviourHolder.addBehaviour(behaviour);
	}

	@Override
	public void removeBehaviour(Behaviour behaviour) {
		behaviourHolder.removeBehaviour(behaviour);
		
	}

	@Override
	public List<Behaviour> getBehaviourByType(Class clazz) {
		return behaviourHolder.getBehaviourByType(clazz);
	}
}
