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
package pl.edu.agh.megamud.base;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.dao.LocationItem;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Portal;
import pl.edu.agh.megamud.dao.base.LocationBase;

/**
 * A module that grabs possible locations and NPCs from database.
 * 
 * @author Tomasz
 */
public abstract class DatabaseModule extends Module {

	/**
	 * Initialization: - installs all locations from database, that are bound to
	 * module getId(). - the same for NPCs, but using reflection.
	 */
	protected void init() {
		List<pl.edu.agh.megamud.dao.Location> locs;

		try {
			locs = LocationBase.createDao().queryBuilder().where()
					.eq("module", this.getId()).query();
		} catch (SQLException e) {
			e.printStackTrace();
			return;
		}
		/**
		 * 1. load location IDs and descriptions SELECT location.id,
		 * location.desc FROM location WHERE module=getId() 2. install them
		 */
		List<Location> outlocs = new LinkedList<Location>();
		for (Iterator<pl.edu.agh.megamud.dao.Location> i = locs.iterator(); i
				.hasNext();) {
			pl.edu.agh.megamud.dao.Location loc = i.next();
			String id = loc.getName();
			String desc = loc.getDescription();

			Location l = new Location(id, desc, this);
			l.setDbLocation(loc);
			outlocs.add(l);

			installLocation(l);
		}
		/**
		 * 3. load exits (exit name + next location ID) and link to existing
		 * locations SELECT exit.to_id, exit.name FROM exit WHERE
		 * from_id=getId()
		 */
		for (Location from : outlocs) {
			pl.edu.agh.megamud.dao.Location dbloc = from.getDbLocation();
			for (Iterator<Portal> set = dbloc.getExits().iterator(); set
					.hasNext();) {
				Portal p = set.next();
				String out = p.getDestination().getName();
				String name = p.getName();
				Location end = GameServer.getInstance().getLocation(out);

				if (end == null)
					continue;

				from.addExit(name, end);
			}

			try {
				List<LocationItem> itemz = LocationItem.createDao()
						.queryBuilder().where().eq("location_id", dbloc)
						.query();
				for (Iterator<LocationItem> i = itemz.iterator(); i.hasNext();) {
					LocationItem li = i.next();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		/**
		 * @todo Loading NPCs 1. load stored npc data SELECT npc.id,
		 *       creature.name, creature.class, creature.klazz, etc FROM npcs
		 *       INNER JOIN npc.creature = creature.id WHERE npc.module =
		 *       getId() class = in-java class of NPCController klazz = in-game
		 *       creature's class Creature data is to be stored as any other
		 *       creature. 2. install'em
		 */
		if (false) {

			for (;;) {
				String npcId = "id";
				String creatureName = "name";
				String npcController = "pl.edu.agh.megamud.Base.NPCController";

				String creatureLocation = "start";

				try {
					@SuppressWarnings("unchecked")
					Class<NPCController> klazz = (Class<NPCController>) Class
							.forName(npcController);
					Constructor<NPCController> cons = klazz.getConstructor();
					NPCController bot = cons.newInstance();

					Player p = new Player();
					PlayerCreature pc = new PlayerCreature(p);

					Creature c = new Creature(creatureName);
					c.setDbCreature(pc);

					Location loc = GameServer.getInstance().getLocation(
							creatureLocation);

					installNPC(bot, c, loc);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		}
	}

}
