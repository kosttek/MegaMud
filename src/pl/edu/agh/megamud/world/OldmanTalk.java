package pl.edu.agh.megamud.world;

public class OldmanTalk {
	String [][] ask0 = {{"numbers","I am not Matemaician !","1"},
			{"anything","hehe You are very clever"}};
	
	String [][] ask1 = {{"numbers","I told yaa i am not Matematician !"},
			{"why","I do not like study Math"}};
	
	int state = 0;
	
	public String ask(String command){
		
		switch (state){
			case 0 :	return stateNormal(command); 
			case 1 : 	return stateMathematic(command);
		}
		return null;
	}
	
	private String stateNormal(String command){
		return checkTable(ask0, command);
	}
	
	private String stateMathematic(String command){
		return checkTable(ask1, command);
	}

	
	private String checkTable (String [][] tab,String command){
		command = command.split(" ")[0];
		command = command.toLowerCase();
		
		for(int i = 0 ; i < tab.length ;i++){	
			if(tab[i][0].equals(command)){
				if(tab[i].length==3)
					state = Integer.parseInt(tab[i][2]);
				return tab[i][1];
			}
		}
		return null;
	}
}
