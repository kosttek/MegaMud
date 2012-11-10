package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Module;
import pl.edu.agh.megamud.base.SimpleItem;

/**
 * Abstraction of a in-server module. A module loads locations, NPCs, new items etc.
 * @author Tomasz
 */
public class DefaultModule extends Module{
	public String getId(){
		return "default";
	}
	
	public String getDescription(){
		return "Default game module.";
	}
	
	protected void init(){
		//Commands
		
		installCommand(new CommandExit());
		installCommand(new CommandGoto());
		installCommand(new CommandHelp());
		installCommand(new CommandInfo());
		installCommand(new CommandLogin());
		installCommand(new CommandLook());
		
		installCommand(new CommandTake());
		installCommand(new CommandGive());
		installCommand(new CommandDrop());
		
		installCommand(new CommandSay());
		
		installCommand(new CommandKill());
		
		// Locations	
		Location loc1 = new Location("start","Pokoj 1",this);
		Location loc2 = new Location("p2","pokoj 2",this);
		Location loc3 = new Location("p3","pokoj 3",this);
		
		loc1.addExit("drzwi", loc2);
		loc2.addExit("drzwi", loc1);
		loc2.addExit("schody", loc3);
		loc3.addExit("schody", loc2);
		
		installLocation(loc1);
		installLocation(loc2);
		installLocation(loc3);
		
		new CyclicBehaviour(loc2,1000L){
			protected void action() {
				Location c=(Location)owner;
				
				if(c.getItems().containsKey("apple"))
					return;
				
				SimpleItem it=new SimpleItem("apple","Precious, golden apple.");
				it.giveTo(c);
			}
		}.init();
		
		installNPC(new SampleBot(),new Creature("Hohlik").setLevel(100).setHp(666),GameServer.getInstance().getStartLocation());
	}
	
	public void onNewController(Controller c){
		findCommands("login").get(0).installTo(c);
		findCommands("exit").get(0).installTo(c);
		findCommands("help").get(0).installTo(c);
		
		findCommands("kill").get(0).installTo(c);
	}
	
	public void onKillController(Controller c){
		findCommands("login").get(0).uninstallFrom(c);
		findCommands("exit").get(0).uninstallFrom(c);
		findCommands("help").get(0).uninstallFrom(c);
		
		findCommands("kill").get(0).uninstallFrom(c);
	}
	
	public void onNewCreature(Creature c){
		Controller d=c.getController();
		
		findCommands("login").get(0).uninstallFrom(d);
		findCommands("info").get(0).installTo(d);
		
		findCommands("take").get(0).installTo(d);
		findCommands("drop").get(0).installTo(d);
		findCommands("give").get(0).installTo(d);
		
		findCommands("look").get(0).installTo(d);
		findCommands("goto").get(0).installTo(d);
		findCommands("say").get(0).installTo(d);
	}
	
	public void onKillCreature(Creature c){
		Controller d=c.getController();
		
		findCommands("info").get(0).uninstallFrom(d);
		findCommands("login").get(0).installTo(d);
		
		findCommands("take").get(0).uninstallFrom(d);
		findCommands("drop").get(0).uninstallFrom(d);
		findCommands("give").get(0).uninstallFrom(d);
		
		findCommands("look").get(0).uninstallFrom(d);
		findCommands("goto").get(0).uninstallFrom(d);
		findCommands("say").get(0).uninstallFrom(d);
	}
}
