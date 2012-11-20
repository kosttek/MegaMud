package pl.edu.agh.megamud.base;

import java.util.Map;

import pl.edu.agh.megamud.dao.Attribute;

/**
 * A temporary modifier to creature's attributes. Works like that: - creature
 * has basic attributes/stats - creature gets a modifier, in a fight or as a
 * result of an item - everytime attributes are needed (= nearly always), a
 * modifier can modify temporary stats of a creature - it can eventually self
 * destruct (if for example it is a 30sec modifier) Examples: an apple can give
 * +5 strength for 5 minutes.
 */

public interface Modifier {
	/**
	 * Owner of this modifier.
	 */
	public Creature getCreature();

	/**
	 * If not null, then this holds a behaviour already bound to creature, that
	 * will eventually self-destruct the modifier.
	 */
	public Behaviour willSelfDestruct();

	/**
	 * It's name.
	 */
	public String getName();

	/**
	 * Modify any attribute from attrs. Returns false, if the modifier expired.
	 */
	public boolean modify(Creature c, Map<Attribute, Long> attrs);

	/**
	 * Executed upon binding to a creature. Shall send some information.
	 */
	public void onBegin();

	/**
	 * Executed upon disappearing.
	 */
	public void onStop();
}
