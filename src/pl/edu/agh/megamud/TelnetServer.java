package pl.edu.agh.megamud;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import pl.edu.agh.megamud.base.DbManager;

public class TelnetServer {

	public static ServerSocket s = null;

	public static Socket incoming = null;
	
	private static int socketPort = 44449;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		DbManager.init();
		parseArguments(args);

		GameServer.getInstance();

		while (true) {

			try {
				// establish server socket
				s = new ServerSocket(socketPort);

				// wait for incoming connection
				incoming = s.accept();

				executor.execute(new Session(incoming));

			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				s.close();
				s = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static void parseArguments(String [] args) {
		if(args.length==1){
			String arg = args[0];
			
			try{
			socketPort = Integer.parseInt(arg);
			
			}catch(NumberFormatException e){
				e.printStackTrace();
			}
		}
		
	}

}