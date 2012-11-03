package pl.edu.agh.megamud.tests.dao;

import java.sql.SQLException;

import org.junit.BeforeClass;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.*;

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
	protected static Dao<Attribute, Integer> attributeDao;
	protected static Dao<ItemAttribute, Integer> itemAttributeDao;
	protected static Dao<CreatureAttribute, Integer> creatureAttributeDao;
	
	protected Player predefinedPlayer = null;
	protected PlayerCreature predefinedPlayerCreature = null;
	protected Item predefinedItem = null;	
	protected CreatureItem predefinedCreatureItem = null;
	protected Attribute predefinedAttribute = null;
	
	@BeforeClass
	public static void init() throws SQLException{
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();

		TableUtils.dropTable(connectionSource, CreatureItem.class, true);
		TableUtils.dropTable(connectionSource, Item.class, true);
		TableUtils.dropTable(connectionSource, PlayerCreature.class, true);
		TableUtils.dropTable(connectionSource, Player.class, true);
		TableUtils.dropTable(connectionSource, Attribute.class, true);
		TableUtils.dropTable(connectionSource, ItemAttribute.class, true);
		TableUtils.dropTable(connectionSource, CreatureAttribute.class, true);
		
		DbManager.init();
		
		creatureItemDao = CreatureItem.createDao();
		itemDao = Item.createDao();
		playerCreatureDao = PlayerCreature.createDao();
		playerDao = Player.createDao();
		attributeDao = Attribute.createDao();
		itemAttributeDao = ItemAttribute.createDao();
		creatureAttributeDao = CreatureAttribute.createDao();
		
		connectionSource.close();
	}	
	
	protected void prepareDatabase() throws SQLException{
		connectionSource = DbManager.getConnectionSource();
		TableUtils.clearTable(connectionSource, CreatureItem.class);
		TableUtils.clearTable(connectionSource, Player.class);
		TableUtils.clearTable(connectionSource, PlayerCreature.class);
		TableUtils.clearTable(connectionSource, Item.class);
		TableUtils.clearTable(connectionSource, Attribute.class);
		TableUtils.clearTable(connectionSource, ItemAttribute.class);
		TableUtils.clearTable(connectionSource, CreatureAttribute.class);	
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
	
	protected void resetAttribute() throws SQLException{
		predefinedAttribute = new Attribute();
		predefinedAttribute.setName("predefinedAttribute");
		attributeDao.create(predefinedAttribute);
	}
	
	protected void resetEntities() throws SQLException{
		resetPlayer();
		resetPlayerCreature();
		resetItem();
		resetAttribute();
		
		predefinedCreatureItem = new CreatureItem();
		predefinedCreatureItem.setCreature(predefinedPlayerCreature);
		predefinedCreatureItem.setItem(predefinedItem);
		creatureItemDao.create(predefinedCreatureItem);
	}
}