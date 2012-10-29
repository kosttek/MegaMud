package pl.edu.agh.megamud.mockdata;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.SimpleModifier;

public class MockNPCs1 {
	public static void loadNpcs(){
		NPCController bot=new NPCController(){
			public void onEnter(Creature otherCreature){
				interpreteCommand("say","Welcome, useless "+otherCreature.getName()+". Prepare to DIE!");
				new SimpleModifier(otherCreature,"Power remover","power",-10000L,1000);
			}
		};
		GameServer.getInstance().initUser(bot);
		
		Creature c=new Creature("Hohlik");
		c.connect(bot);
		c.setLocation(GameServer.getInstance().getLocations().get(0),null);
		
		new SayinNutsBehaviour(c,5000L).init();
	}
}
