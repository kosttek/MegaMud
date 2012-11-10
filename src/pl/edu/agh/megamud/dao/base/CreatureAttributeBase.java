package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class CreatureAttributeBase {

	@DatabaseField(generatedId = true)
	private Integer id;

	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private PlayerCreature creature;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Attribute attribute;	

	@DatabaseField(canBeNull = false, defaultValue = "0")
	private Integer value;
	
	public PlayerCreature getCreature() {
		return creature;
	}

	public void setCreature(PlayerCreature creature) {
		this.creature = creature;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public CreatureAttributeBase(){
		
	}
	
	public static Dao<CreatureAttribute, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), CreatureAttribute.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
