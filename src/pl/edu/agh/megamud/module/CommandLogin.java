package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Controller;
import pl.edu.agh.megamud.base.Location;

public class CommandLogin implements Command {
	private static int num=0;
	public String getName(){
		return "login";
	}
	
	public boolean interprete(Controller user, String command) {
		if(user.getCreature()!=null)
			return false;
		
		String [] args = command.trim().split(" ");
		if(args.length<2){
			user.write("Invalid parameters!");
			return true;
		}
		String login=args[0];
		String pass=args[1];
		
		if(login.equals("admin") && pass.equals("admin")){
			user.write("Login successfull!");
			
			Location loc=GameServer.getInstance().getLocations().get(0);
			Creature c=new Creature("Admin-"+num);
			c.connect(user);
			c.setLocation(loc,null);
			
			num++;
		}else{
			user.write("Invalid login! Use admin + admin");
		}
		return true;
	}

}
