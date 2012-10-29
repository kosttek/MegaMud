package pl.edu.agh.megamud.base;

/*
 * Abstraction of a command to be executed by a controller.
 */
public interface Command {
	/*
	 * Short name that identified this command. E.g. exit, help. Note that many commands can have similar name - which will be executed is distinguished by interprete() result.
	 */
	public String getName();
	/*
	 * Run the command.
	 * @returns true, if:
	 * - user is allowed to run this command (he is an administrator or similar);
	 * - some preconditions are met, e.g. user is an location, holds an item, etc. This is used to distinguish whether this command is specific and "needed" by a creature. See getName().
	 * - command succedeed.
	 * Otherwise false.
	 */
	public boolean interprete(Controller user, String command);
}
