package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A location in our world. Location has its description, exits and creatures
 * inside. A location can serve a controller own commands (thus it extends
 * CommandCollector).
 */
public class Location extends ItemHolder implements BehaviourHolderInterface {
	private BehaviourHolder behaviourHolder = new BehaviourHolder();
	private Map<String, Location> exits = new HashMap<String, Location>();
	private Map<String, Creature> creatures = new HashMap<String, Creature>();
	private String id;
	private String description;
	// private Map<String, List<Command>> map = new HashMap<String,
	// List<Command>>();

	private pl.edu.agh.megamud.dao.Location dbLocation = null;

	public pl.edu.agh.megamud.dao.Location getDbLocation() {
		return dbLocation;
	}

	public void setDbLocation(pl.edu.agh.megamud.dao.Location dbLocation) {
		this.dbLocation = dbLocation;
	}

	public Location(String id, String description, Module module) {
		this.id = id;
		this.description = description;
	}

	public final Map<String, Location> getExits() {
		return exits;
	}

	public final Map<String, Creature> getCreatures() {
		return creatures;
	}

	public String getId() {
		return this.id;
	}

	/**
	 * Use this for location initialisation - add an exit.
	 */
	public final void addExit(String name, Location loc) {
		exits.put(name, loc);
	}

	/**
	 * Executed after a creature entered the room. Notifies other creatures and
	 * sends the "look" command result.
	 */
	public void onAddCreature(Creature creature) {
		creature.controller.write(prepareLook());
		creatures.put(creature.getName(), creature);

		for (Creature c : creatures.values())
			if (c != creature)
				c.controller.onEnter(creature);
	}

	/**
	 * Executed after a creature left this room. Notifies other creatures.
	 */
	public void onRemoveCreature(Creature creature, String usedExit) {
		creatures.remove(creature.getName());
		for (Creature c : creatures.values())
			if (c != creature)
				c.controller.onLeave(creature, usedExit);
	}

	/**
	 * Executed after a creature "says" something.
	 */
	public void onCreatureSay(Creature creature, String s) {
		for (Creature c : creatures.values())
			c.controller.onSayInLocation(creature, s);
	}

	public void onItemTransfer(ItemHolder oldOwner, ItemHolder newOwner, Item i) {
		for (Creature c : creatures.values())
			c.controller.onItemTransfer(oldOwner, newOwner, i);
	}

	public void onItemAppear(Item i, ItemHolder from) {
		for (Creature c : creatures.values())
			c.controller.onItemTransfer(from, this, i);
	}

	public void onItemDisappear(Item i, ItemHolder to) {
		for (Creature c : creatures.values())
			c.controller.onItemTransfer(this, to, i);
	}

	public String getDescription() {
		return description;
	}

	/**
	 * Result for command "look". Contains location description, all exists and
	 * other creatures.
	 */
	public final String prepareLook() {
		String desc = "" + getDescription() + "\r\n";
		desc += "Possible exits: ";

		int cnt = exits.size();
		for (String locationPointer : exits.keySet())
			desc += locationPointer + (cnt > 0 ? ", " : "");
		desc += "\r\n";

		for (Item i : items.values())
			desc += "Here is " + i.getName() + " - " + i.getDescription()
					+ "\r\n";
		desc += "\r\n";

		for (Creature creature : creatures.values()) {
			desc += "Here is " + creature.getName() + ", a LV"
					+ creature.getLevel() + " "
					+ creature.getProfession().getName() + ".\r\n";
		}

		return desc;
	}

	@Override
	public List<Behaviour> getBehaviourList() {
		return behaviourHolder.getBehaviourList();
	}

	@Override
	public void setBehaviourList(List<? extends Behaviour> list) {
		behaviourHolder.setBehaviourList(list);
	}

	@Override
	public void addBehaviour(Behaviour behaviour) {
		behaviourHolder.addBehaviour(behaviour);
	}

	@Override
	public void removeBehaviour(Behaviour behaviour) {
		behaviourHolder.removeBehaviour(behaviour);

	}

	@Override
	public List<Behaviour> getBehaviourByType(Class<? extends Behaviour> clazz) {
		return behaviourHolder.getBehaviourByType(clazz);
	}
	// public List<Command> findCommands(String name) {
	// return map.get(name);
	// }
	//
	// /**
	// * Remove a command from this collector.
	// */
	// public void removeCommandLocation(Command cmd) {
	// List<Command> v = map.get(cmd.getName());
	// if (v == null)
	// return;
	// v.remove(cmd);
	// if (v.size() == 0)
	// map.remove(cmd.getName());
	// }
	//
	// /*
	// * Add a command to this collector.
	// */
	// public final void addCommandLocation(Command cmd) {
	// List<Command> v = map.get(cmd.getName());
	// if (v == null) {
	// v = new LinkedList<Command>();
	// map.put(cmd.getName(), v);
	// }
	// v.add(cmd);
	// }
	//
	// /**
	// * Get a list of all commands supported by this collector.
	// */
	// public final List<Command> getAllCommandsLocation() {
	// List<Command> l = new LinkedList<Command>();
	// for (Iterator<Entry<String, List<Command>>> i = map.entrySet()
	// .iterator(); i.hasNext();) {
	// Entry<String, List<Command>> e = i.next();
	// for (Iterator<Command> c = e.getValue().iterator(); c.hasNext();)
	// l.add(c.next());
	// }
	// return l;
	// }
	//
}
