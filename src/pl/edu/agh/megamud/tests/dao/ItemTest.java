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
