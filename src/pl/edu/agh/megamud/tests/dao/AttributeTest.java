package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.ItemAttribute;

public class AttributeTest extends TestBase {

	@Before
	public void setUp() throws Exception {
		super.prepareDatabase();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test
	public void should_create_attribute() throws SQLException {
		Attribute attribute = new Attribute("example attribute");
		attributeDao.create(attribute);

		assertTrue(attribute.getId() != 0);
		assertTrue(attribute.getId() != null);
	}

	@Test
	public void should_get_attributes_items() throws SQLException {
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

		attributeDao.refresh(predefinedAttribute);
		assertEquals(3, predefinedAttribute.getItemAttributes().size());
	}

	@Test
	public void should_get_attributes_creatures() throws SQLException {
		super.resetAttribute();
		super.resetPlayer();
		super.resetPlayerCreature();

		CreatureAttribute ca1 = new CreatureAttribute();
		ca1.setAttribute(predefinedAttribute);
		ca1.setCreature(predefinedPlayerCreature);
		creatureAttributeDao.create(ca1);

		CreatureAttribute ca2 = new CreatureAttribute();
		ca2.setAttribute(predefinedAttribute);
		ca2.setCreature(predefinedPlayerCreature);
		creatureAttributeDao.create(ca2);

		CreatureAttribute ca3 = new CreatureAttribute();
		ca3.setAttribute(predefinedAttribute);
		ca3.setCreature(predefinedPlayerCreature);
		creatureAttributeDao.create(ca3);

		attributeDao.refresh(predefinedAttribute);
		assertEquals(3, predefinedAttribute.getCreatureAttributes().size());
	}
}