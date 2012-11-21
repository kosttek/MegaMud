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

import java.sql.SQLException;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.Session;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;
import pl.edu.agh.megamud.mechanix.FightBehaviour;
import pl.edu.agh.megamud.mechanix.Mechanix;

/**
 * User connected to server. PlayerController is aware of possible commands, its
 * creature and stuff. Typical workflow: - client connects - PlayerController is
 * created; - after client logs in, controller is assigned a Creature and given
 * all attributes from database.; - client disconnects - both PC and Creature
 * are destroyed; - client logs out (possibly to change a creature) - only
 * Creature is destroyed.
 **/
public class PlayerController extends Controller {
	/**
	 * Whether we will be disconnected - indicator for Session that we wrote
	 * "exit".
	 */
	private boolean readyToDisconnect = false;
	/**
	 * Our session.
	 */
	private Session session;

	/**
	 * In-database representation of this client.
	 */
	private Player dbPlayer;

	public Player getDbPlayer() {
		return dbPlayer;
	}

	public void setDbPlayer(Player dbPlayer) {
		this.dbPlayer = dbPlayer;
	}

	public boolean isReadyToDisconnect() {
		return readyToDisconnect;
	}

	public PlayerController(Session session) {
		super();
		this.session = session;
	}

	public void write(String txt) {
		session.write(txt + "\r\n");
	}

	/**
	 * Called by creature, when they unbound from us. The creature does all
	 * command transfer.
	 */
	public void disconnect() {
		super.disconnect();
		readyToDisconnect = true;
	}

	public void onEnter(Creature otherCreature) {
		write("" + otherCreature.getName() + " entered the room.");
	}

	public void onLeave(Creature otherCreature, String usedExit) {
		if(usedExit!=null)
		write("" + otherCreature.getName() + " left the room"
				+ (usedExit != null ? " (towards " + usedExit + ")" : "") + ".");
		else
			write("" + otherCreature.getName() + " died.");
	}

	public void onSayInLocation(Creature otherCreature, String message) {
		write("" + otherCreature.getName() + " said: " + message);
	}

	public void onConnect() {
		write("Greetings! Type \"login username password\" to register or log in!");
	}

	public void onDisconnect() {
		write("Bye!");
	}

	public void onTake(Creature otherCreature, Item item) {
		write("" + otherCreature.getName() + " took " + item.getName()
				+ " from the ground.");
	}

	public void onDrop(Creature otherCreature, Item item) {
		write("" + otherCreature.getName() + " dropped " + item.getName()
				+ " to the ground.");
	}

	public void onGive(Creature from, Creature to, Item item) {
		if (from == creature)
			write("You gave " + item.getName() + " to " + to.getName() + ".");
		else if (to == creature)
			write("" + from.getName() + " gave you " + item.getName() + "!");
		else
			write("" + from.getName() + " gave " + item.getName() + " to "
					+ to.getName() + ".");
	}

	public void onAppear(Item item) {
		write("Suddenly, a wild " + item.getName() + " appeared.");
	}

	public void onDisappear(Item item) {
		write("A " + item.getName() + " has turned into ashes.");
	}

	public void onItemEquip(Item i) {
		write("You equiped " + i.getName() + ".");
	}

	public void onItemUnequip(Item i) {
		write("You unequiped " + i.getName() + ".");
	}

	public void onItemTransfer(ItemHolder from, ItemHolder to, Item item) {
		if (from == creature || to == creature)
			return;

		if (from == null) {
			write("Suddenly a wild " + item.getName() + " appeared.");
		} else if (from instanceof Location) {
			if (to == null) {
				// ???
			} else if (to instanceof Location) {
				// transfer from location to location?
			} else {
				write("" + ((Creature) to).getName() + " took a "
						+ item.getName() + ".");
			}
		} else {
			if (to == null) {
				write("A wild " + item.getName() + " has perished.");
			} else if (to instanceof Location) {
				write("" + ((Creature) from).getName() + " dropped a "
						+ item.getName() + ".");
			} else {
				write("" + ((Creature) from).getName() + " gave a "
						+ item.getName() + " to " + ((Creature) to).getName()
						+ ".");
			}
		}
	}

	public void onItemAppear(Item i, ItemHolder from) {
		super.onItemAppear(i, from);
		if (from != null && from instanceof Creature)
			write("You have now " + i.getName() + " from "
					+ ((Creature) from).getName() + "!");
		else if (from != null && from instanceof Location)
			write("You took " + i.getName() + "!");
		else
			write("Suddenly you have " + i.getName() + "!");
	}

	public void onItemDisappear(Item i, ItemHolder to) {
		super.onItemDisappear(i, to);
		if (to != null && to instanceof Creature)
			write("You gave " + i.getName() + " to "
					+ ((Creature) to).getName() + "!");
		else if (to != null && to instanceof Location)
			write("You dropped " + i.getName() + "!");
		else
			write("Suddenly your " + i.getName() + " disappeared!");
	}
	
	public void onExp(int given){
		write("You have been given "+given+" EXP, now you have "+getCreature().getExp()+"/"+getCreature().getExpNeeded());
	}
	
	public void onLevel(){
		write("You are now LV"+getCreature().getLevel()+" and now you have "+getCreature().getExp()+"/"+getCreature().getExpNeeded()+"EXP.");
	}
	public void onDie() {
		write("You died but friendly souls will bring you back to life.");
		
		PlayerCreature pc;
		pc = new PlayerCreature(this.getDbPlayer());

		pc.setExp(0);
		pc.setExp_needed(5);
		pc.setLevel(1);
		pc.setHp(100);
		pc.setProfession(Profession.DEFAULT);
		pc.setName(this.getDbPlayer().getLogin() + "");

		CreatureAttribute caStrength, caDexterity;
		caStrength = new CreatureAttribute();
		caStrength.setAttribute(Attribute.findByName(Attribute.STRENGTH));
		caStrength.setValue(10);
		caStrength.setCreature(pc);

		caDexterity = new CreatureAttribute();
		caDexterity.setAttribute(Attribute.findByName(Attribute.DEXTERITY));
		caDexterity.setValue(10);
		caDexterity.setCreature(pc);
		try {
			PlayerCreature.createDao().create(pc);
			PlayerCreature.createDao().refresh(pc);
			CreatureAttribute.createDao().create(caStrength);
			CreatureAttribute.createDao().create(caDexterity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Location loc = GameServer.getInstance().getStartLocation();
		Creature c = new Creature(pc.getName());
		c.setDbCreature(pc);

		Mechanix.initEquipment(c);
		c.addBehaviour(new FightBehaviour(c));

		GameServer.getInstance().initCreature(this, c);

		c.setLocation(loc, null);
	}
}
