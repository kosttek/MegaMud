package pl.edu.agh.megamud;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import pl.edu.agh.megamud.base.Creature;
import pl.edu.agh.megamud.base.User;

public class Session implements Runnable {
	public  Socket incoming = null;
	public User user;
	
	public Session(Socket sock) {
		incoming = sock;
	}
	
	@Override
	public void run() {
		try {
		
		initUser();
		user.out.println("Server running...");
		
		while (!user.isExitServer() && user.in.hasNextLine()) {


			String line = user.in.nextLine();
			GameServer.getInstance().interpreteCommand(user, line);
//			user.out.println("Echo: " + line);
			

		}
		
		
			incoming.close();
			incoming = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private void initUser() throws IOException{
		user = new User();
		InputStream inps = incoming.getInputStream();
		OutputStream outs = incoming.getOutputStream();

		Scanner in = new Scanner(inps);
		PrintWriter out = new PrintWriter(outs, true);
		user.in = in;
		user.out = out;
		
		GameServer.getInstance().initUser(user);
	}
	
}
