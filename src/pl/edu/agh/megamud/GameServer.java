package pl.edu.agh.megamud;

import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.mockdata.MockLocations1;
import pl.edu.agh.megamud.mockdata.MockNPCs1;

public class GameServer {
	private static GameServer gameServer=null;
	//TODO it could be not important to have allLocations in GameServer
	//maybe could be something else like map <id, Location>
	private List<Location> allLocations = new LinkedList<Location>();
	private List<Controller> allUsersLogged = new LinkedList<Controller>();
	
	public List<Location> getLocations(){
		return allLocations;
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
	
	private void init(){
		allLocations.addAll(MockLocations1.createLocations());
		
		MockNPCs1.loadNpcs();
	}
	
	public void initUser(Controller user){
		allUsersLogged.add(user);
	}
	
	public void initCreature(Creature c){
		c.setKlass("student");
		c.setHp(100);
		c.setMaxHp(150);
		c.setLevel(22);
		c.setExp(0);
		c.setExpNeeded(1337);
		c.getPropAttributes().put("POWER", 20000L);
	}
	
	public void killUser(Controller user){
		allUsersLogged.remove(user);
	}

}
