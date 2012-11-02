package pl.edu.agh.megamud.tests.dao;

import java.sql.SQLException;

import org.junit.BeforeClass;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public abstract class TestBase{
	
	protected static ConnectionSource connectionSource;
	protected static String databaseUrl = "jdbc:sqlite:db/test.db";

	protected static Dao<CreatureItem, Integer> creatureItemDao;
	protected static Dao<Item, Integer> itemDao;
	protected static Dao<PlayerCreature, String> playerCreatureDao;
	protected static Dao<Player, String> playerDao;
	
	protected Player predefinedPlayer = null;
	protected PlayerCreature predefinedPlayerCreature = null;
	protected Item predefinedItem = null;	
	
	@BeforeClass
	public static void init() throws SQLException{
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();

		TableUtils.dropTable(connectionSource, CreatureItem.class, true);
		TableUtils.dropTable(connectionSource, Item.class, true);
		TableUtils.dropTable(connectionSource, PlayerCreature.class, true);
		TableUtils.dropTable(connectionSource, Player.class, true);
		DbManager.init();
		
		creatureItemDao = CreatureItem.createDao();
		itemDao = Item.createDao();
		playerCreatureDao = PlayerCreature.createDao();
		playerDao = Player.createDao();
		
		connectionSource.close();
	}	
	
	protected void prepareDatabase() throws SQLException{
		connectionSource = DbManager.getConnectionSource();
		TableUtils.clearTable(connectionSource, CreatureItem.class);
		TableUtils.clearTable(connectionSource, Player.class);
		TableUtils.clearTable(connectionSource, PlayerCreature.class);
		TableUtils.clearTable(connectionSource, Item.class);		
	}
	
	protected void resetPlayer() throws SQLException{
		predefinedPlayer = new Player();
		predefinedPlayer.setLogin("player");
		predefinedPlayer.setPassword("_secret");
		playerDao.create(predefinedPlayer);		
	}
	
	protected void resetPlayerCreature() throws SQLException{
		predefinedPlayerCreature = new PlayerCreature();
		predefinedPlayerCreature.setName("predefinedCreature");
		predefinedPlayerCreature.setPlayer(predefinedPlayer);
		playerCreatureDao.create(predefinedPlayerCreature);
	}
	
	protected void resetItem() throws SQLException{
		predefinedItem = new Item();
		predefinedItem.setName("predefinedItem");
		itemDao.create(predefinedItem);		
	}
}