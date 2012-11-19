package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Module;
import pl.edu.agh.megamud.dao.ModuleNPC;
import pl.edu.agh.megamud.dao.Player;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;

public abstract class ModuleNPCBase {

	@DatabaseField(generatedId = true)
	private Integer id;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Module module;
	
	@DatabaseField(canBeNull = false)
	private String java_class;
	
	@DatabaseField(foreign = true, canBeNull = false, foreignAutoRefresh = true)
	private Player player;
	
	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public String getJava_class() {
		return java_class;
	}

	public ModuleNPCBase setJava_class(String java_class) {
		this.java_class = java_class;
		return this;
	}

	public ModuleNPCBase(){
		
	}
	
	public static Dao<ModuleNPC, Integer> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), ModuleNPC.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
}
