package pl.edu.agh.megamud.base;

/**
 * Abstraction of an physical item inside a game. An item can be a weapon, a stone or anything.
 * @author Tomasz
 */
public abstract class Item {
	
	/**
	 * In-database ID.
	 */
	protected String id;
	
	/**
	 * Description.
	 */
	protected String description;
	
	public String getDescription() {
		return description;
	}
	
	public Item(String id,String description){
		this.id=id;
		this.description=description;
	}

	/**
	 * An owner, if any.
	 */
	protected ItemHolder owner=null;
	
	public String getId() {
		return id;
	}

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
	protected abstract boolean canBeGivenTo(ItemHolder owner);
}
