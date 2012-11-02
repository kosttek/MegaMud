package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ItemTest {

	private static ConnectionSource connectionSource;
	private static String databaseUrl = "jdbc:sqlite:db/test.db";

	private static Dao<Item, Integer> itemDao;
	
	@BeforeClass
	public static void init() throws SQLException{
		DbManager.setDbPath(databaseUrl);
		connectionSource = DbManager.getConnectionSource();

		TableUtils.dropTable(connectionSource, Item.class, true);
		DbManager.init();
		itemDao = Item.createDao();	
		
		connectionSource.close();
	}
	
	@Before
	public void setUp() throws Exception {
		connectionSource = DbManager.getConnectionSource();
		TableUtils.clearTable(connectionSource, Item.class);
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}	
	
	@Test(expected = SQLException.class)
	public void should_not_create_item_without_name() throws SQLException{
		Item item = new Item();
		itemDao.create(item);
	}
	
	@Test
	public void should_create_item() throws SQLException{
		Item item = new Item();
		item.setName("blasphemous dagger of doom");
		itemDao.create(item);
		assertTrue(item.getId() != 0);
		assertTrue(item.getId() != null);
	}
}