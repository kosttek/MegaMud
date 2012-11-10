package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Item;

public class CommandGive extends Command {
	public String getName(){
		return "give";
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		
		String arr[]=command.split(" ");
		
		Creature self=user.getCreature();
		Location here=self.getLocation();
		
		Item it=self.getItems().get(arr[0]);
		if(it==null)
			user.write("You do not own such item as "+command+"!");
		else{
			if(arr.length<2)
				user.write("Who do you want to give "+it.getName()+"?");
			else{
				Creature other=here.getCreatures().get(arr[1]);
				if(other==null){
					user.write("There is no creature like "+arr[1]+"!");	
				}else{
					if(other.getItems().containsKey(command)){
						user.write("They already have "+it.getName()+"!");
						other.write(""+self.getName()+" tried to give you "+it.getName()+", but you already have one!");
					}else{
						boolean b=it.giveTo(other);
						if(b){
							here.onItemTransfer(self,other,it);
						}else
							user.write("You cannot give this item to "+other.getName()+"!");
					}
				}
			}
		}
		
		return true;
	}
	
}
