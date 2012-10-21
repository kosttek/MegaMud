package pl.edu.agh.megamud;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.CommandCollector;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.mockdata.MockLocations1;
import pl.edu.agh.megamud.mockdata.SayinNutsBehaviour;


public class GameServer {
	private static GameServer gameServer;
	//TODO it could be not important to have allLocations in GameServer
	//maybe could be something else like map <id, Location>
	private ArrayList<Location> allLocations = new ArrayList<Location>();
	private ArrayList<Controller> allUsersLogged = new ArrayList<Controller>();
	
	public ArrayList<Location> getLocations(){
		return allLocations;
	}
	
	private GameServer(){
		init();
	}
	
	public static GameServer getInstance(){
		if (gameServer== null){
			gameServer = new GameServer();
		}
		return gameServer;
	}  
	
	private void init(){
		allLocations.addAll(MockLocations1.createLocations());
	}
	
	public void initUser(Controller user){
		allUsersLogged.add(user);
	}
	
	public void killUser(Controller user){
		allUsersLogged.remove(user);
	}
	/*
	
	void mockSetCreature(){
		Creature creature = new Creature();
		creature.name = "stwor";
		allLocations.get(0).putCreature(creature);
		
		//Instalowanie samego behaviouru, byc moze bedzeie sie dalo to jakos uproscic
		CyclicBehaviour beh = new SayinNutsBehaviour();
		beh.setCyclicDelay(3000L);
		beh.setOwner(creature);
		creature.getBehaviourList().add(beh);
		beh.init(3000L);
	}
	
	*/
}
