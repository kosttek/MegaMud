package pl.edu.agh.megamud;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.CommandCollector;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Module;
import pl.edu.agh.megamud.base.Controller;

public class GameServer {
	/**
	 * Link to only instance.
	 */
	private static GameServer gameServer=null;
	
	/**
	 * List of all players, including NPCs.
	 */
	private List<Controller> players = new LinkedList<Controller>();
	
	/**
	 * List of all creatures.
	 */
	private List<Creature> creatures= new LinkedList<Creature>();
	
	/**
	 * List of all installed modules.
	 */
	private Map<String,Module> modules=new HashMap<String,Module>();
	
	/**
	 * Map of all locations - installed by modules.
	 */
	private Map<String,Location> locations = new HashMap<String,Location>();
	/**
	 * Map of all commands - installed by modules.
	 * @param loc
	 */
	private CommandCollector commands=new CommandCollector();
	
	public CommandCollector getCommands(){
		synchronized(commands){
			return commands;
		}
	}
	
	public List<Command> findCommands(String id){
		synchronized(commands){
			return commands.findCommands(id);
		}
	}
	
	public List<Command> findCommandsByModule(String module,String id){
		synchronized(modules){
			Module m=modules.get(module);
			if(m!=null)
				return m.findCommands(id);
			return null;
		}
	}
	
	public void addLocation(Location loc){
		synchronized(locations){
			locations.put(loc.getId(),loc);
		}
	}
	public void removeLocation(String id){
		synchronized(locations){
			locations.remove(id);
		}
	}
	public void removeLocation(Location loc){
		synchronized(locations){
			locations.remove(loc.getId());
		}
	}
	
	public void addModule(Module m){
		synchronized(modules){
			modules.put(m.getId(),m);
		}
	}
	public void removeModule(Module m){
		synchronized(modules){
			modules.remove(m.getId());
		}
	}
	public void removeModule(String id){
		synchronized(modules){
			modules.remove(id);
		}
	}
	
	public Location getStartLocation(){
		synchronized(locations){
			return locations.get("start");
		}
	}
	
	public Location getLocation(String id){
		synchronized(locations){
			return locations.get(id);
		}
	}
	
	private GameServer(){
	}
	
	public static GameServer getInstance(){
		if (gameServer== null){
			gameServer = new GameServer();
			gameServer.init();
		}
		return gameServer;
	}
	
	@SuppressWarnings("unchecked")
	private void init() {
		/*
		 * @todo Loading modules from configuration/db.
		 */
		
		List<pl.edu.agh.megamud.dao.Module> mod;
		try {
			mod = pl.edu.agh.megamud.dao.Module.getModules();
			for(Iterator<pl.edu.agh.megamud.dao.Module> i=mod.iterator();i.hasNext();){
				pl.edu.agh.megamud.dao.Module m=i.next();
				try{
					Class<Module> klazz=(Class<Module>) Class.forName(m.getJava_class());
					
					Module inst=klazz.newInstance();
					
					inst.install();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	public void initController(Controller user){
		synchronized(players){
			players.add(user);
			for(Module m:modules.values())
				m.onNewController(user);
			
			user.onConnect();
		}
	}
	
	public void killController(Controller user){
		synchronized(players){
			
			if(user.getCreature()!=null){
				user.getCreature().setLocation(null,null);
				user.getCreature().disconnect();
			}
			
			user.onDisconnect();
			
			for(Module m:modules.values())
				m.onKillController(user);
			players.remove(user);
		}
		
	}
	
	public void initCreature(Controller user,Creature c){
		synchronized(players){
			c.connect(user);
			creatures.add(c);
			for(Module m:modules.values())
				m.onNewCreature(c);
		}
	}
	
	public void killCreature(Creature c){
		synchronized(players){
			c.disconnect();
			creatures.remove(c);
			for(Module m:modules.values())
				m.onKillCreature(c);
		}
	}
}
