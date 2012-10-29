package pl.edu.agh.megamud;

import java.util.ArrayList;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.mockdata.MockLocations1;
import pl.edu.agh.megamud.mockdata.MockNPCs1;
import pl.edu.agh.megamud.mockdata.SayinNutsBehaviour;

public class GameServer {
	private static GameServer gameServer=null;
	//TODO it could be not important to have allLocations in GameServer
	//maybe could be something else like map <id, Location>
	private ArrayList<Location> allLocations = new ArrayList<Location>();
	private ArrayList<Controller> allUsersLogged = new ArrayList<Controller>();
	
	public ArrayList<Location> getLocations(){
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
		c.setPropClass("student");
		c.setHp(100);
		c.setLevel(22);
		c.setExp(0);
		c.setExpNeeded(1337);
		c.getPropAttributes().put("power", 20000L);
	}
	
	public void killUser(Controller user){
		allUsersLogged.remove(user);
	}

}
