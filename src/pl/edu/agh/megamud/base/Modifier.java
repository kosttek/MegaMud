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

import java.util.Map;

import pl.edu.agh.megamud.dao.Attribute;

/**
 * A temporary modifier to creature's attributes. Works like that: - creature
 * has basic attributes/stats - creature gets a modifier, in a fight or as a
 * result of an item - everytime attributes are needed (= nearly always), a
 * modifier can modify temporary stats of a creature - it can eventually self
 * destruct (if for example it is a 30sec modifier) Examples: an apple can give
 * +5 strength for 5 minutes.
 */

public interface Modifier {
	/**
	 * Owner of this modifier.
	 */
	public Creature getCreature();

	/**
	 * If not null, then this holds a behaviour already bound to creature, that
	 * will eventually self-destruct the modifier.
	 */
	public Behaviour willSelfDestruct();

	/**
	 * It's name.
	 */
	public String getName();

	/**
	 * Modify any attribute from attrs. Returns false, if the modifier expired.
	 */
	public boolean modify(Creature c, Map<Attribute, Long> attrs);

	/**
	 * Executed upon binding to a creature. Shall send some information.
	 */
	public void onBegin();

	/**
	 * Executed upon disappearing.
	 */
	public void onStop();
}
