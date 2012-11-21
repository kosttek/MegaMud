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
import java.util.Map;
import java.util.Map.Entry;

import pl.edu.agh.megamud.dao.Attribute;

/**
 * This class implements a most simple Modifier that can be used. It changes a
 * specified creature's attribute by adding/removing points. E.g. POWER +5.
 * After a specified time, the modifier will wear off.
 */
public class SimpleModifier implements Modifier {
	private Creature creature;
	private String name;
	private String stat;
	private long diff;
	private Behaviour beh = null;

	/**
	 * We need this behaviour to wear the modifiers effect of after some time.
	 */
	private class SelfDestructBehaviour extends Behaviour {
		public SelfDestructBehaviour(Creature o, long delay) {
			super(o, delay);
		}

		protected void action() {
			Creature creature = (Creature) getOwner();
			creature.removeModifier(SimpleModifier.this);
		}
	}

	public SimpleModifier(Creature creature, String stat, long diff, long delay) {
		this.creature = creature;
		this.name = "" + stat + " " + (diff > 0 ? "+" : "") + diff + "";
		this.stat = stat;
		this.diff = diff;
		if (delay > 0) {
			beh = new SelfDestructBehaviour(creature, delay);
			beh.put();
		}
	}

	public Creature getCreature() {
		return creature;
	}

	public Behaviour willSelfDestruct() {
		return beh;
	}

	public String getName() {
		return name;
	}

	/**
	 * @todo What if there is no such attribute?
	 */
	public boolean modify(Creature c, Map<Attribute, Long> attrs) {
		for (Iterator<Entry<Attribute, Long>> set = attrs.entrySet().iterator(); set
				.hasNext();) {
			Entry<Attribute, Long> next = set.next();
			Attribute a = next.getKey();
			if (a.getName().equals(stat)) {
				Long l = next.getValue();
				next.setValue(new Long(l.longValue() + diff));
				return true;
			}
		}
		return true;
	}

	public void onBegin() {
		creature.write("You have been given a modifier " + stat + " "
				+ (diff > 0 ? "+" : "") + diff + "!!!");
	}

	public void onStop() {
		creature.write("Modifier " + stat + " " + (diff > 0 ? "+" : "") + diff
				+ " expired.");
	}
}
