package pl.edu.agh.megamud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.edu.agh.megamud.base.Command;
import pl.edu.agh.megamud.base.InteractiveObject;
import pl.edu.agh.megamud.utils.StringCommandTuple;


public class Interpreter {
	private Map <String , List<Command>> map = new HashMap<String, List<Command>>();
	
	public List<Command> getCommands(String firstWord){
		return map.get(firstWord);
	}
	
	public void installCommand(Command command, InteractiveObject interactiveObject){
		List<StringCommandTuple> commands = command.install(interactiveObject);
		
		for(StringCommandTuple sct : commands){
			
			if(map.containsKey(sct.string)){
				List<Command> value = map.get(sct.string);	
				if(!value.contains(sct.command))
					value.add(sct.command);
				
			}else{	
				List<Command> value = new ArrayList<Command>();
				value.add(sct.command);
				map.put(sct.string, value);
			}
		}
	}
	
}

