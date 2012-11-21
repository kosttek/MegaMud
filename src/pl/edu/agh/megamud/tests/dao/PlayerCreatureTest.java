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
import static org.junit.Assert.assertFalse;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.CreatureItem;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;

public class PlayerCreatureTest extends TestBase {

	@Before
	public void setUp() throws Exception {
		super.prepareDatabase();
		super.resetPlayer();
	}

	@After
	public void tearDown() throws Exception {
		connectionSource.close();
	}

	@Test
	public void should_autogenerate_ids() throws SQLException {
		PlayerCreature pc1 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc1);

		PlayerCreature pc2 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc2);

		PlayerCreature pc3 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc3);

		Integer expected = pc1.getId() + 1;
		Integer actual = pc2.getId();
		assertEquals(expected, actual);

		expected = pc2.getId() + 1;
		actual = pc3.getId();
		assertEquals(expected, actual);
	}

	@Test(expected = SQLException.class)
	public void should_not_create_player_creature_without_player()
			throws SQLException {
		playerCreatureDao.create(new PlayerCreature());
	}

	@Test
	public void should_create_player_creature_with_player() throws SQLException {
		PlayerCreature pc = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc);

		assertEquals(predefinedPlayer.getLogin(), pc.getPlayer().getLogin());
	}

	@Test
	public void should_allow_multiple_player_creatures_for_one_player()
			throws SQLException {
		PlayerCreature pc1 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc1);

		PlayerCreature pc2 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc2);

		PlayerCreature pc3 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc3);

		assertEquals(3, predefinedPlayer.getPlayerCreatures().size());
	}

	@Test
	public void should_allow_only_one_creature_for_player_creature_and_overwrite_previous()
			throws SQLException {
		Player timmy = new Player();
		timmy.setLogin("timmy");
		timmy.setPassword("_secret");
		playerDao.create(timmy);

		PlayerCreature pc1 = new PlayerCreature(predefinedPlayer);
		playerCreatureDao.create(pc1);

		assertEquals(predefinedPlayer.getLogin(), pc1.getPlayer().getLogin());

		pc1.setPlayer(timmy);
		playerCreatureDao.update(pc1);

		assertFalse(predefinedPlayer.getLogin().equals(
				pc1.getPlayer().getLogin()));
		assertEquals(timmy.getLogin(), pc1.getPlayer().getLogin());
	}

	@Test
	public void should_get_creatures_items() throws SQLException {
		super.resetPlayerCreature();
		super.resetItem();

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

		playerCreatureDao.refresh(predefinedPlayerCreature);
		assertEquals(3, predefinedPlayerCreature.getCreatureItems().size());
	}

	@Test
	public void should_get_attributes_creatures() throws SQLException {
		super.resetAttribute();
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

		playerCreatureDao.refresh(predefinedPlayerCreature);
		assertEquals(3, predefinedPlayerCreature.getCreatureAttributes().size());
	}
}
