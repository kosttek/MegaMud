package pl.edu.agh.megamud;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TelnetServer {

	public static ServerSocket s = null;

	public static Socket incoming = null;

	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
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
				System.out.println("end");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}
	
	static void test(){
		HashMap<String,Integer> hm = new HashMap<String, Integer>();
		hm.put(new String("xxx"),new Integer(1));
		System.out.println(hm.get(new String("xxx")));
	}

}