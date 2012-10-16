package pl.edu.agh.megamud.utils;

import pl.edu.agh.megamud.base.Command;

public class StringCommandTuple{
	public String string;
	public Command command;
	
	public StringCommandTuple(String string , Command command){
		this.string = string;
		this.command = command;		
	}

}