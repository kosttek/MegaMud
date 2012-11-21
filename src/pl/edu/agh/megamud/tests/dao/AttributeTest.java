/*******************************************************************************
 * Copyright (c) 2012, AGH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
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
