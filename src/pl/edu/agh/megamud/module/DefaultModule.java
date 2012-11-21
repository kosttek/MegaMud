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
package pl.edu.agh.megamud.module;

import java.sql.SQLException;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.DatabaseModule;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.SimpleItem;
import pl.edu.agh.megamud.base.itemtype.HealItem;
import pl.edu.agh.megamud.base.itemtype.Weapon;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.LocationItem;
import pl.edu.agh.megamud.dao.base.LocationBase;
import pl.edu.agh.megamud.mechanix.CommandHit;
import pl.edu.agh.megamud.world.AggressiveSentry;
import pl.edu.agh.megamud.world.CaveInitializer;
import pl.edu.agh.megamud.world.Chochlik;
import pl.edu.agh.megamud.world.CommandAskOldman;
import pl.edu.agh.megamud.world.CreatureFactory;
import pl.edu.agh.megamud.world.HellInitializer;
import pl.edu.agh.megamud.world.Oldman;
import pl.edu.agh.megamud.world.OldmanFight;
import pl.edu.agh.megamud.world.Sentry;

/**
 * Abstraction of a in-server module. A module loads locations, NPCs, new items
 * etc.
 * 
 * @author Tomasz
 */
public class DefaultModule extends DatabaseModule {
	public String getId() {
		return "default";
	}

	public String getDescription() {
		return "Default game module.";
	}

	private void prepareAttributes() throws SQLException {
		// AttributeBase.createDao().deleteBuilder().delete();
		Attribute.insertIfNotExists(Attribute.STRENGTH);
		Attribute.insertIfNotExists(Attribute.DEXTERITY);
		Attribute.insertIfNotExists(Attribute.DAMAGE);
		Attribute.insertIfNotExists(Attribute.HEAL);
	}

	private void clearLocations() throws SQLException {
		LocationBase.createDao().deleteBuilder().delete();
		LocationItem.createDao().deleteBuilder().delete();
	}

