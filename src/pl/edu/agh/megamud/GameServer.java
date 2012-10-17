package pl.edu.agh.megamud;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.User;
import pl.edu.agh.megamud.mockdata.MockLocations1;
import pl.edu.agh.megamud.mockdata.SayinNutsBehaviour;


public class GameServer {
	private static GameServer gameServer;
	//TODO it could be not important to have allLocations in GameServer
	//maybe could be something else like map <id, Location>
	ArrayList<Location> allLocations = new ArrayList<Location>();
	ArrayList<User> allUsersLoged = new ArrayList<User>();

	
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
		allLocations.addAll(MockLocations1.createLocations());
	}
	
	public void initUser(User user){
		allUsersLoged.add(user);
		mockPlayerSetName(user);
		mockPlayerSetLocation(user);
		mockSetCreature();
	}
	
	void mockPlayerSetName(User user){
		if (user.player!=null)
			user.player.name= "jacus "+allUsersLoged.size();
	}
	
	void mockPlayerSetLocation(User user){
		if (user.player!=null)
			allLocations.get(0).putCreature(user.player);
	}
	
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
	
	public void interpreteCommand(User user, String commandString){
//		for(Interprete signleIterpreter : interpreters){
//			signleIterpreter.interprete(user, command);
//		}
		String [] args = commandString.trim().split(" ");
		String firstWord = args[0];
		
		List<Command> commands = new ArrayList<Command>();
		//GET ALL KNOWN INTERPETERS TO USER ON THAT MOMENT
		List<Command> tempCommands;
		CommandsCollection [] listInter = {user.getInterpreter(),user.player.getInterpreter(),user.player.currentLocation.getInterpreter()};
		for(CommandsCollection inter : listInter){
			tempCommands = inter.getCommands(firstWord);
			if(tempCommands!=null)
				commands.addAll(tempCommands);
		}

		for(Command command : commands){
			if(command.interprete(user, commandString)) //INTERPRETE ONLY FOR FIRST FOUND COMMAND
				break;
		}
	}
}
