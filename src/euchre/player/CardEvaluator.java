package euchre.player;


/**
 * A class that is used to evaluate cards.
 * 
 * @author Neil MacBay (nmmacbay)
 */
public class CardEvaluator {
	
	/**
	 * Given the trump and lead suit will determine the integer value of a card (Higher values winning).
	 * 
	 * Values of all cards:
	 * Right Bower = 13
	 * Left Bower = 12
	 * Ace (Trump) = 11
	 * King (Trump) = 10
	 * Queen (Trump) = 9
	 * Ten (Trump) = 8
	 * Nine (Trump) = 7
	 * Ace (Lead) = 6
	 * King (Lead) = 5
	 * Queen (Lead) = 4
	 * Jack (Lead) = 3
	 * Ten (Lead) = 2
	 * Nine (Lead) = 1
	 * Ace (Off-Suite) = 0
	 * King (Off-Suite) = -1
	 * Queen (Off-Suite) = -2
	 * Jack (Off-Suite) = -3
	 * Then (Off-Suite) = -4
	 * Nine (Off-Suite) = -5
	 * 
	 * @return The value of the given card.
	 */
	public static int cardValue(char trump, char lead, Card card){
		Card TRB = new Card('j', trump);
		Card TLB = new Card('j', trump);
		Card TA = new Card('a', trump);
		Card TK = new Card('k', trump);
		Card TQ = new Card('q', trump);
		Card T10 = new Card('0', trump);
		Card T9 = new Card('9', trump);
		Card LA = new Card('a', lead);
		Card LK = new Card('k', lead);
		Card LQ = new Card('q', lead);
		Card LJ = new Card('j', lead);
		Card L10 = new Card('0', lead);
		Card L9 = new Card('9', lead);
		int cardValue;
		if (card.compareTo(TRB)==0){ 
			cardValue = 13;
		}
		else if (card.compareTo(TLB)==0){
			cardValue = 12;
		}
		else if (card.compareTo(TA)==0){
			cardValue = 11;
		}
		else if (card.compareTo(TK)==0){
			cardValue = 10;
		}
		else if (card.compareTo(TQ)==0){
			cardValue = 9;
		}
		else if (card.compareTo(T10)==0) {
			cardValue = 8;
		}
		else if (card.compareTo(T9)==0) {
			cardValue = 7;
		}
		else if (card.compareTo(LA)==0) {
			cardValue = 6;
		}
		else if (card.compareTo(LK)==0) {
			cardValue = 5;
		}
		else if (card.compareTo(LQ)==0) {
			cardValue = 4;
		}
		else if (card.compareTo(LJ)==0) {
			cardValue = 3;
		}
		else if (card.compareTo(L10)==0) {
			cardValue = 2;
		}
		else if (card.compareTo(L9)==0) {
			cardValue = 1;
		}
		else{ //not lead or trump.
			switch (card.value){
			case '9':
				cardValue = -5;
				break;
			case '0':
				cardValue = -4;
				break;
			case 'j':
				cardValue = -3;
				break;
			case 'q':
				cardValue = -2;
				break;
			case 'k':
				cardValue = -1;
				break;
			default: //case 'a'
				cardValue = 0;
				break;
			}
		}
		return cardValue;
	}
	
	
}
