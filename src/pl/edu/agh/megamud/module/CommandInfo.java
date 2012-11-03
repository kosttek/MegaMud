package pl.edu.agh.megamud.module;

import java.util.Iterator;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.dao.Item;

public class CommandInfo implements Command {
	public String getName(){
		return "info";
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		Creature c=user.getCreature();
		
		user.write("You are a "+c.getKlass()+" LV"+c.getLevel()+" (exp:"+c.getExp()+"/"+c.getExpNeeded()+") HP"+c.getHp()+"/"+c.getMaxHp()+"\r\n");
		
		String s="";
		for(Iterator<String> i=c.getPropAttributes().keySet().iterator();i.hasNext();){
			String t=i.next();
			Long v=c.getPropAttributes().get(t);
			s+=""+t+":"+v.longValue()+" ";
		}
		user.write(s);
		
		for(Item i:c.getItems().values())
			user.write("You have "+ i.getName()+" - "+i.getDescription());
		
		return true;
	}

}
