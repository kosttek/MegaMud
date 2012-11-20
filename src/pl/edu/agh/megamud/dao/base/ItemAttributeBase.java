package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.ItemAttribute;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class ItemAttributeBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer level;

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer value;

	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Attribute attribute;

	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Item item;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getId() {
		return id;
	}

	public ItemAttributeBase() {

	}

	public static Dao<ItemAttribute, Integer> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					ItemAttribute.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
