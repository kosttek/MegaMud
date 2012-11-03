package pl.edu.agh.megamud.base;

import java.sql.SQLException;

import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;

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
			TableUtils.createTableIfNotExists(getConnectionSource(), Player.class);
			TableUtils.createTableIfNotExists(getConnectionSource(), PlayerCreature.class);
			TableUtils.createTableIfNotExists(getConnectionSource(), Profession.class);
			TableUtils.createTableIfNotExists(getConnectionSource(), pl.edu.agh.megamud.dao.Item.class);
			TableUtils.createTableIfNotExists(getConnectionSource(), CreatureItem.class);
			TableUtils.createTableIfNotExists(getConnectionSource(), Attribute.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
}
