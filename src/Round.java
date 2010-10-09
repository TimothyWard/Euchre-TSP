import euchre.player.*;
/**
 * 
 * @author Timothy Ward
 * 
 * This class holds all information pertaining to a single round. The only class in communication with this one is the
 * Game class. The game class writes information to this class as it becomes available from network. This object is then
 * passed to GameLogic, which interprets and passes back pertinent information to Game.
 *
 */
public class Round{
	protected char trumpSuit = ' ';
	protected Player playerWhoOrdered;
	protected char teamWhoOrdered = ' ';
	protected char suitLed = ' ';
	protected boolean alone = false;
	protected Card[] cardsPlayed = new Card[20];
}
