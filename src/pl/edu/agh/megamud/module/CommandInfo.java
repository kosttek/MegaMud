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

import java.util.Iterator;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.Modifier;
import pl.edu.agh.megamud.base.itemtype.ItemToWorn;
import pl.edu.agh.megamud.dao.Attribute;

public class CommandInfo extends Command {
	public String getName() {
		return "info";
	}

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;

		Creature c = user.getCreature();

		user.write("You are a " + c.getProfession().getName() + " LV"
				+ c.getLevel() + " (exp:" + c.getExp() + "/" + c.getExpNeeded()
				+ ") HP" + c.getHp() + "\r\n");

		String s = "";
		for (Iterator<Attribute> i = c.getAttributes().keySet().iterator(); i
				.hasNext();) {
			Attribute t = i.next();
			Long v = c.getAttributes().get(t);
			s += "" + t.getName() + ":" + v.longValue() + " ";
		}
		user.write(s);

		long now = System.currentTimeMillis();
		for (Modifier m : c.getModifiers()) {
			s = "You are under influence of " + m.getName();

			Behaviour sd = m.willSelfDestruct();
			if (sd != null) {
				long left = (sd.getNextTime() - now) / 1000;
				if (left > 0) {
					s += " (will wear of in " + left + "s)";
				}
			}
			s += ".";

			user.write(s);
		}

		for (Item i : c.getItems().values()) {
			user.write("You have " + i.getName() + " - " + i.getDescription());
		}

		String out = "You wore:\n";
		for (Class<? extends ItemToWorn> clazz : c.getEquipment().keySet()) {
			Item item = c.getEquipment().get(clazz);
			if (item != null)
				out += clazz.getSimpleName() + ": " + item.getName();
		}
		user.write(out);

		return true;
	}

}
