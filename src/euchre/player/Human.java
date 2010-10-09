package euchre.player;

public class Human implements Player{	
	
	@Override
	public void drawCard(Card c){
		for(int x = 0; x < 5; x++){
			if(hand[x] == null){
				hand[x] = c;
				return;
			}
		}
		System.out.println("error: hand is full");
	}

	@Override
	public void setTurn(){
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean orderUp(){
		// TODO Auto-generated method stub
		return false;
	}

}
