package pl.edu.agh.megamud.mockdata;

import pl.edu.agh.megamud.GameServer;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.SimpleItem;

public class MockNPCs1 {
	public static void loadNpcs(){
		NPCController bot=new NPCController(){
			public void onEnter(Creature otherCreature){
				interpreteCommand("say","Welcome, useless "+otherCreature.getName()+". Find me an apple, or die!");
			}
			public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){
				if(to==getCreature() && from!=null && from instanceof Creature){
					Creature fromc=(Creature)from;
					
					if(item.getId().equals("apple")){
						interpreteCommand("say","Great, I like that. Have this for your quest.");
						
						Item prize=new SimpleItem("prize","Hiper-duper prize.");
						prize.giveTo(getCreature());
						
						interpreteCommand("give","prize "+fromc.getName());
						
						long delay=5000L;
						new Behaviour(to,delay){
							public void action(){
								interpreteCommand("say","Hahahaha, I tricked you. Watch as your dreams perish.");
							}
						}.init();
						new Behaviour(prize,delay){
							public void action(){
								Item i=(Item)owner;
								i.giveTo(null);
							}
						}.init();
					}else{
						interpreteCommand("say","I don't want this, I want an apple!");
						interpreteCommand("give","apple "+fromc.getName());
					}
				}
			}
		};
		final Creature botCreature=new Creature("Hohlik");
		final Location botRoom=GameServer.getInstance().getLocations().get(0);
		Item botBall=new Item("ball","Extreme expensive NIKE-signed foot-ball."){
			protected boolean canBeGivenTo(ItemHolder owner) {
				return owner==botCreature || owner==botRoom;
			}
			
		};
		
		GameServer.getInstance().initUser(bot);
		
		botCreature.connect(bot);
		botCreature.setLocation(botRoom,null);
		
		botCreature.setKlass("Troll");
		botCreature.setLevel(100);
		botCreature.setHp(666);
		botCreature.setMaxHp(666);
		
		botBall.giveTo(botRoom);
		
		new SayinNutsBehaviour(botCreature,5000L).init();
		new BallBounceBehaviour(botCreature,2500L).init();
	}
}
