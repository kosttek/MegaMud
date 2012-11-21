package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.NPCController;

public class Oldman extends NPCController {
	OldmanTalk oldmanTalk = new OldmanTalk();;

	public Oldman() {
		super();
	}

	public void onEnter(Creature otherCreature) {
		interpreteCommand("say", "Hello my friend, ask me about anything");
	}

	public void onItemTransfer(ItemHolder from, ItemHolder to, Item item) {
	}

	public void setLocation(Location exit, String exitName) {
	}
	
	public String ask(String command){
		return oldmanTalk.ask(command);
	}
	
}
