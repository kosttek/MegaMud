package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;
import java.util.List;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.*;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.stmt.PreparedQuery;

public abstract class AttributeBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull = false, unique = true)
	private String name;
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<ItemAttribute> itemAttributes;	

	@ForeignCollectionField(eager = true)
	private ForeignCollection<CreatureAttribute> creatureAttributes;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public ForeignCollection<ItemAttribute> getItemAttributes() {
		return itemAttributes;
	}

	public void setItemAttributes(ForeignCollection<ItemAttribute> itemAttributes) {
		this.itemAttributes = itemAttributes;
	}

	public ForeignCollection<CreatureAttribute> getCreatureAttributes() {
		return creatureAttributes;
	}

	public void setCreatureAttributes(
			ForeignCollection<CreatureAttribute> creatureAttributes) {
		this.creatureAttributes = creatureAttributes;
	}

	public AttributeBase(){
		
	}
	
	public static Attribute findByName(String name){
		Dao<Attribute, Integer> dao = createDao();
		PreparedQuery<Attribute> preparedQuery;
		try {
			preparedQuery = dao.queryBuilder()
					.where().eq("name", name)
					.prepare();
			List<Attribute> accounts = dao.query(preparedQuery);
			if (accounts.size() == 1){
				return accounts.get(0);
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	public static void insertIfNotExists(String name) throws SQLException{
		Attribute attribute = findByName(name);
		if (attribute == null){
			attribute = new Attribute(name);
			Attribute.createDao().create(attribute);
		}
	}
	
	public static Dao<Attribute, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Attribute.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}	
}
