package pl.edu.agh.megamud.module;

import java.sql.SQLException;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.dao.Player;

public class CommandLogin extends Command {
	public String getName(){
		return "login";
	}
	
	private Player account = null;
	private Controller user = null;
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()!=null)
			return false;
		this.user = user;
		
		String [] args = command.trim().split(" ");
		if(args.length<2){
			user.write("Invalid parameters!");
			return true;
		}
		String login=args[0];
		String pass=args[1];
		
		if (Player.isRegistered(login)){
			tryToLogin(login, pass);
		} else {
			registerNewAccount(login, pass);
		}
		return true;
	}
	
	private void tryToLogin(String login, String password){
		account = Player.getByLoginAndPassword(login, password);
		if (account != null) {
			user.write("Login successfull!");
			handleSucessfulAuthentication(user);
		} else {
			user.write("Invalid password.");
		}		
	}
	
	private void registerNewAccount(String login, String password){
		try {
			account = Player.registerNewAccount(login, password);
			user.write("New account registered.");
			handleSucessfulAuthentication(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			user.write("Internal server error.");
			e.printStackTrace();
		}		
	}
	
	/**
	 * @todo A command to choose a creature to play with.
	 * @todo Grab a creature data from database, its items etc.
	 */
	private void handleSucessfulAuthentication(Controller user){
		Location loc=GameServer.getInstance().getStartLocation();
		Creature c=new Creature(account.getLogin());
		GameServer.getInstance().initCreature(user,c);
		
		c.setLocation(loc,null);
	}
}
