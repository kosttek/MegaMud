package pl.edu.agh.megamud.base;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Object capable of collecting and managing commands. Hosted in a map (string
 * => list<commands>) - commands with the same name.
 */
public class CommandCollector  {
	private Map<String, List<Command>> map = new HashMap<String, List<Command>>();

	/**
	 * Gets all commands with specified name.
	 */
	public final List<Command> findCommands(String name) {
		return map.get(name);
	}

	/**
	 * Remove a command from this collector.
	 */
	public final void removeCommand(Command cmd) {
		List<Command> v = map.get(cmd.getName());
		if (v == null)
			return;
		v.remove(cmd);
		if (v.size() == 0)
			map.remove(cmd.getName());
	}

	/*
	 * Add a command to this collector.
	 */
	public final void addCommand(Command cmd) {
		List<Command> v = map.get(cmd.getName());
		if (v == null) {
			v = new LinkedList<Command>();
			map.put(cmd.getName(), v);
		}
		v.add(cmd);
	}

	/**
	 * Get a list of all commands supported by this collector.
	 */
	public final List<Command> getAllCommands() {
		List<Command> l = new LinkedList<Command>();
		for (Iterator<Entry<String, List<Command>>> i = map.entrySet()
				.iterator(); i.hasNext();) {
			Entry<String, List<Command>> e = i.next();
			for (Iterator<Command> c = e.getValue().iterator(); c.hasNext();)
				l.add(c.next());
		}
		return l;
	}
}
