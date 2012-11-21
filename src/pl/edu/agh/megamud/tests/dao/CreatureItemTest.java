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

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.CreatureItem;

public class CreatureItemTest extends TestBase {

	@Before
	public void setUp() throws Exception {
		prepareDatabase();
		resetPlayer();
		resetPlayerCreature();
		resetItem();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test(expected = SQLException.class)
	public void should_not_create_without_item() throws SQLException {
		CreatureItem creatureItem = new CreatureItem();
		creatureItem.setCreature(predefinedPlayerCreature);
		creatureItemDao.create(creatureItem);
	}

	@Test
	public void should_create_without_player_creature() throws SQLException {
		CreatureItem creatureItem = new CreatureItem();
		creatureItem.setItem(predefinedItem);
		creatureItemDao.create(creatureItem);
		creatureItemDao.refresh(creatureItem);

		Assert.assertNotNull(creatureItem.getId());
		Assert.assertTrue(creatureItem.getId() != 0);
	}

	@Test
	public void should_create() throws SQLException {
		CreatureItem creatureItem = new CreatureItem();
		creatureItem.setCreature(predefinedPlayerCreature);
		creatureItem.setItem(predefinedItem);
		creatureItemDao.create(creatureItem);

		Assert.assertTrue(creatureItem.getId() != null
				&& creatureItem.getId() != 0);
	}
}
