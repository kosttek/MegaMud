package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.PlayerCreature;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class CreatureItemBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private PlayerCreature creature;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Item item;	

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer level;
	
	public PlayerCreature getCreature() {
		return creature;
	}

	public void setCreature(PlayerCreature creature) {
		this.creature = creature;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public CreatureItemBase(){
		
	}
	
	public static Dao<CreatureItem, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), CreatureItem.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
