/*******************************************************************************
 * Copyright (c) 2012, AGH
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package pl.edu.agh.megamud.world;

public class OldmanTalk {
	String [][] ask0 = {{"numbers","I am not Matematician !","1"},
			{"anything","hehe You are very clever"}};
	
	String [][] ask1 = {{"numbers","I told yaa I am not Matematician !"},
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
