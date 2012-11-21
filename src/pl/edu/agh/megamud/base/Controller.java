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

import java.util.Iterator;
import java.util.List;

import pl.edu.agh.megamud.dao.Player;

/**
 * Abstraction of a "controller" of a creature. Controler is a brain for a
 * creature. Receives various events, can control creature's behaviour and
 * initiates commands.
 **/
public abstract class Controller extends CommandCollector {
	/**
	 * Our creature. Placed after log-in (if real player) or after controller
	 * creation (if NPC).
	 */
	protected Creature creature = null;

	/**
	 * In-database representation.
	 */
	protected Player dbPlayer = null;

	public Player getDbPlayer() {
		return dbPlayer;
	}

	public void setDbPlayer(Player dbPlayer) {
		this.dbPlayer = dbPlayer;
	}

	public Controller() {
	}

	public final Creature getCreature() {
		return creature;
	}

	public void setCreature(Creature creature) {
		this.creature = creature;
	}

	public void disconnect() {
		if(creature!=null){
			creature.setLocation(null, null);
		}
	}

	/**
	 * Method to run a command. Provide a command line from user.
	 */
	public boolean interpreteCommand(String commandString) {
		String cmd = commandString.trim();
		String[] arr = cmd.split(" ");
		String firstWord = arr[0];

		String args2 = arr.length > 1 ? cmd.substring(firstWord.length() + 1)
				: "";

		return interpreteCommand(firstWord, args2);
	}

	/**
	 * Method to run a command. Provide a command name and arguments. Use this
	 * even in NPC class, since due to modular nature of MegaMUD some commands
	 * may not be available to user at a specific moment, or other commands may
	 * be more appriopriate than default ones.
	 * 
	 * @return true, if any actual command was run.
	 */
	public boolean interpreteCommand(String cmd, String args) {
		List<Command> cmd2 = findCommands(cmd);
		if (cmd2 != null)
			for (Iterator<Command> i = cmd2.iterator(); i.hasNext();) {
				Command c = i.next();
				if (c.interprete(this, args))
					return true;
			}
		write("Unknown command, type help for known commands.");
		return false;
	}

	/**
	 * Use this to send a message to controller.
	 */
	public void write(String txt) {
	}

	/** In-location events. */
	public void onEnter(Creature otherCreature) {
	}

	public void onLeave(Creature otherCreature, String usedExit) {
	}

	public void onSayInLocation(Creature otherCreature, String message) {
	}

	/**
	 * Abstract event for all item dis/appearances: - item magically appeared in
	 * a location or creature's inventory; - disappeared ; - a creature gave an
	 * item to other creature (especially: _we_ gave (from), or _we_ were given
	 * (to)); - someone dropped an item (to=Location); - someone picked an item
	 * (from=Location). Here we will get notifications from the Location.
	 */
	public void onItemTransfer(ItemHolder from, ItemHolder to, Item item) {
	}

	/** Inherited from ItemHolder. */
	public void onItemAppear(Item i, ItemHolder from) {
	}

	public void onItemDisappear(Item i, ItemHolder to) {
	}

	public void onItemEquip(Item i) {
	}

	public void onItemUnequip(Item i) {
	}

	/**
	 * Called upon connection. Executed also in NPC controller.
	 */
	public void onConnect() {
	}

	/**
	 * Called upon disconnection. Executed also in NPC controller.
	 */
	public void onDisconnect() {
	}

	public void onTakeDamage(int hpLost) {

	}

	public void onDie() {

	}
	
	public void onExp(int given){
		
	}
	public void onLevel(){
		
	}
}
