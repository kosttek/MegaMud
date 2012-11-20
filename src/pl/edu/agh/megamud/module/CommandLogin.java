package pl.edu.agh.megamud.module;

import java.sql.SQLException;
import java.util.Iterator;

import com.j256.ormlite.dao.ForeignCollection;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.PlayerController;
import pl.edu.agh.megamud.dao.Attribute;
import pl.edu.agh.megamud.dao.CreatureAttribute;
import pl.edu.agh.megamud.dao.Player;
import pl.edu.agh.megamud.dao.PlayerCreature;
import pl.edu.agh.megamud.dao.Profession;
import pl.edu.agh.megamud.mechanix.FightBehaviour;
import pl.edu.agh.megamud.mechanix.Mechanix;

public class CommandLogin extends Command {
	public String getName(){
		return "login";
	}
	
	private Player account = null;
	private PlayerController user = null;
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()!=null || !(user instanceof PlayerController))
			return false;
		
		this.user = (PlayerController)user;
		
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
			handleSucessfulAuthentication(user, account);
		} else {
			user.write("Invalid password.");
		}		
	}
	
	private void registerNewAccount(String login, String password){
		try {
			account = Player.registerNewAccount(login, password);
			user.write("New account registered.");
			handleSucessfulAuthentication(user, account);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			user.write("Internal server error.");
			e.printStackTrace();
		}		
	}
	
	/**
	 * @todo A command to choose a creature to play with.
	 */
	private void handleSucessfulAuthentication(PlayerController user,Player player){
		user.setDbPlayer(player);
		
		ForeignCollection<PlayerCreature> creatures=player.getPlayerCreatures();
		Iterator<PlayerCreature> i=creatures.iterator();
		PlayerCreature pc;
		CreatureAttribute caStrength, caDexterity;
		
		if(!i.hasNext()){
			pc=new PlayerCreature(player);
			
			pc.setExp(0);
			pc.setExp_needed(5);
			pc.setLevel(1);
			pc.setHp(100);
			pc.setProfession(Profession.DEFAULT);
			pc.setName(player.getLogin()+"");
			
			caStrength = new CreatureAttribute();
			caStrength .setAttribute(Attribute.findByName(Attribute.STRENGTH));
			caStrength .setValue(10);
			caStrength .setCreature(pc);
			
			caDexterity = new CreatureAttribute();
			caDexterity .setAttribute(Attribute.findByName(Attribute.DEXTERITY));
			caDexterity .setValue(10);
			caDexterity .setCreature(pc);
			try {
				PlayerCreature.createDao().create(pc);
				PlayerCreature.createDao().refresh(pc);
				CreatureAttribute.createDao().create(caStrength);
				CreatureAttribute.createDao().create(caDexterity);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else{
			pc=i.next();
		}
		
		Location loc=GameServer.getInstance().getStartLocation();
		Creature c=new Creature(pc.getName());
		c.setDbCreature(pc);
		
		Mechanix.initEquipment(c);
		c.addBehaviour(new FightBehaviour(c));
		
		GameServer.getInstance().initCreature(user,c);
		
		c.setLocation(loc,null);
	}
}
