package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Abstraction of an object, that can hold items.
 * @author Tomasz
 *
 */
public abstract class ItemHolder {
	protected Map<String,Item> items=new HashMap<String,Item>();
	
	/**
	 * Executed after an item was added here.
	 * @param i
	 */
	public void addItem(Item i){
		items.put(i.getId(),i);
	}
	/**
	 * Executed after an item was removed from here.
	 * @param i
	 */
	public void removeItem(Item i){
		items.remove(i.getId());
	}
	
	public Map<String,Item> getItems(){
		return items;
	}
	
	/**
	 * Event executed after this item appears in our inventory.
	 * @param i Item.
	 * @param from Who gave this us, or null, if magically appeared.
	 */
	public abstract void onItemAppear(Item i,ItemHolder from);
	
	/**
	 * Event executed after this item disappears from our inventory.
	 * @param i Item.
	 * @param from Who gave this us, or null, if magically destroyed.
	 */
	public abstract void onItemDisappear(Item i,ItemHolder to);
}
