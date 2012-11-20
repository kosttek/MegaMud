package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Location;
import pl.edu.agh.megamud.dao.Portal;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

public abstract class LocationBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(canBeNull = false)
	private String description;

	@DatabaseField(canBeNull = false)
	private String module;

	public String getModule() {
		return module;
	}

	public LocationBase setModule(String module) {
		this.module = module;
		return this;
	}

	@ForeignCollectionField(eager = true)
	private ForeignCollection<Portal> exits;

	public String getName() {
		return name;
	}

	public LocationBase setName(String name) {
		this.name = name;
		return this;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public LocationBase setDescription(String description) {
		this.description = description;
		return this;
	}

	public ForeignCollection<Portal> getExits() {
		return exits;
	}

	public void setExits(ForeignCollection<Portal> exits) {
		this.exits = exits;
	}

	public LocationBase() {

	}

	public static Dao<Location, Integer> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					Location.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
