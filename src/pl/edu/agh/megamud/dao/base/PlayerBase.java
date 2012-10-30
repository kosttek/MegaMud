package pl.edu.agh.megamud.dao.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Player;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "player")
public abstract class PlayerBase{
    @DatabaseField(id = true)
    private String login;    
    
    @DatabaseField(canBeNull = false)
    private String passwordMd5;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswordMd5() {
		return this.passwordMd5;
	}

	protected void setPasswordMd5(String passwordMd5) {
		this.passwordMd5 = passwordMd5;
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