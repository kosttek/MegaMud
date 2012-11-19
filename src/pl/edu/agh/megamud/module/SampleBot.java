package pl.edu.agh.megamud.module;

import pl.edu.agh.megamud.base.Behaviour;
import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.CyclicBehaviour;
import pl.edu.agh.megamud.base.Item;
import pl.edu.agh.megamud.base.ItemHolder;
import pl.edu.agh.megamud.base.Location;
import pl.edu.agh.megamud.base.NPCController;
import pl.edu.agh.megamud.base.SimpleItem;
import pl.edu.agh.megamud.base.SimpleModifier;

public class SampleBot extends NPCController {
	private Item ball;
	private CyclicBehaviour ballBehaviour;
	
	public SampleBot(){
		super();
	}
	
	public void onEnter(Creature otherCreature){
		interpreteCommand("say","Welcome, useless "+otherCreature.getName()+". Find me an apple, or die!");
	}
	
	public void onItemTransfer(ItemHolder from,ItemHolder to,Item item){
		if(to==getCreature() && from!=null && from instanceof Creature){
			Creature fromc=(Creature)from;
			
			if(item.getName().equals("apple")){
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
				
				fromc.addModifier(new SimpleModifier(fromc, "power", +10, delay));
			}else{
				interpreteCommand("say","I don't want this, I want an apple!");
				interpreteCommand("give","apple "+fromc.getName());
			}
		}
	}
	
	public void setLocation(Location exit,String exitName){
		if(ball!=null){
			ball.giveTo(null);
			ballBehaviour.setDone(true);
		}
		
		ball=new Item("ball","Extreme expensive NIKE-signed foot-ball."){
			protected boolean canBeGivenTo(ItemHolder owner) {
				return owner==SampleBot.this.getCreature() || owner==SampleBot.this.getCreature().getLocation();
			}
			
		};
		ball.giveTo(getCreature());
		
		ballBehaviour=(CyclicBehaviour)new CyclicBehaviour(getCreature().getLocation(),2500L){
			public void action() {
				Creature owner = (Creature) getOwner();
				
				if(owner.getItems().size()==0){
					SampleBot.this.interpreteCommand("take", "ball");
				}else{
					SampleBot.this.interpreteCommand("drop", "ball");
				}
			}
		}.init();
	}
}
