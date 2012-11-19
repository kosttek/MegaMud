package pl.edu.agh.megamud.module;

import java.util.Iterator;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Modifier;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.itemtype.ItemToWorn;
import pl.edu.agh.megamud.dao.Attribute;

public class CommandInfo extends Command {
	public String getName(){
		return "info";
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()==null)
			return false;
		
		Creature c=user.getCreature();
		
		user.write("You are a "+c.getProfession().getName()+" LV"+c.getLevel()+" (exp:"+c.getExp()+"/"+c.getExpNeeded()+") HP"+c.getHp()+"\r\n");
		
		String s="";
		for(Iterator<Attribute> i=c.getAttributes().keySet().iterator();i.hasNext();){
			Attribute t=i.next();
			Long v=c.getAttributes().get(t);
			s+=""+t.getName()+":"+v.longValue()+" ";
		}
		user.write(s);
		
		long now=System.currentTimeMillis();
		for(Modifier m:c.getModifiers()){
			s="You are under influence of "+ m.getName();
			
			Behaviour sd=m.willSelfDestruct();
			if(sd!=null){
				long left=(sd.getNextTime()-now)/1000;
				if(left>0){
					s+=" (will wear of in "+left+"s)";
				}
			}
			s+=".";
			
			user.write(s);
		}
		
		for(Item i:c.getItems().values()){
            user.write("You have "+ i.getName()+" - "+i.getDescription());
		}
		
		String out = "You wore:\n";
		for(Class<ItemToWorn> clazz : c.getEquipment().keySet()){
			Item item = c.getEquipment().get(clazz);
			if(item != null)
				out+= clazz.getSimpleName()+": "+item.getName();
		}
		user.write(out);
		
		return true;
	}

}
