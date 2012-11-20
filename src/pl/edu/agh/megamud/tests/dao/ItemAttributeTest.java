package pl.edu.agh.megamud.tests.dao;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.ItemAttribute;

public class ItemAttributeTest extends TestBase {

	@Before
	public void setUp() throws Exception {
		prepareDatabase();
		resetItem();
		resetAttribute();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test(expected = SQLException.class)
	public void should_not_create_without_item() throws SQLException {
		ItemAttribute itemAttribute = new ItemAttribute();
		itemAttribute.setAttribute(predefinedAttribute);
		itemAttributeDao.create(itemAttribute);
	}

	@Test(expected = SQLException.class)
	public void should_not_create_without_attribute() throws SQLException {
		ItemAttribute itemAttribute = new ItemAttribute();
		itemAttribute.setItem(predefinedItem);
		itemAttributeDao.create(itemAttribute);
	}

	@Test
	public void should_create() throws SQLException {
		ItemAttribute itemAttribute = new ItemAttribute();
		itemAttribute.setItem(predefinedItem);
		itemAttribute.setAttribute(predefinedAttribute);
		itemAttributeDao.create(itemAttribute);

		Assert.assertTrue(itemAttribute.getId() != null
				&& itemAttribute.getId() != 0);
	}
}