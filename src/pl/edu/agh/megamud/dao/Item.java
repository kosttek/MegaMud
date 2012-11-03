package pl.edu.agh.megamud.dao;

/**
 * Abstraction of an physical item inside a game. An item can be a weapon, a stone or anything.
 * @author Tomasz, Lukasz
 */

import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.dao.base.ItemBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "item")
public class Item extends ItemBase{
	
	public Item(String name,String description){
		super.setName(name);
		super.setDescription(description);
	}
	
	public Item() {}
	
	protected ItemHolder owner=null;
	
	public ItemHolder getOwner() {
		return owner;
	}
	
	/**
	 * Use this to move this item to other item holder (creature or location) - especially from/to null, when item is magically (dis)appearing. 
	 * @todo Write this to database.
	 * @param owner
	 * @return True, if given.
	 */	
	public boolean giveTo(ItemHolder newOwner){
		ItemHolder oldOwner=owner;
		
		if(!canBeGivenTo(newOwner))
			return false;
		
		if(oldOwner!=null){
			oldOwner.removeItem(this);
			oldOwner.onItemDisappear(this,newOwner);
		}
		
		this.owner=newOwner;
		if(newOwner!=null){
			newOwner.addItem(this);
			newOwner.onItemAppear(this,oldOwner);
		}
		
		return true;
	}	
	
	/**
	 * Internal check, whether this item can be held by a creature. It can depend on its stats (class, some attribute's value) or other held items.
	 * @param owner
	 */
	protected boolean canBeGivenTo(ItemHolder owner){
		return false; // TODO Add some logic.
	}
}