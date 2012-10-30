package pl.edu.agh.megamud.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.dao.Account;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DbManager {
	private static String dbPath = "jdbc:sqlite:db/dev.db";
	public static String getDbPath() {
		return dbPath;
	}

	public static void setDbPath(String dbPath) {
		DbManager.dbPath = dbPath;
	}

	private static ConnectionSource connectionSource;
	public static ConnectionSource getConnectionSource() { 
		if (connectionSource == null){
			try{
				connectionSource = new JdbcConnectionSource(dbPath);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return connectionSource; 
	}
	
	public static void init(){
		try {
			TableUtils.createTableIfNotExists(getConnectionSource(), Account.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
