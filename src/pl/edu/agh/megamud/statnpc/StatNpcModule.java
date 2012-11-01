package pl.edu.agh.megamud.statnpc;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Module;
import pl.edu.agh.megamud.base.NPCController;

/**
 * Abstraction of a in-server module. A module loads locations, NPCs, new items etc.
 * @author Tomasz
 */
public class StatNpcModule extends Module{
	public String getId(){
		return "statnpc";
	}
	
	public String getDescription(){
		return "Draws a NPC that says various things.";
	}
	
	private class CommandVar extends Command {
		public String getName(){
			return "var";
		}
		
		public boolean interprete(Controller user, String command) {
			if(user.getCreature()==null)
				return false;
			
			user.write("Var is neat");
			
			return true;
		}

	}

	
	protected List<Command> installCommands(){
		List<Command> cs=new LinkedList<Command>();
		cs.add(new CommandVar());	
		return cs;
	}
	
	protected List<NPCController> installNPCs(){
		List<NPCController> npcs=new LinkedList<NPCController>();
		
		NPCController bot=new NPCController(){
			public void onEnter(Creature otherCreature){
				interpreteCommand("say","Hi, "+otherCreature.getName()+". You are "+otherCreature.getHp()+"/"+otherCreature.getMaxHp()+"HP "+otherCreature.getKlass()+".!");
			}
			public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){
				if(to==getCreature()){
					Creature fromc=(Creature)from;
					
					item.giveTo(from);
				}
			}
		};
		final Creature botCreature=new Creature("Szpiek");
		final Location botRoom=GameServer.getInstance().getStartLocation();
		
		GameServer.getInstance().initController(bot);
		GameServer.getInstance().initCreature(bot,botCreature);
		
		botCreature.setLocation(botRoom,null);
		botCreature.setKlass("Troll");
		botCreature.setLevel(100);
		botCreature.setHp(666);
		botCreature.setMaxHp(666);

		npcs.add(bot);
			
		return npcs;
	}
	
	public void onNewController(Controller c){
	}
	
	public void onKillController(Controller c){
	}
	
	public void onNewCreature(Creature c){
		Controller d=c.getController();
		
		installedCommands.findCommands("var").get(0).installTo(d);
	}
	
	public void onKillCreature(Creature c){
		Controller d=c.getController();
		
		installedCommands.findCommands("var").get(0).uninstallFrom(d);
	}
}
