package pl.edu.agh.megamud.tests.dao;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.*;

public class CreatureAttributeTest extends TestBase{
	
	@Before
	public void setUp() throws Exception {
		prepareDatabase();
		resetPlayer();
		resetPlayerCreature();
		resetAttribute();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}	
	
	@Test(expected = SQLException.class)
	public void should_not_create_without_creature() throws SQLException{
		CreatureAttribute creatureAttribute = new CreatureAttribute();
		creatureAttribute.setAttribute(predefinedAttribute);
		creatureAttributeDao.create(creatureAttribute);
	}
	
	@Test(expected = SQLException.class)
	public void should_not_create_without_attribute() throws SQLException{
		CreatureAttribute creatureAttribute = new CreatureAttribute();
		creatureAttribute.setCreature(predefinedPlayerCreature);
		creatureAttributeDao.create(creatureAttribute);
	}
	
	@Test
	public void should_create() throws SQLException{
		CreatureAttribute creatureAttribute = new CreatureAttribute();
		creatureAttribute.setAttribute(predefinedAttribute);
		creatureAttribute.setCreature(predefinedPlayerCreature);
		creatureAttributeDao.create(creatureAttribute);
		
		Assert.assertTrue(creatureAttribute.getId() != null && creatureAttribute.getId() != 0);
	}
}