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

import java.util.LinkedList;
import java.util.List;

/**
 * Abstraction of a command to be executed by a controller.
 */
public abstract class Command {
	/**
	 * Used to keep track, who uses us.
	 */
	private List<CommandCollector> collectors = new LinkedList<CommandCollector>();

	/**
	 * Use this to install this command.
	 * 
	 * @param collector
	 */
	public final void installTo(CommandCollector collector) {
		synchronized (this) {
			collectors.add(collector);
			collector.addCommand(this);
		}
	}

	/**
	 * Use this to uninstall this command.
	 * 
	 * @param collector
	 */
	public final void uninstallFrom(CommandCollector collector) {
		synchronized (this) {
			collectors.remove(collector);
			collector.removeCommand(this);
		}
	}

	public final void uninstall() {
		synchronized (this) {
			for (CommandCollector c : collectors) {
				c.removeCommand(this);
			}
			collectors.clear();
		}
	}

	public List<CommandCollector> getCollectors() {
		synchronized (this) {
			return collectors;
		}
	}

	/*
	 * Short name that identified this command. E.g. exit, help. Note that many
	 * commands can have similar name - which will be executed is distinguished
	 * by interprete() result.
	 */
	public abstract String getName();

	/*
	 * Run the command.
	 * 
	 * @returns true, if: - user is allowed to run this command (he is an
	 * administrator or similar); - some preconditions are met, e.g. user is an
	 * location, holds an item, etc. This is used to distinguish whether this
	 * command is specific and "needed" by a creature. See getName(). - command
	 * succedeed. Otherwise false.
	 */
	public abstract boolean interprete(Controller user, String command);
}
