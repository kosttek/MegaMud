package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.ItemAttribute;
import pl.edu.agh.megamud.dao.Location;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;

public abstract class LocationBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String name;
	
	@DatabaseField(canBeNull = false)
	private String description;
	
//	@ForeignCollectionField(eager = true)
//	private ForeignCollection<ItemAttribute> itemAttributes;	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
//	public ForeignCollection<ItemAttribute> getItemAttributes() {
//		return itemAttributes;
//	}
//
//	public void setItemAttributes(ForeignCollection<ItemAttribute> itemAttributes) {
//		this.itemAttributes = itemAttributes;
//	}

	public LocationBase(){
		
	}
	
	public static Dao<Location, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Location.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}	
}