	protected void init() {
		try {
			clearLocations();
			prepareAttributes();

			CaveInitializer.init(this.getId());
			HellInitializer.init(this.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		super.init();

		// Commands

		installCommands();

		Weapon sword = new Weapon("sword", "little rusty sword");
		sword.giveTo(GameServer.getInstance().getLocation(
				CaveInitializer.B2.getName()));
		sword.initAtribute(Attribute.findByName(Attribute.DAMAGE));
		sword.setAttribute(Attribute.DAMAGE, 3L);
		
		HealItem potion1 = new HealItem("potion", "small potion");
		potion1.giveTo(GameServer.getInstance().getLocation(CaveInitializer.B1.getName()));
		potion1.initAtribute(Attribute.findByName(Attribute.HEAL));
		potion1.setAttribute(Attribute.HEAL, 30L);

		new CyclicBehaviour(GameServer.getInstance().getLocation(
				CaveInitializer.C7), 1000L) {
			protected void action() {
				Location location = (Location) owner;

				if (location.getItems().containsKey("apple"))
					return;

				SimpleItem it = new SimpleItem("apple",
						"Precious, golden apple.");
				it.giveTo(location);
			}
		}.init();
		Oldman oldman = new Oldman();
		CommandAskOldman askOldman = new CommandAskOldman(oldman);
		GameServer.getInstance().getStartLocation().addCommand(askOldman);
		Creature oldCreature = new Creature("Oldman");
		installNPC(oldman, oldCreature.setLevel(100).setHp(1000),
				GameServer.getInstance().getStartLocation());
		oldCreature.addBehaviour(new OldmanFight(oldCreature));

		installNPC(new Chochlik(), new Creature("Chochlik").setLevel(100)
				.setHp(666),
				GameServer.getInstance().getLocation(CaveInitializer.B3));
		
		installRespawningNPC(
				new Sentry(), 
				CreatureFactory.RAT,
				GameServer.getInstance().getLocation(CaveInitializer.B2));		
		
		installRespawningNPC(
				new AggressiveSentry(), 
				CreatureFactory.RAT,
				GameServer.getInstance().getLocation(CaveInitializer.D3));

		installRespawningNPC(
				new AggressiveSentry(), 
				CreatureFactory.RAT,
				GameServer.getInstance().getLocation(CaveInitializer.E1));
	
		installRespawningNPC(
				new AggressiveSentry(), 
				CreatureFactory.RAT,
				GameServer.getInstance().getLocation(CaveInitializer.D5));
		
		installRespawningNPC(
				new Sentry(), 
				CreatureFactory.RAT,
				GameServer.getInstance().getLocation(CaveInitializer.C6));
		
		installRespawningNPC(
				new Sentry(), 
				CreatureFactory.RAT,
				GameServer.getInstance().getLocation(CaveInitializer.D1));		

		installRespawningNPC(
				new Sentry(), 
				CreatureFactory.GOBLIN,
				GameServer.getInstance().getLocation(HellInitializer.F3));
		
		installRespawningNPC(
				new AggressiveSentry(), 
				CreatureFactory.GOBLIN,
				GameServer.getInstance().getLocation(HellInitializer.E1));
		
		installRespawningNPC(
				new AggressiveSentry(), 
				CreatureFactory.GOBLIN,
				GameServer.getInstance().getLocation(HellInitializer.D4));		

		installRespawningNPC(
				new Sentry(), 
				CreatureFactory.GOBLIN,
				GameServer.getInstance().getLocation(HellInitializer.E6));	

		installRespawningNPC(
				new Sentry(), 
				CreatureFactory.GOBLIN,
				GameServer.getInstance().getLocation(HellInitializer.B4)); 	
	}

	private void installCommands() {
		installCommand(new CommandUse());
		installCommand(new CommandEquip());
		installCommand(new CommandHit());
		installCommand(new CommandUnequip());
		installCommand(new CommandExit());
		installCommand(new CommandGoto());
		installCommand(new CommandHelp());
		installCommand(new CommandInfo());
		installCommand(new CommandLogin());
		installCommand(new CommandLook());

		installCommand(new CommandTake());
		installCommand(new CommandGive());
		installCommand(new CommandDrop());

		installCommand(new CommandSay());

		installCommand(new CommandKill());
	}

	public void onNewController(Controller c) {
		findCommands("login").get(0).installTo(c);
		findCommands("exit").get(0).installTo(c);
		findCommands("help").get(0).installTo(c);

		findCommands("kill").get(0).installTo(c);
	}

	public void onKillController(Controller c) {
		findCommands("login").get(0).uninstallFrom(c);
		findCommands("exit").get(0).uninstallFrom(c);
		findCommands("help").get(0).uninstallFrom(c);

		findCommands("kill").get(0).uninstallFrom(c);
	}

	public void onNewCreature(Creature c) {
		Controller d = c.getController();

		findCommands("login").get(0).uninstallFrom(d);
		findCommands("info").get(0).installTo(d);
		findCommands(CommandEquip.commandString).get(0).installTo(d);
		findCommands(CommandUse.commandString).get(0).installTo(d);
		findCommands(CommandUnequip.commandString).get(0).installTo(d);
		findCommands(CommandHit.commandString).get(0).installTo(d);

		findCommands("take").get(0).installTo(d);
		findCommands("drop").get(0).installTo(d);
		findCommands("give").get(0).installTo(d);

		findCommands("look").get(0).installTo(d);
		findCommands("goto").get(0).installTo(d);
		findCommands("say").get(0).installTo(d);
	}

	public void onKillCreature(Creature c) {
		Controller d = c.getController();
		if (d == null)
			return;
		findCommands("info").get(0).uninstallFrom(d);
		findCommands("login").get(0).installTo(d);

		findCommands(CommandEquip.commandString).get(0).uninstallFrom(d);
		findCommands(CommandUnequip.commandString).get(0).uninstallFrom(d);
		findCommands(CommandHit.commandString).get(0).uninstallFrom(d);
		findCommands("take").get(0).uninstallFrom(d);
		findCommands("drop").get(0).uninstallFrom(d);
		findCommands("give").get(0).uninstallFrom(d);

		findCommands("look").get(0).uninstallFrom(d);
		findCommands("goto").get(0).uninstallFrom(d);
		findCommands("say").get(0).uninstallFrom(d);
	}
}
