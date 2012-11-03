package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.Session;
import pl.edu.agh.megamud.dao.Item;

/**
 * User connected to server.
 * PlayerController is aware of possible commands, its creature and stuff.
 * Typical workflow:
 * - client connects - PlayerController is created;
 * - after client logs in, controller is assigned a Creature and given all attributes from database.;
 * - client disconnects - both PC and Creature are destroyed;
 * - client logs out (possibly to change a creature) - only Creature is destroyed. 
 **/
public class PlayerController extends Controller {
	/**
	 * Whether we will be disconnected - indicator for Session that we wrote "exit".
	 */
	private boolean readyToDisconnect=false;
	/**
	 * Our session.
	 */
	private Session session;
	
	public boolean isReadyToDisconnect(){
		return readyToDisconnect;
	}
		
	public PlayerController(Session session){
		super();
		this.session=session;
	}
	
	public void write(String txt){
		session.write(txt+"\r\n");
	}
	
	/**
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
		write(""+otherCreature.getName()+" left the room"+(usedExit!=null ? " (towards "+usedExit+")" : "")+".");
	}
	public void onSayInLocation(Creature otherCreature,String message){
		write(""+otherCreature.getName()+" said: "+message);
	}
	public void onConnect(){
		write("Greetings! Type \"login username password\" to register or log in!");
	}
	public void onDisconnect(){
		write("Bye!");
	}
	public void onTake(Creature otherCreature,Item item){
		write(""+otherCreature.getName()+" took "+item.getId()+" from the ground.");
	}
	public void onDrop(Creature otherCreature,Item item){
		write(""+otherCreature.getName()+" dropped "+item.getId()+" to the ground.");
	}
	public void onGive(Creature from,Creature to,Item item){
		if(from==creature)
			write("You gave "+item.getName()+" to "+to.getName()+".");
		else if(to==creature)
			write(""+from.getName()+" gave you "+item.getName()+"!");
		else
			write(""+from.getName()+" gave "+item.getName()+" to "+to.getName()+".");
	}
	public void onAppear(Item item){
		write("Suddenly, a wild "+item.getName()+" appeared.");
	}
	public void onDisappear(Item item){
		write("A "+item.getName()+" has turned into ashes.");
	}
	
	public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){
		if(from==creature || to==creature)
			return;
		
		if(from==null){
			write("Suddenly a wild "+item.getName()+" appeared.");
		}else if(from instanceof Location){
			if(to==null){
				//???
			}else if(to instanceof Location){
				//transfer from location to location?
			}else{
				write(""+((Creature)to).getName()+" took a "+item.getName()+".");
			}
		}else{
			if(to==null){
				write("A wild "+item.getName()+" has perished.");
			}else if(to instanceof Location){
				write(""+((Creature)from).getName()+" dropped a "+item.getName()+".");
			}else{
				write(""+((Creature)from).getName()+" gave a "+item.getName()+" to "+((Creature)to).getName()+".");
			}
		}
	}
	public void onItemAppear(Item i,ItemHolder from){
		if(from!=null && from instanceof Creature)
			write("You have now "+i.getName()+" from "+((Creature)from).getName()+"!");
		else if(from!=null && from instanceof Location)
			write("You took "+i.getName()+"!");
		else
			write("Suddenly you have "+i.getName()+"!");
	}
	public void onItemDisappear(Item i,ItemHolder to){
		if(to!=null && to instanceof Creature)
			write("You gave "+i.getName()+" to "+((Creature)to).getName()+"!");
		else if(to!=null && to instanceof Location)
			write("You dropped "+i.getName()+"!");
		else
			write("Suddenly your "+i.getName()+" disappeared!");
	}
}
