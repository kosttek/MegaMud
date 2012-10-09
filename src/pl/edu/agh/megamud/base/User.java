package pl.edu.agh.megamud.base;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class User {
	private boolean exitServer = false;
	public Scanner in ;
	public PrintWriter out ;

	public Creature player;
	
	public User(){
		player = new Creature();
		player.parent = this;
	}
	
	public boolean isExitServer() {
		return exitServer;
	}
	public  void setExitServer(boolean exitServer) {
		//TODO here could be done something before exit server
		this.exitServer = exitServer;
	}
}
