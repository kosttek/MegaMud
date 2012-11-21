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
package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.mechanix.FightBehaviour;

public class CreatureFactory {

	public static enum Creatures { RAT, GOBLIN }
	public static String RAT = "rat";
	public static String GOBLIN = "goblin";
	
	public static Creature getRat() {
		final Creature rat = new Creature(RAT).setLevel(1).setHp(34);

		rat.initAtribute(Attribute.findByName(Attribute.STRENGTH));
		rat.setAttribute(Attribute.STRENGTH, 5L);

		rat.addBehaviour(new FightBehaviour(rat));
		new CyclicBehaviour(rat, 5000L) {
			protected void action() {
				if (rat.getHp() > 0) {
					rat.getController().interpreteCommand("say", "*squeak*");
				}
			}
		}.init();

		return rat;
	}
	
	public static Creature getGoblin(){
		final Creature goblin = new Creature(GOBLIN)
			.setLevel(1)
			.setHp(40);

        goblin.initAtribute(Attribute.findByName(Attribute.STRENGTH));
        goblin.setAttribute(Attribute.STRENGTH, 7L);	

		goblin.addBehaviour(new FightBehaviour(goblin));
		new CyclicBehaviour(goblin, 5000L) {
			protected void action() {
				if (goblin.getHp() > 0){
					goblin.getController().interpreteCommand("say", "Silence! I kill you!");
				}
			}
		}.init();
		
		return goblin;
	}	
	
	public static Creature getCreature(String creature){
		if (creature.equals(GOBLIN)){
			return CreatureFactory.getGoblin();
		} else {
			return CreatureFactory.getRat();
		}
	}
}
