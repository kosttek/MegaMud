package pl.edu.agh.megamud.base;

import java.util.List;

import pl.edu.agh.megamud.GameServer;

/**
 * Abstraction of a in-server module. A module loads locations, NPCs, new items etc.
 * @todo Admin commands for loading/unloading modules.
 * @author Tomasz
 */
public abstract class Module {
	protected List<Location> installedLocations=null;
	protected List<NPCController> installedNpcs=null;
	protected CommandCollector installedCommands=null;
	
	/**
	 * Returns module name.
	 * @return
	 */
	public abstract String getId();
	/**
	 * Returns human-readable module description.
	 * @return
	 */
	public abstract String getDescription();
	
	/**
	 * Use this to install a module into game server.
	 * Order of execution:
	 * - installCommands;
	 * - installLocations;
	 * - installNpcs.
	 * Then the module is loaded.
	 * @param gs
	 */
	public void install(){
		GameServer gs=GameServer.getInstance();
		gs.addModule(this);
		
		List<Command> cs=installCommands();
		
		this.installedCommands=new CommandCollector();
		if(this.installedCommands!=null){
			for(Command cmd:cs){
				cmd.installTo(this.installedCommands);
				cmd.installTo(gs.getCommands());
			}
		}
		
		this.installedLocations=installLocations();
		if(this.installedLocations!=null){
			for(Location loc:this.installedLocations){
				gs.addLocation(loc);
			}
		}
		
		this.installedNpcs=installNPCs();
	}
	
	/**
	 * Use this any time to uninstall the module.
	 */
	public void uninstall(){
		GameServer gs=GameServer.getInstance();
		
		gs.removeModule(this);
		
		if(this.installedNpcs!=null){
			for(NPCController nc:this.installedNpcs){
				gs.killController(nc);
			}
		}
		
		if(this.installedCommands!=null){
			while(this.installedCommands.getAllCommands().size()>0){
				Command c=this.installedCommands.getAllCommands().get(0);
				c.uninstall();
			}
		}
		
		if(this.installedLocations!=null){
			for(Location loc:this.installedLocations){
				gs.removeLocation(loc);
			}
		}
		
		this.installedNpcs=null;
		this.installedCommands=null;
		this.installedLocations=null;
	}
	
	/**
	 * Use this to find a specific command from this module.
	 * @param id
	 * @return
	 */
	public List<Command> findCommands(String id){
		return installedCommands.findCommands(id);
	}
	
	/**
	 * Implement this to provide locations to be installed.
	 * This method will be executed only once, in install(). Generated list will be used in eventual uninstall().
	 * Here you can:
	 * - create new locations, link them with existing locations (if any);
	 * - give them items;
	 * - spawn NPCs (using NPCController and Creature);
	 * - etc.
	 * @return By default, null.
	 */
	protected List<Location> installLocations(){
		return null;
	}
	
	/**
	 * Implement this to provide own commands to game. These commands will be given to a creature upon spawn.
	 * These commands will be internally added to GameServer's command collector.
	 * This method will be executed only once, in install(). Generated list will be used in eventual uninstall().
	 * @return By default null.
	 */
	protected List<Command> installCommands(){
		return null;
	}
	
	/**
	 * Implement this to add own NPC creatures in game. Internally you must do GameServer.initController() and GameServer.initCreature() by yourself.
	 * This method will be executed only once, in install(). Generated list will be used in eventual uninstall().
	 * @return By default null.
	 */
	protected List<NPCController> installNPCs(){
		return null;
	}
	
	/**
	 * Executed after creation of a new controller (either new incoming client, or new NPC).
	 * @param c
	 */
	public void onNewController(Controller c){}
	
	/**
	 * Executed after a controller destroyed (client disconnected or NPC removed).
	 * @param c
	 */
	public void onKillController(Controller c){}
	
	/**
	 * Executed after creation of a new creature appeared in game.
	 * @param c
	 */
	public void onNewCreature(Creature c){}
	
	/**
	 * Executed after a creature destroyed.
	 * @param c
	 */
	public void onKillCreature(Creature c){}
}
