package pl.edu.agh.megamud.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "account")
public class Account {
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

	public Account() {
    	// all persisted classes must define a no-arg constructor with at least package visibility
    }
    
	
}