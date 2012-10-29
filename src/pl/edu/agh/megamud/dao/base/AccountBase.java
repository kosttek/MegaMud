package pl.edu.agh.megamud.dao.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Account;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "account")
public abstract class AccountBase{
    @DatabaseField(id = true)
    private String login;    
    
    @DatabaseField(canBeNull = false)
    private String password;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountBase() {
    	// all persisted classes must define a no-arg constructor with at least package visibility
    }
    
	public static Dao<Account,String> createDao(){
		try {
			return DaoManager.createDao(DbManager.getConnectionSource(), Account.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}