package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import pl.edu.agh.megamud.base.DbManager;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class ItemTest extends TestBase{

	@Before
	public void setUp() throws Exception {
		super.prepareDatabase();
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
	
	@Test
	public void should_get_creatures_having_this_item() throws SQLException{
		super.resetItem();
		super.resetPlayer();
		super.resetPlayerCreature();
		
		CreatureItem ci1 = new CreatureItem();
		ci1.setCreature(predefinedPlayerCreature);
		ci1.setItem(predefinedItem);
		creatureItemDao.create(ci1);
		
		CreatureItem ci2 = new CreatureItem();
		ci2.setCreature(predefinedPlayerCreature);
		ci2.setItem(predefinedItem);
		creatureItemDao.create(ci2);
		
		CreatureItem ci3 = new CreatureItem();
		ci3.setCreature(predefinedPlayerCreature);
		ci3.setItem(predefinedItem);
		creatureItemDao.create(ci3);
		
		itemDao.refresh(predefinedItem);
		assertEquals(3, predefinedItem.getCreatureItems().size());		
	}
}