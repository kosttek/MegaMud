package pl.edu.agh.megamud.dao;

/**
 * Abstraction of an physical item inside a game. An item can be a weapon, a stone or anything.
 * @author Tomasz, Lukasz
 */

import pl.edu.agh.megamud.dao.base.ItemBase;

import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "item")
public class Item extends ItemBase {

	public Item(String name, String description) {
		super.setName(name);
		super.setDescription(description);
	}

	public Item() {
	}

}