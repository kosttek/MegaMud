package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.dao.ItemAttribute;

public class ItemTest extends TestBase {

	@Before
	public void setUp() throws Exception {
		super.prepareDatabase();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test(expected = SQLException.class)
	public void should_not_create_item_without_name() throws SQLException {
		Item item = new Item();
		itemDao.create(item);
	}

	@Test
	public void should_create_item() throws SQLException {
		Item item = new Item();
		item.setName("blasphemous dagger of doom");
		itemDao.create(item);
		assertTrue(item.getId() != 0);
		assertTrue(item.getId() != null);
	}

	@Test
	public void should_get_creatures_having_this_item() throws SQLException {
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

	@Test
	public void should_get_items_attributes() throws SQLException {
		super.resetAttribute();
		super.resetItem();

		ItemAttribute ia1 = new ItemAttribute();
		ia1.setAttribute(predefinedAttribute);
		ia1.setItem(predefinedItem);
		itemAttributeDao.create(ia1);

		ItemAttribute ia2 = new ItemAttribute();
		ia2.setAttribute(predefinedAttribute);
		ia2.setItem(predefinedItem);
		itemAttributeDao.create(ia2);

		ItemAttribute ia3 = new ItemAttribute();
		ia3.setAttribute(predefinedAttribute);
		ia3.setItem(predefinedItem);
		itemAttributeDao.create(ia3);

		itemDao.refresh(predefinedItem);
		assertEquals(3, predefinedItem.getItemAttributes().size());
	}
}