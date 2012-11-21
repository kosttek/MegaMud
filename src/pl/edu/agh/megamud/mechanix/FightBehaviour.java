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
package pl.edu.agh.megamud.mechanix;

import java.util.List;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.BehaviourHolderInterface;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;

public class FightBehaviour extends Behaviour {
	private boolean active;
	private Creature opponent;
	private Creature fighter;
	
	public FightBehaviour(BehaviourHolderInterface o) {
		super(o, 0L);
		this.fighter=(Creature)o;
	}

	public FightBehaviour(BehaviourHolderInterface o, long delay) {
		super(o, delay);
		this.fighter=(Creature)o;
		this.active = false;
	}

	@Override
	protected void action() {
		if (!isActive() || fighter.getHp()<=0 || opponent.getHp() <= 0
				|| !opponent.getLocation().getCreatures().containsKey(fighter.getName()))
			return;
		
		FightBehaviour oppFightBeh = getOpponentFightBehaviour();
		if (oppFightBeh != null && !oppFightBeh.isActive() && isOpponentAlive()) {
			oppFightBeh.setOpponent(fighter);
			oppFightBeh.init();
		}
		
		attack();
		write();
		
		if (isOpponentAlive()) {
			put();
		} else {
			this.fighter.giveExp(3); //?);//?
			setActive(false);
			setOpponent(null);
		}
	}

	@Override
	public final Behaviour init() {
		setDelay(800);
		setActive(true);
		return super.init();
	}

	private void write() {
		Controller c = ((Creature) owner).getController();
		if (c != null) {
			c.write("Your enemy has " + opponent.getHp());
		}
		Controller cOpp = opponent.getController();
		if (c != null) {
			c.write("You got " + ((Creature) owner).getHp() + " hp");
		}
	}

	private void attack() {
		Mechanix.attack((Creature) owner, opponent);
	}

	private boolean isOpponentAlive() {
		try{
			if (opponent.getHp() <= 0)
				return false;
		}catch(Exception e){
			return false;
		}
		return true;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Creature getOpponent() {
		return opponent;
	}

	public void setOpponent(Creature opponent) {
		this.opponent = opponent;
	}

	public FightBehaviour getOpponentFightBehaviour() {
		List<Behaviour> list = opponent
				.getBehaviourByType(FightBehaviour.class);
		if (list.isEmpty())
			return null;
		return (FightBehaviour) list.get(0);
	}

}
