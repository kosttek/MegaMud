package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.base.itemtype.ItemToWorn;

/**
 * Abstraction of an object, that can hold items.
 * @author Tomasz
 *
 */
public class ItemHolder extends CommandCollector {
	protected Map<String,Item> items=new HashMap<String,Item>();
	protected Map<Class<ItemToWorn>,ItemToWorn> equipment = new HashMap<Class<ItemToWorn>, ItemToWorn>();
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
	/**
	 * Its set how equpiment you can use
	 * @param item of classes you can have items on like Head, Hand or Body.
	 * 
	 */
	public void setEquipmentTypes(List<Class> list){
		for(Class clazz : list){
			equipment.put(clazz, null);
		}
	}
	
	public void equip(ItemToWorn item){
		for(Class<ItemToWorn> clazz:equipment.keySet()){
			if(clazz.isAssignableFrom(item.getClass())){
				equipment.put(clazz, item);
			}
		}
		
	}
	
	public void unequip(ItemToWorn item){
		for(Class<ItemToWorn> clazz:equipment.keySet()){
			if(clazz.isAssignableFrom(item.getClass())){
				equipment.put(clazz, null);
			}
		}
	}
	
	public Map<Class<ItemToWorn>,ItemToWorn> getEquipment(){
		return this.equipment;
	}
}
