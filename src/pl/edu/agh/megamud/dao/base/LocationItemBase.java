package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.LocationItem;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class LocationItemBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(foreign = true, canBeNull = true, foreignAutoRefresh = true)
	private Location location;

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Item item;

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer level;

	
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

	public LocationItemBase() {

	}

	public static Dao<LocationItem, Integer> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					LocationItem.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
