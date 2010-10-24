/**
 * 
 */
package euchre.network;

import java.util.StringTokenizer;

import euchre.player.GameManager;
import euchre.player.Human;

/**
 * @author mdhelgen
 * Facilitates network communication between the clients and the server. 
 */
public class EuchreProtocol {
	
	private GameManager manager;
	private ClientNetworkManager client;
	private ServerNetworkManager server;
	
	/**
	 * Get any necessary references
	 * 
	 */
	public EuchreProtocol(){
		
		
	}
	
	
	public void serverParse(String input){
		String token;
		StringTokenizer parser = new StringTokenizer(input,",");
		System.out.println("PARSING '" + input + "'");
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
				if(manager.getPlayer2() == null)
				{
					manager.getPlayer2().setName(name);
					server.toClients("SetPlayerName,"+name+",2");
					
				}
				if(manager.getPlayer3() == null)
				{
					manager.getPlayer3().setName(name);
					server.toClients("SetPlayerName,"+name+",3");
					
				}
				if(manager.getPlayer4() == null)
				{
					manager.getPlayer4().setName(name);
					server.toClients("SetPlayerName,"+name+",4");
					
				}
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
				String name = parser.nextToken();
				int num = Integer.parseInt(parser.nextToken());
				switch(num){
				case 1:
					manager.getPlayer1().setName(name);
					break;
				case 2:
					manager.getPlayer2().setName(name);
					break;
				case 3:
					manager.getPlayer3().setName(name);
					break;
				case 4:
					manager.getPlayer4().setName(name);
					break;
	
				}
				
				
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
	
	public void setClientNetworkManager(ClientNetworkManager c){
		client = c;
	}
	
	public void setServerNetworkManager(ServerNetworkManager s){
		server = s;
	}
	
	
	

}
