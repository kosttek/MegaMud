package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public abstract class ItemBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer max_level;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<CreatureItem> creatureItems;

	@ForeignCollectionField(eager = true)
	private ForeignCollection<ItemAttribute> itemAttributes;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMax_level() {
		return max_level;
	}

	public void setMax_level(Integer max_level) {
		this.max_level = max_level;
	}

	public ForeignCollection<CreatureItem> getCreatureItems() {
		return creatureItems;
	}

	public void setCreatureItems(ForeignCollection<CreatureItem> creatureItems) {
		this.creatureItems = creatureItems;
	}	
	
	public ForeignCollection<ItemAttribute> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(ForeignCollection<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public ItemBase(){
		
	}
	
	public static Dao<Item, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Item.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
