package pl.edu.agh.megamud.base;

import java.io.PrintWriter;
import java.util.Scanner;

import pl.edu.agh.megamud.Interpreter;
import pl.edu.agh.megamud.module.CommandExit;
import pl.edu.agh.megamud.module.CommandGoto;
import pl.edu.agh.megamud.module.CommandLook;
import pl.edu.agh.megamud.module.CommandSay;

public class User implements InteractiveObject{
	private boolean exitServer = false;
	public Scanner in ;
	public PrintWriter out ;

	public Creature player;
	private Interpreter interpreter = new Interpreter();
	
	Command [] commandsToInstall = {
			new CommandExit(),
			new CommandLook(),
			new CommandGoto(),
			new CommandSay()
			};
	
	public User(){
		player = new Creature();
		player.parent = this;
		for (Command command : commandsToInstall)
			interpreter.installCommand(command, this);
	}
	
	public boolean isExitServer() {
		return exitServer;
	}
	public  void setExitServer(boolean exitServer) {
		//TODO here could be done something before exit server
		this.exitServer = exitServer;
	}
	@Override
	public Interpreter getInterpreter() {
		return interpreter;
	}

	@Override
	public void setInterpreter(Interpreter interpreter) {
		this.interpreter = interpreter;		
	}
}
