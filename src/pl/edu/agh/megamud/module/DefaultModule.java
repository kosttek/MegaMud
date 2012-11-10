package pl.edu.agh.megamud.module;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Module;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.SimpleItem;
import pl.edu.agh.megamud.base.SimpleModifier;

/**
 * Abstraction of a in-server module. A module loads locations, NPCs, new items etc.
 * @author Tomasz
 */
public class DefaultModule extends Module{
	public String getId(){
		return "mock";
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
		
		NPCController bot=new NPCController(){
			public void onEnter(Creature otherCreature){
				interpreteCommand("say","Welcome, useless "+otherCreature.getName()+". Find me an apple, or die!");
			}
			public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){
				if(to==getCreature() && from!=null && from instanceof Creature){
					Creature fromc=(Creature)from;
					
					if(item.getId().equals("apple")){
						interpreteCommand("say","Great, I like that. Have this for your quest.");
						
						Item prize=new SimpleItem("prize","Hiper-duper prize.");
						prize.giveTo(getCreature());
						
						interpreteCommand("give","prize "+fromc.getName());
						
						long delay=5000L;
						new Behaviour(to,delay){
							public void action(){
								interpreteCommand("say","Hahahaha, I tricked you. Watch as your dreams perish.");
							}
						}.init();
						new Behaviour(prize,delay){
							public void action(){
								Item i=(Item)owner;
								i.giveTo(null);
							}
						}.init();
						
						fromc.addModifier(new SimpleModifier(fromc, "power", +10, delay));
					}else{
						interpreteCommand("say","I don't want this, I want an apple!");
						interpreteCommand("give","apple "+fromc.getName());
					}
				}
			}
		};
		final Creature botCreature=new Creature("Hohlik");
		final Location botRoom=GameServer.getInstance().getStartLocation();
		
		botCreature.setKlass("Troll");
		botCreature.setLevel(100);
		botCreature.setHp(666);
		botCreature.setMaxHp(666);
		
		installNPC(bot,botCreature,botRoom);
		
		Item botBall=new Item("ball","Extreme expensive NIKE-signed foot-ball."){
			protected boolean canBeGivenTo(ItemHolder owner) {
				return owner==botCreature || owner==botRoom;
			}
			
		};
		botBall.giveTo(botRoom);
		new CyclicBehaviour(botCreature,2500L){
			public void action() {
				Creature owner = (Creature) getOwner();
				Controller con=owner.getController();
				
				if(owner.getItems().size()==0){
					con.interpreteCommand("take", "ball");
				}else{
					con.interpreteCommand("drop", "ball");
				}
			}
		}.init();
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
		
		c.setHp(100);
		c.setMaxHp(100);
		c.setLevel(1);
		c.setExp(0);
		c.setExpNeeded(5);
		
		c.getAttributes().put("power", 1L);
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
