package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Module;
import pl.edu.agh.megamud.base.SimpleItem;
import pl.edu.agh.megamud.dao.Portal;
import pl.edu.agh.megamud.dao.base.LocationBase;
import pl.edu.agh.megamud.dao.base.ModuleBase;
import pl.edu.agh.megamud.dao.base.PortalBase;
import pl.edu.agh.megamud.base.itemtype.Weapon;
/**
 * Abstraction of a in-server module. A module loads locations, NPCs, new items etc.
 * @author Tomasz
 */
public class DefaultModule extends DatabaseModule{
	public String getId(){
		return "default";
	}
	
	public String getDescription(){
		return "Default game module.";
	}
	
	private void clearLocations() throws SQLException{
		LocationBase.createDao().deleteBuilder().delete();
	}
	
	private void prepareLocation(String id,String desc) throws SQLException{
		pl.edu.agh.megamud.dao.Location loc1=new pl.edu.agh.megamud.dao.Location();
		loc1.setName(id).setDescription(desc).setModule(this.getId());
		LocationBase.createDao().create(loc1);
	}
	
	private void preparePortal(String from,String to,String name) throws SQLException{
		pl.edu.agh.megamud.dao.Location loc1=LocationBase.createDao().queryBuilder().where().eq("name", from).and().eq("module", this.getId()).query().get(0);
		pl.edu.agh.megamud.dao.Location loc2=LocationBase.createDao().queryBuilder().where().eq("name", to).and().eq("module", this.getId()).query().get(0);
		
		Portal p=new Portal();
		p.setDestination(loc2);
		p.setEntry(loc1);
		p.setName(name);
		PortalBase.createDao().create(p);
	}
	
	protected void init(){
		try {
			clearLocations();
			
			prepareLocation("start","Pokoj glowny");
			prepareLocation("p2","Pokoj 2");
			prepareLocation("p3","Pokoj 3");
			
			preparePortal("start","p2","Lewo");
			preparePortal("start","p3","Prawo");
			preparePortal("p2","start","Prawo");
			preparePortal("p3","start","Lewo");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		super.init();
		
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
Weapon sword = new Weapon("sword", "little rusty sword");
		sword.giveTo(GameServer.getInstance().getLocation("p2"));
		
		new CyclicBehaviour(GameServer.getInstance().getLocation("p2"),1000L){
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
		findCommands(CommandEquip.commandString).get(0).installTo(d);
		findCommands(CommandUnequip.commandString).get(0).installTo(d);
		
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
		
		findCommands(CommandEquip.commandString).get(0).uninstallFrom(d);
		findCommands(CommandUnequip.commandString).get(0).uninstallFrom(d);
		findCommands("take").get(0).uninstallFrom(d);
		findCommands("drop").get(0).uninstallFrom(d);
		findCommands("give").get(0).uninstallFrom(d);
		
		findCommands("look").get(0).uninstallFrom(d);
		findCommands("goto").get(0).uninstallFrom(d);
		findCommands("say").get(0).uninstallFrom(d);
	}
}
