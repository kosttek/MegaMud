package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
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
