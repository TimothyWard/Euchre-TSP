/**
 * 
 */
package euchre.network;

import java.util.StringTokenizer;

import euchre.player.GameManager;

/**
 * @author mdhelgen
 * Facilitates network communication between the clients and the server. 
 */
public class EuchreProtocol {
	
	private GameManager manager;
	
	/**
	 * Get any necessary references
	 * 
	 */
	public EuchreProtocol(){
		
		
	}
	
	
	public void serverParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		while(parser.hasMoreTokens()){
			token = parser.nextToken();
		
			//"name", player name, player number
			if(token.equals("Name")){
				String name = parser.nextToken();
				int number = Integer.parseInt(parser.nextToken());
				
				setName(name, number);
			}
			else if(token.equals("RegisterPlayer")){
				String name = parser.nextToken();
				
			}
			else{
				System.out.println("Undefined token: " + token);
			}
				
			
			
		}
		
	}
	
	public void clientParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		while(parser.hasMoreTokens()){
			token = parser.nextToken();
			if(token.equals("SetPlayerName")){
				
			}
			else
				System.out.println("Undefined token: " + token);
		}
		
	}
	
	public void setName(String name, int number){
		System.out.println("Player name: " + name);
		System.out.println("Player number: " + number);
		
		
	}
	
	public void setGameManager(GameManager gm){
		manager = gm;
	}
	
	
	

}
