package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.megamud.dao.Item;

/**
 * Abstraction of an object, that can hold items.
 * @author Tomasz
 *
 */
public class ItemHolder extends CommandCollector {
	protected Map<String,Item> items=new HashMap<String,Item>();
	
	/**
	 * Executed after an item was added here.
	 * @param i
	 */
	public void addItem(Item i){
		items.put(i.getName(),i);
	}
	/**
	 * Executed after an item was removed from here.
	 * @param i
	 */
	public void removeItem(Item i){
		items.remove(i.getName());
	}
	
	public Map<String,Item> getItems(){
		return items;
	}
	
	/**
	 * Event executed after this item appears in our inventory.
	 * @param i Item.
	 * @param from Who gave this us, or null, if magically appeared.
	 */
	public void onItemAppear(Item i,ItemHolder from){}
	
	/**
	 * Event executed after this item disappears from our inventory.
	 * @param i Item.
	 * @param from Who gave this us, or null, if magically destroyed.
	 */
	public void onItemDisappear(Item i,ItemHolder to){}
}
