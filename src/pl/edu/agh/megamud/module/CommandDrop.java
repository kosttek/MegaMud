package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.dao.Item;

public class CommandDrop implements Command {
	public String getName(){
		return "drop";
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		
		Item it=user.getCreature().getItems().get(command);
		Creature c=user.getCreature();
		
		if(it==null)
			user.write("You do not own such item as "+command+"!");
		else{
			boolean b=it.giveTo(c.getLocation());
			if(!b){
				user.write("You cannot drop this item here!");
			}
		}
		
		return true;
	}
	
}
