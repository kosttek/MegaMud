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

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.Location;

public class CommandGive extends Command {
	public String getName() {
		return "give";
	}

	public boolean interprete(Controller user, String command) {
		if (user.getCreature() == null)
			return false;

		String arr[] = command.split(" ");

		Creature self = user.getCreature();
		Location here = self.getLocation();

		Item it = self.getItems().get(arr[0]);
		if (it == null)
			user.write("You do not own such item as " + command + "!");
		else {
			if (arr.length < 2)
				user.write("Who do you want to give " + it.getName() + "?");
			else {
				Creature other = here.getFirstCreature(arr[1]);
				if (other == null) {
					user.write("There is no creature like " + arr[1] + "!");
				} else {
					if (other.getItems().containsKey(command)) {
						user.write("They already have " + it.getName() + "!");
						other.write("" + self.getName() + " tried to give you "
								+ it.getName() + ", but you already have one!");
					} else {
						boolean b = it.giveTo(other);
						if (b) {
							here.onItemTransfer(self, other, it);
						} else
							user.write("You cannot give this item to "
									+ other.getName() + "!");
					}
				}
			}
		}

		return true;
	}

}
