package pl.edu.agh.megamud;

import java.util.ArrayList;

import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.mockdata.MockLocations1;
import pl.edu.agh.megamud.module.BaseCommands;
import pl.edu.agh.megamud.module.MoveModule;
import pl.edu.agh.megamud.module.interfaces.Interprete;

public class GameServer {
	private static GameServer gameServer;
	//TODO it could be not important to have allLocations in GameServer
	//maybe could be something else like map <id, Location>
	ArrayList<Location> allLocations = new ArrayList<Location>();
	ArrayList<User> allUsersLoged = new ArrayList<User>();
	ArrayList<Interprete> interpreters = new ArrayList<Interprete>();
	
	private GameServer(){
		init();
	}
	
	static public GameServer getInstance(){
		if (gameServer== null){
			gameServer = new GameServer();
		}
		return gameServer;
	}  
	
	private void init(){
		interpreters.add(new BaseCommands());
		interpreters.add(new MoveModule());
		allLocations.addAll(MockLocations1.createLocations());
	}
	
	public void initUser(User user){
		allUsersLoged.add(user);
		mockPlayerSetName(user);
		mockPlayerSetLocation(user);
	}
	
	void mockPlayerSetName(User user){
		if (user.player!=null)
			user.player.name= "jacus "+allUsersLoged.size();
	}
	
	void mockPlayerSetLocation(User user){
		if (user.player!=null)
			allLocations.get(0).putCreature(user.player);
	}
	
	public void interpreteCommand(User user, String command){
		for(Interprete signleIterpreter : interpreters){
			signleIterpreter.interprete(user, command);
		}
	}
}
