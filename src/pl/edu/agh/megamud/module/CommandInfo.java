package pl.edu.agh.megamud.module;

import java.util.Iterator;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Controller;

public class CommandInfo implements Command {
	public String getName(){
		return "info";
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		Creature c=user.getCreature();
		String s="You are a "+c.getPropClass()+" LV"+c.getLevel()+" (exp:"+c.getExp()+"/"+c.getExpNeeded()+") HP"+c.getHp()+"\r\n";
		for(Iterator<String> i=c.getPropAttributes().keySet().iterator();i.hasNext();){
			String t=i.next();
			Long v=c.getPropAttributes().get(t);
			s+=""+t+":"+v.longValue()+" ";
		}
		s+="\r\n";
		user.write(s);
		return true;
	}

}
