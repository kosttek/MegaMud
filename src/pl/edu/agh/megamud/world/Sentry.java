package pl.edu.agh.megamud.world;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.NPCController;

public class Sentry extends NPCController {
	
	public Sentry(){
		super();
	}
	
	public void onEnter(Creature otherCreature){
	}
	
	public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){
	}
	
	public void setLocation(Location exit,String exitName){
	}
}
