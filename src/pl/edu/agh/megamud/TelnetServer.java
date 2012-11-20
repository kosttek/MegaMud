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

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		DbManager.init();

		GameServer.getInstance();

		while (true) {

			try {
				// establish server socket
				s = new ServerSocket(44449);

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

}