package pl.edu.agh.megamud.module;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Module;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.SimpleItem;
import pl.edu.agh.megamud.base.SimpleModifier;
import pl.edu.agh.megamud.dao.Item;
import pl.edu.agh.megamud.mockdata.SayinNutsBehaviour;

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
	
	protected List<Command> installCommands(){
		List<Command> cs=new LinkedList<Command>();
		cs.add(new CommandExit());
		cs.add(new CommandGoto());
		cs.add(new CommandHelp());
		cs.add(new CommandInfo());
		cs.add(new CommandLogin());
		cs.add(new CommandLook());
		
		cs.add(new CommandTake());
		cs.add(new CommandGive());
		cs.add(new CommandDrop());
		
		cs.add(new CommandSay());
		
		cs.add(new CommandKill());
		
		return cs;
	}
	
	protected List<Location> installLocations(){
		List<Location> locs=new LinkedList<Location>();
		
		Location loc1 = new Location("start","Pokoj 1",this);
		Location loc2 = new Location("p2","pokoj 2",this);
		Location loc3 = new Location("p3","pokoj 3",this);
		
		loc1.addExit("drzwi", loc2);
		loc2.addExit("drzwi", loc1);
		loc2.addExit("schody", loc3);
		loc3.addExit("schody", loc2);
		
		locs.add(loc1);
		locs.add(loc2);
		locs.add(loc3);
		
		new CyclicBehaviour(loc2,1000L){
			protected void action() {
				Location c=(Location)owner;
				
				if(c.getItems().containsKey("apple"))
					return;
				
				SimpleItem it=new SimpleItem("apple","Precious, golden apple.");
				it.giveTo(c);
			}
		}.init();
		
		return locs;
	}
	protected List<NPCController> installNPCs(){
		List<NPCController> npcs=new LinkedList<NPCController>();
		
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
		
		GameServer.getInstance().initController(bot);
		GameServer.getInstance().initCreature(bot,botCreature);
		
		botCreature.setLocation(botRoom,null);
		botCreature.setKlass("Troll");
		botCreature.setLevel(100);
		botCreature.setHp(666);
		botCreature.setMaxHp(666);
		
		new SayinNutsBehaviour(botCreature,5000L).init();
		
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
		
		npcs.add(bot);
		
		return npcs;
	}
	
	public void onNewController(Controller c){
		installedCommands.findCommands("login").get(0).installTo(c);
		installedCommands.findCommands("exit").get(0).installTo(c);
		installedCommands.findCommands("help").get(0).installTo(c);
		
		installedCommands.findCommands("kill").get(0).installTo(c);
	}
	
	public void onKillController(Controller c){
		installedCommands.findCommands("login").get(0).uninstallFrom(c);
		installedCommands.findCommands("exit").get(0).uninstallFrom(c);
		installedCommands.findCommands("help").get(0).uninstallFrom(c);
		
		installedCommands.findCommands("kill").get(0).uninstallFrom(c);
	}
	
	public void onNewCreature(Creature c){
		Controller d=c.getController();
		
		installedCommands.findCommands("login").get(0).uninstallFrom(d);
		installedCommands.findCommands("info").get(0).installTo(d);
		
		installedCommands.findCommands("take").get(0).installTo(d);
		installedCommands.findCommands("drop").get(0).installTo(d);
		installedCommands.findCommands("give").get(0).installTo(d);
		
		installedCommands.findCommands("look").get(0).installTo(d);
		installedCommands.findCommands("goto").get(0).installTo(d);
		installedCommands.findCommands("say").get(0).installTo(d);
		
		c.setHp(100);
		c.setMaxHp(100);
		c.setLevel(1);
		c.setExp(0);
		c.setExpNeeded(5);
		
		c.getAttributes().put("power", 1L);
	}
	
	public void onKillCreature(Creature c){
		Controller d=c.getController();
		
		installedCommands.findCommands("info").get(0).uninstallFrom(d);
		installedCommands.findCommands("login").get(0).installTo(d);
		
		installedCommands.findCommands("take").get(0).uninstallFrom(d);
		installedCommands.findCommands("drop").get(0).uninstallFrom(d);
		installedCommands.findCommands("give").get(0).uninstallFrom(d);
		
		installedCommands.findCommands("look").get(0).uninstallFrom(d);
		installedCommands.findCommands("goto").get(0).uninstallFrom(d);
		installedCommands.findCommands("say").get(0).uninstallFrom(d);
	}
}
