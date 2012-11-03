package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.Attribute;

public class AttributeTest extends TestBase{

	@Before
	public void setUp() throws Exception {
		super.prepareDatabase();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}	
	
	@Test(expected = SQLException.class)
	public void should_not_create_attribute_without_name() throws SQLException{
		Attribute attribute = new Attribute();
		attributeDao.create(attribute);
	}
	
	@Test
	public void should_create_attribute() throws SQLException{
		Attribute attribute = new Attribute();
		attribute.setName("example attribute");
		attributeDao.create(attribute);
		
		assertTrue(attribute.getId() != 0);
		assertTrue(attribute.getId() != null);
	}
}