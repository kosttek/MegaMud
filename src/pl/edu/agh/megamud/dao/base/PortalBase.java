package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.Portal;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class PortalBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Location entry;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Location destination;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public Location getEntry() {
		return entry;
	}

	public void setEntry(Location entry) {
		this.entry = entry;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	public PortalBase(){
		
	}
	
	public static Dao<Portal, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Portal.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
