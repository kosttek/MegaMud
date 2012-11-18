package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Module;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class ModuleBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(canBeNull = false)
	private String java_class;
	
	public String getJava_class() {
		return java_class;
	}

	public ModuleBase setJava_class(String java_class) {
		this.java_class = java_class;
		return this;
	}

	public ModuleBase(){
		
	}
	
	public static Dao<Module, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Module.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
