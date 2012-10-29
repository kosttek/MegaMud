package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.Session;
import pl.edu.agh.megamud.mockdata.MockCommands1;

/**
 * User connected to server.
 * PlayerController is aware of possible commands, its creature and stuff.
 **/
public class PlayerController extends Controller {
	/*
	 * Whether we will be disconnected - indicator for Session that we wrote "exit".
	 */
	private boolean readyToDisconnect=false;
	/*
	 * Our session.
	 */
	private Session session;
	
	public boolean isReadyToDisconnect(){
		return readyToDisconnect;
	}
		
	public PlayerController(Session session){
		super();
		this.session=session;

		addCommand(MockCommands1.getBasicCommand("login"));
		addCommand(MockCommands1.getBasicCommand("exit"));
		addCommand(MockCommands1.getBasicCommand("help"));
		
		addCommand(MockCommands1.getBasicCommand("kill"));
	}
	
	public void write(String txt){
		session.write(txt+"\r\n");
	}
	
	/*
	 * Called by creature, when they bound to us.
	 * The creature does all command transfer.
	 */
	public void setCreature(Creature creature){
		super.setCreature(creature);
		
		if(creature==null){
			addCommand(MockCommands1.getBasicCommand("login"));
			removeCommand(MockCommands1.getBasicCommand("info"));
		}else{
			addCommand(MockCommands1.getBasicCommand("info"));
			removeCommand(MockCommands1.getBasicCommand("login"));
		}
	}
	
	/*
	 * Called by creature, when they unbound from us.
	 * The creature does all command transfer.
	 */
	public void disconnect(){
		super.disconnect();
		readyToDisconnect=true;
	}

	public void onEnter(Creature otherCreature){
		write(""+otherCreature.getName()+" entered the room.");
	}
	public void onLeave(Creature otherCreature,String usedExit){
		write(""+otherCreature.getName()+" has left towards "+usedExit);
	}
	public void onSay(Creature otherCreature,String message){
		write(otherCreature.getName()+" said: "+message);
	}
	public void onConnect(){
		write("Greetings! Type \"login admin admin\" to log in!");
	}
	public void onDisconnect(){
		write("Bye!");
	}
}
