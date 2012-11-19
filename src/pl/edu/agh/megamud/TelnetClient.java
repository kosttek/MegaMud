package pl.edu.agh.megamud;
import java.net.*;
import java.io.*;
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class TelnetClient extends Applet
{	
	
	LinkedList<String> commandList = new LinkedList<String>();
	private Socket socket              = null;
	private DataInputStream  console   = null;
//private DataOutputStream streamOut = null;
	private PrintStream streamOut  = null;
	private TelnetClientThread client    = null;
	private TextArea  display = new TextArea();
	private int currentHistoryIndex = -1;
	private TextField input   = new TextField();
	private Button    send    = new Button("Send"), connect = new Button("Connect"),
                     quit    = new Button("Bye");
	private String f1Buttonaction = "";
	private String f2Buttonaction = "";
	private String f3Buttonaction = "";
	private String f4Buttonaction = "";
	private String f5Buttonaction = "";
	private String f6Buttonaction = "";
	private String f7Buttonaction = "";
	private String f8Buttonaction = "";
	private String f9Buttonaction = "";
	private String f10Buttonaction = "";
	private String f11Buttonaction = "";
	private String f12Buttonaction = "";
	private String    serverName = "localhost";
	private int       serverPort = 44449;
  // PrintStream dout=new PrintStream(soc.getOutputStream());
   public void init()
   {  Panel keys = new Panel(); keys.setLayout(new GridLayout(1,2));
      keys.add(quit); keys.add(connect);
      Panel south = new Panel(); south.setLayout(new BorderLayout());
      south.add("West", keys); south.add("Center", input);  south.add("East", send);
      
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_ENTER) {
              
               	send();
               }
            }
          } 
       );

      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F1) {
            	input.setText(f1Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_UP) {
            	if(!commandList.isEmpty()){
            	int idx = currentHistoryIndex;//commandList.indexOf(input.getText());
            	if (idx < 0) {
            		currentHistoryIndex = commandList.size() - 1;
            		input.setText(commandList.getLast());
            	}
            	else{
                	if (idx == 0) {
                		input.setText(commandList.getFirst());
                	//	currentHistoryIndex=;
                	}else{
            		String newText =  commandList.get(idx - 1);
            		input.setText(newText);
            		currentHistoryIndex--;
//            	if(commandList.indexOf(input.getText()).hasPrevious()){
//            		String newText = commandList.indexOf(input.getText()).previousElement();
//            	}
            	}
            	
            	}
               }
            }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_DOWN) {
            	if(!commandList.isEmpty()){
            	int idx = currentHistoryIndex;//commandList.indexOf(input.getText());
            	if (idx >= 0 && idx < commandList.size()) {

            		String newText =  commandList.get(currentHistoryIndex);
            		input.setText(newText);
            		currentHistoryIndex++;
            	}
            	else{
            		currentHistoryIndex = -1;
            		input.setText("");
            	}
               }
            }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F2) {
            	input.setText(f2Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F3) {
            	input.setText(f3Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F4) {
            	input.setText(f4Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F5) {
            	input.setText(f5Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F6) {
            	input.setText(f6Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F7) {
            	input.setText(f7Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F8) {
            	input.setText(f8Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F9) {
            	input.setText(f9Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F10) {
            	input.setText(f10Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F11) {
            	input.setText(f11Buttonaction);
               }
            }
          } 
       );
      input.addKeyListener
      (new KeyAdapter() {
          public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_F12) {
            	input.setText(f12Buttonaction);
               }
            }
          } 
       );
      Label title = new Label("Simple Telnet Client Applet", Label.CENTER);
      title.setFont(new Font("Helvetica", Font.BOLD, 14));
      setLayout(new BorderLayout());
      add("North", title); add("Center", display);  add("South",  south);
      quit.disable(); send.disable();
      
      println("Welcome into MUD console. To associate any actions with key use function:  set_key [key] [text].");   
 
   }
   public boolean action(Event e, Object o)
   {  if (e.target == quit)
      {  input.setText(".bye");
         send();  quit.disable(); send.disable(); connect.enable(); }
      else if (e.target == connect)
      {  connect("localhost", 44449); }
      else if (e.target == send)
      {  send(); input.requestFocus(); }
      return true; }
   public void connect(String serverName, int serverPort)
   {  println("Establishing connection. Please wait ...");
      try
      {  socket = new Socket("localhost", 44449);
         println("Connected: " + socket);
         open(); send.enable(); connect.disable(); quit.enable(); }
      catch(UnknownHostException uhe)
      {  println("Host unknown: " + uhe.getMessage()); }
      catch(IOException ioe)
      {  println("Unexpected exception: " + ioe.getMessage()); } }
   private void send()
   { // try
  //    { 
	   currentHistoryIndex = -1;
	   String commandText = input.getText();

	   commandList.add(commandText);   
	   
	   if(commandText.split(" ")[0].equals("set_key")){
		   if(commandText.split(" ").length!=3){
		       println("Wrong usage of set_key. Try: set_key [key] [text]");
		       input.setText("");
		       return;

		   }
	       switch (commandText.split(" ")[1]) {
           case "F1" : 
        	   f1Buttonaction = commandText.split(" ")[2];
        	      input.addKeyListener
        	      (new KeyAdapter() {
        	          public void keyPressed(KeyEvent e) {
        	            int key = e.getKeyCode();
        	            if (key == KeyEvent.VK_F1) {
        	            	input.setText(f1Buttonaction);
        	               }
        	            }
        	          } 
        	       );
        	   
                    break;
           case "F2" :         f2Buttonaction = commandText.split(" ")[2]; 	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F2) {
	            	input.setText(f2Buttonaction);
	               }
	            }
	          } 
	       );
	   
                    break;
           case "F3" :          f3Buttonaction = commandText.split(" ")[2]; 	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F3) {
	            	input.setText(f3Buttonaction);
	               }
	            }
	          } 
	       );
	   
                    break;
           case "F4" :        	  f4Buttonaction = commandText.split(" ")[2];      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F4) {
	            	input.setText(f4Buttonaction);
	               }
	            }
	          } 
	       );
	   
                    break;
           case "F5" :      f5Buttonaction = commandText.split(" ")[2];     	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F5) {
	            	input.setText(f5Buttonaction);
	               }
	            }
	          } 
	       );
	   
                    break;
           case "F6" :        f6Buttonaction = commandText.split(" ")[2];   	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F6) {
	            	input.setText(f6Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           case "F7" :        f7Buttonaction = commandText.split(" ")[2];   	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F7) {
	            	input.setText(f7Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           case "F8" :      f8Buttonaction = commandText.split(" ")[2];     	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F8) {
	            	input.setText(f8Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           case "F9" :          f9Buttonaction = commandText.split(" ")[2]; 	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F9) {
	            	input.setText(f9Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           case "F10" :      f10Buttonaction = commandText.split(" ")[2];     	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F10) {
	            	input.setText(f10Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           case "F11" :          f11Buttonaction = commandText.split(" ")[2]; 	      input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F11) {
	            	input.setText(f11Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           case "F12" :         f12Buttonaction = commandText.split(" ")[2];
        	   input.addKeyListener
 	      (new KeyAdapter() {
	          public void keyPressed(KeyEvent e) {
	            int key = e.getKeyCode();
	            if (key == KeyEvent.VK_F12) {
	            	input.setText(f12Buttonaction);
	               }
	            }
	          } 
	       );
	   ;
                    break;
           default: 		       println("Wrong usage of set_key. You cant associate any action with key "+commandText.split(" ")[2]+". Possible keys: F1, F2, F3, F4, F5, F6, F7, F8, F9, F10, F11 or F12");
	       input.setText("");
	       return;
                  
       }
	       println("New shortkey has been made. From now on use "+commandText.split(" ")[1]+" to write "+commandText.split(" ")[2]+".");
	       input.setText("");
	       return;
	   }
	   
	   streamOut.println(input.getText());//writeUTF(input.getText()); streamOut.flush();
	   input.setText(""); }
 //     catch(IOException ioe)
//     {  println("Sending error: " + ioe.getMessage()); close(); } 
// }
   public void handle(String msg)
   {  if (msg.equals(".bye"))
      {  println("Good bye. Press RETURN to exit ...");  close(); }
      else println(msg); }
   public void open()
   {  try
      {  streamOut = //new DataOutputStream(socket.getOutputStream());
    		  new PrintStream(socket.getOutputStream());
         client = new TelnetClientThread(this, socket); }
      catch(IOException ioe)
      {  println("Error opening output stream: " + ioe); } }
   public void close()
   {  try
      {  if (streamOut != null)  streamOut.close();
         if (socket    != null)  socket.close(); }
      catch(IOException ioe)
      {  println("Error closing ..."); }
      client.close();  client.stop(); }
   private void println(String msg)
   {  display.appendText(msg + "\n"); }
   public void getParameters()
   {  serverName = "localhost";
      serverPort = 44449; }
}