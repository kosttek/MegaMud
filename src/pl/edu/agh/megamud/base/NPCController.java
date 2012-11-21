package pl.edu.agh.megamud.base;

import pl.edu.agh.megamud.GameServer;

/**
 * A NPC inside the game.
 **/
public class NPCController extends Controller {
	public NPCController() {
		super();
	}

	public void onDie() {
		GameServer.getInstance().killController(this);
	}
	
	public void onStart(){
		
	}
}
