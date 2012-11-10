package pl.edu.agh.megamud.tests.dao;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.*;

import pl.edu.agh.megamud.dao.*;

public class LocationTest extends TestBase{

	@Before
	public void setUp() throws Exception {
		super.prepareDatabase();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}	
	
	@Test(expected = SQLException.class)
	public void should_not_create_location_without_name() throws SQLException{
		Location location = new Location();
		location.setDescription("some location description");
		locationDao.create(location);
	}
	
	@Test(expected = SQLException.class)
	public void should_not_create_location_without_description() throws SQLException{
		Location location = new Location();
		location.setName("some location name");
		locationDao.create(location);
	}
	
	@Test
	public void should_create_location() throws SQLException{
		Location location = new Location();
		location.setDescription("some location description");
		location.setName("some location name");
		locationDao.create(location);
		
		assertTrue(location.getId() != 0);
		assertTrue(location.getId() != null);
	}
	
	@Test
	public void should_get_exits() throws SQLException{
		Location location1 = new Location();
		location1.setName("Big room");
		location1.setDescription("Some very large room");
		locationDao.create(location1);

		Location location2 = new Location();
		location2.setName("Medium room");
		location2.setDescription("The room seems rather boring. Nothing special about it.");
		locationDao.create(location2);
		
		Location location3 = new Location();
		location3.setName("Small room");
		location3.setDescription("This room makes you feel claustrophobic!");
		locationDao.create(location3);
		
		Portal p1 = new Portal();
		p1.setEntry(location1);
		p1.setDestination(location2);
		p1.setName("Door");
		portalDao.create(p1);
		
		Portal p2 = new Portal();
		p2.setEntry(location1);
		p2.setDestination(location3);
		p2.setName("stairs");
		portalDao.create(p2);
		
		locationDao.refresh(location1);
		assertEquals(2, location1.getExits().size());		
	}		
}