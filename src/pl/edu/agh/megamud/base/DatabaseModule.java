package pl.edu.agh.megamud.base;

import java.lang.reflect.Constructor;
import java.util.LinkedList;
import java.util.List;

import pl.edu.agh.megamud.GameServer;

/**
 * A module that grabs possible locations and NPCs from database.
 * @author Tomasz
 */
public abstract class DatabaseModule extends Module{
	
	/**
	 * Initialization:
	 * - installs all locations from database, that are bound to module getId().
	 * - the same for NPCs, but using reflection. 
	 */
	protected void init(){
		/**
		 * @todo Loading locations
		 * 1. load location IDs and descriptions
		 *   SELECT location.id, location.desc FROM location WHERE module=getId()
		 * 2. install them
		 */
		List<Location> locs=new LinkedList<Location>();
		for(;;){
			String id="id";
			String desc="desc";
			
			Location l=new Location(id,desc,this);
			locs.add(l);
			
			installLocation(l);
			
			break;
		}
		/**
		 * @todo
		 * 3. load exits (exit name + next location ID) and link to existing locations
		 *   SELECT exit.to_id, exit.name FROM exit WHERE from_id=getId()
		 */
		for(Location from: locs){
			for(;;){
				String name="exit";
				String toId="other";
				Location end=GameServer.getInstance().getLocation(toId);
				if(end==null)
					continue;
				from.addExit("drzwi", end);
			}
		}
		
		/**
		 * @todo Loading NPCs
		 * 1. load stored npc data
		 *   SELECT npc.id, creature.name, creature.class, creature.klazz, etc FROM npcs
		 *     INNER JOIN npc.creature = creature.id
		 *     WHERE npc.module = getId()
		 *   class = in-java class of NPCController
		 *   klazz = in-game creature's class
		 *   Creature data is to be stored as any other creature.
		 * 2. install'em
		 */
		
		for(;;){
			String npcId="id";
			String creatureName="name";
			String creatureClass="pl.edu.agh.megamud.Base.NPCController";
			
			String creatureLocation="start";
			
			try{
				@SuppressWarnings("unchecked")
				Class<NPCController> klazz=(Class<NPCController>) Class.forName(creatureClass);
				Constructor<NPCController> cons=klazz.getConstructor();
				NPCController bot=cons.newInstance();
						
				Creature c=new Creature(creatureName);
				
				Location loc=GameServer.getInstance().getLocation(creatureLocation);
				
				installNPC(bot,c,loc);
			}catch(Exception e){
				e.printStackTrace();
			}
			break;
		}
	}
	
}
