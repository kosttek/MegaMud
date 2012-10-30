package pl.edu.agh.megamud.mockdata;

import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.module.*;

public class MockCommands1 {
	private MockCommands1(){

	}
	private static Map<String,Command> basicCommands=new HashMap<String,Command>();
	static{
		basicCommands.put("exit",new CommandExit());
		basicCommands.put("goto",new CommandGoto());
		basicCommands.put("help",new CommandHelp());
		basicCommands.put("info",new CommandInfo());
		basicCommands.put("kill",new CommandKill());
		basicCommands.put("login",new CommandLogin());
		basicCommands.put("look",new CommandLook());
		basicCommands.put("take",new CommandTake());
		basicCommands.put("give",new CommandGive());
		basicCommands.put("drop",new CommandDrop());
		basicCommands.put("say",new CommandSay());
	}
	public final static Command getBasicCommand(String x){
		return basicCommands.get(x);
	}
}
