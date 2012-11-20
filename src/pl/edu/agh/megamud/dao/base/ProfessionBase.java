package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Profession;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class ProfessionBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(canBeNull = false)
	private String name;

	@DatabaseField(canBeNull = true)
	private String description;

	@DatabaseField(canBeNull = true, foreign = true, foreignAutoRefresh = true)
	private Profession parent;

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer level;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Profession getParent() {
		return parent;
	}

	public void setParent(Profession parent) {
		this.parent = parent;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public ProfessionBase() {

	}

	public static Dao<Profession, Integer> createDao() {
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(),
					Profession.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
