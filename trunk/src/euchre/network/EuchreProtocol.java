/**
 * 
 */
package euchre.network;

import java.util.StringTokenizer;

/**
 * @author mdhelgen
 * Facilitates network communication between the clients and the server. 
 */
public class EuchreProtocol {
	
	
	/**
	 * Get any necessary references
	 * 
	 */
	public EuchreProtocol(){
		
		
	}
	
	
	public void parse(String input){
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
			else{
				System.out.println("Undefined token:" + token);
			}
				
			
			
		}
		
	}
	
	public void setName(String name, int number){
		System.out.println("Player name: " + name);
		System.out.println("Player number: " + number);
		
	}
	
	
	

}
