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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Object capable of collecting and managing commands. Hosted in a map (string
 * => list<commands>) - commands with the same name.
 */
public class CommandCollector {
	private Map<String, List<Command>> map = new HashMap<String, List<Command>>();

	/**
	 * Gets all commands with specified name.
	 */
	public final List<Command> findCommands(String name) {
		return map.get(name);
	}

	/**
	 * Remove a command from this collector.
	 */
	public final void removeCommand(Command cmd) {
		List<Command> v = map.get(cmd.getName());
		if (v == null) {
			return;
		}
		v.remove(cmd);
		if (v.size() == 0) {
			map.remove(cmd.getName());
		}
	}

	/*
	 * Add a command to this collector.
	 */
	public final void addCommand(Command cmd) {
		List<Command> v = map.get(cmd.getName());
		if (v == null) {
			v = new LinkedList<Command>();
			map.put(cmd.getName(), v);
		}
		v.add(cmd);
	}

	/**
	 * Get a list of all commands supported by this collector.
	 */
	public final List<Command> getAllCommands() {
		List<Command> l = new LinkedList<Command>();
		for (Iterator<Entry<String, List<Command>>> i = map.entrySet()
				.iterator(); i.hasNext();) {
			Entry<String, List<Command>> e = i.next();
			for (Iterator<Command> c = e.getValue().iterator(); c.hasNext();) {
				l.add(c.next());
			}
		}
		return l;
	}
}
