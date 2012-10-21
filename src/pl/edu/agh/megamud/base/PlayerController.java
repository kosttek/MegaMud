package pl.edu.agh.megamud.base;


import pl.edu.agh.megamud.Session;

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
		
		addCommand("login");
		addCommand("exit");
		addCommand("help");
		
		addCommand("kill");
	}
	
	public void write(String txt){
		session.write(txt);
	}
	
	/*
	 * Method to "interprete" a line from session. Tries to run a command (if there is one).
	 */
	public boolean interpreteCommand(String commandString){
		String cmd=commandString.trim();
		String[] arr=cmd.split(" ");
		String firstWord = arr[0];
		
		String args2=arr.length>1 ? cmd.substring(firstWord.length()+1) : "";
		
		Command cmd2=findCommand(firstWord);
		if(cmd2!=null && cmd2.interprete(this, args2))
			return true;
		write("Unknown command, type help for known commands.");
		return false;
	}
	
	/*
	 * Called by creature, when they bound to us.
	 * The creature does all command transfer.
	 */
	public void setCreature(Creature creature){
		super.setCreature(creature);
		if(creature==null)
			addCommand("login");
		else
			removeCommand("login");
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
		write(otherCreature+" said: "+message);
	}
	public void onConnect(){
		write("Greetings!\nType login admin admin to log in!");
	}
	public void onDisconnect(){
		write("Bye!");
	}
}
