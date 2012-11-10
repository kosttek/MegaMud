package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.*;

import com.j256.ormlite.dao.*;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

public abstract class PlayerBase{
    @DatabaseField(id = true)
    private String login;    
    
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@DatabaseField(canBeNull = false)
	private String password_md5;

	public String getPasswordMd5() {
		return this.password_md5;
	}

	protected void setPasswordMd5(String passwordMd5) {
		this.password_md5 = passwordMd5;
	}

    @DatabaseField(canBeNull = false, defaultValue = "0")
    private Integer priviledges;

	public Integer getPriviledges() {
		return priviledges;
	}

	public void setPriviledges(Integer priviledges) {
		this.priviledges = priviledges;
	}	
	
	@ForeignCollectionField(eager = true)
	private ForeignCollection<PlayerCreature> playerCreatures;
	
	
	public ForeignCollection<PlayerCreature> getPlayerCreatures() {
		try {
			createDao().refresh((Player) this);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return playerCreatures;
	}

	public void setPlayerCreatures(ForeignCollection<PlayerCreature> playerCreatures) {
		this.playerCreatures = playerCreatures;
	}

	public PlayerBase() {
    	// all persisted classes must define a no-arg constructor with at least package visibility
    }
    
	public static Dao<Player,String> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Player.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}