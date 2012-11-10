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
	
//	@Test
//	public void should_get_attributes_items() throws SQLException{
//		super.resetAttribute();
//		super.resetItem();
//		
//		ItemAttribute ia1 = new ItemAttribute();
//		ia1.setAttribute(predefinedAttribute);
//		ia1.setItem(predefinedItem);
//		itemAttributeDao.create(ia1);
//		
//		ItemAttribute ia2 = new ItemAttribute();
//		ia2.setAttribute(predefinedAttribute);
//		ia2.setItem(predefinedItem);
//		itemAttributeDao.create(ia2);
//
//		ItemAttribute ia3 = new ItemAttribute();
//		ia3.setAttribute(predefinedAttribute);
//		ia3.setItem(predefinedItem);
//		itemAttributeDao.create(ia3);
//		
//		attributeDao.refresh(predefinedAttribute);
//		assertEquals(3, predefinedAttribute.getItemAttributes().size());		
//	}		
}