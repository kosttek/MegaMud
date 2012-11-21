/*******************************************************************************
 * Copyright (c) 2012, AGH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.base.itemtype.ItemToWorn;

/**
 * Abstraction of an object, that can hold items.
 * 
 * @author Tomasz
 * 
 */
public class ItemHolder extends CommandCollector {
	protected Map<String, Item> items = new HashMap<String, Item>();
	protected Map<Class<? extends ItemToWorn>, ItemToWorn> equipment = new HashMap<Class<? extends ItemToWorn>, ItemToWorn>();

	/**
	 * Executed after an item was added here.
	 * 
	 * @param i
	 */
	public void addItem(Item i) {
		items.put(i.getName(), i);
	}

	/**
	 * Executed after an item was removed from here.
	 * 
	 * @param i
	 */
	public void removeItem(Item i) {
		items.remove(i.getName());
	}

	public Map<String, Item> getItems() {
		return items;
	}

	/**
	 * Event executed after this item appears in our inventory.
	 * 
	 * @param i
	 *            Item.
	 * @param from
	 *            Who gave this us, or null, if magically appeared.
	 */
	public void onItemAppear(Item i, ItemHolder from) {
	}

	/**
	 * Event executed after this item disappears from our inventory.
	 * 
	 * @param i
	 *            Item.
	 * @param from
	 *            Who gave this us, or null, if magically destroyed.
	 */
	public void onItemDisappear(Item i, ItemHolder to) {
	}

	/**
	 * Event executed after this item is equiped.
	 * 
	 * @param i
	 *            Item.
	 */
	public void onItemEquip(Item i) {
	}

	/**
	 * Event executed after this item is unequiped.
	 * 
	 * @param i
	 *            Item.
	 */
	public void onItemUnequip(Item i) {
	}

	/**
	 * Its set how equpiment you can use
	 * 
	 * @param item
	 *            of classes you can have items on like Head, Hand or Body.
	 * 
	 */
	public void setEquipmentTypes(List<Class<? extends ItemToWorn>> list) {
		for (Class<? extends ItemToWorn> clazz : list) {
			equipment.put(clazz, null);
		}
	}

	public void equip(ItemToWorn item) {
		for (Class<? extends ItemToWorn> clazz : equipment.keySet()) {
			if (clazz.isAssignableFrom(item.getClass())) {
				equipment.put(clazz, item);
			}
		}

	}

	public void unequip(ItemToWorn item) {
		for (Class<? extends ItemToWorn> clazz : equipment.keySet()) {
			if (clazz.isAssignableFrom(item.getClass())) {
				equipment.put(clazz, null);
			}
		}
	}

	public Map<Class<? extends ItemToWorn>, ItemToWorn> getEquipment() {
		return this.equipment;
	}
}
