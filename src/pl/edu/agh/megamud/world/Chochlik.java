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

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.SimpleItem;
import pl.edu.agh.megamud.base.SimpleModifier;

public class Chochlik extends NPCController {
	private Item ball;

	public Chochlik() {
		super();
	}

	public void onEnter(Creature otherCreature) {
		interpreteCommand("say", "Welcome, useless " + otherCreature.getName()
				+ ". Find me an apple, or die!");
	}

	public void onItemTransfer(ItemHolder from, ItemHolder to, Item item) {
		if(to instanceof Creature && to!=getCreature() && item.getName().equals("ball")){
			Creature toc=(Creature)to;
			interpreteCommand("say",
					"Oh, you treacherous "+toc.getName()+". Give me my ball back!");
			return;
		}
		if(from instanceof Creature && to==getCreature() && item.getName().equals("ball")){
			interpreteCommand("say",
					"Thank you, my pleasures are back!");
			return;
		}
		
		if (to == getCreature() && from != null && from instanceof Creature) {
			Creature fromc = (Creature) from;

			if (item.getName().equals("apple")) {
				interpreteCommand("say",
						"Great, I like that. Have this for your quest.");

				Item prize = new SimpleItem("prize", "Hiper-duper prize.");
				prize.giveTo(getCreature());

				interpreteCommand("give", "prize " + fromc.getName());

				long delay = 5000L;
				new Behaviour(prize, delay) {
					public void action() {
						interpreteCommand("say",
								"Hahahaha, I tricked you. Watch as your dreams perish.");
					}
				}.init();
				new Behaviour(prize, delay) {
					public void action() {
						Item i = (Item) owner;
						i.giveTo(null);
					}
				}.init();

				fromc.addModifier(new SimpleModifier(fromc, "power", +10, delay));
			} else {
				interpreteCommand("say", "I don't want this, I want an apple!");
				interpreteCommand("give",
						item.getName() + " " + fromc.getName());
			}
		}
	}

	public void onStart(){
		ball = new SimpleItem("ball", "Extreme expensive NIKE-signed foot-ball.");
		ball.giveTo(getCreature());

		new CyclicBehaviour(getCreature(), 2500L) {
			public void action() {
				Creature owner = (Creature) getOwner();
				
				if(owner.getHp()<=0)
					return;

				if (owner.getItems().size() == 0) {
					Chochlik.this.interpreteCommand("take", "ball");
				} else {
					Chochlik.this.interpreteCommand("drop", "ball");
				}
			}
		}.init();
	}
}
