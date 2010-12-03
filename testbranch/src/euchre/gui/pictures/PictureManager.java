package euchre.gui.pictures;

import javax.swing.*;

/**
 * A class for managing card pictures, to make them easier to access. Acts primarily as a library of pictures.
 * 
 * @author Neil MacBay (nmmacbay)
 */
public class PictureManager {
	
	//Variables for the various card images. naming convention of suite-value
	private ImageIcon h9;
	private ImageIcon h10;
	private ImageIcon ha;
	private ImageIcon hj;
	private ImageIcon hq;
	private ImageIcon hk;
	private ImageIcon d9;
	private ImageIcon d10;
	private ImageIcon da;
	private ImageIcon dj;
	private ImageIcon dq;
	private ImageIcon dk;
	private ImageIcon s9;
	private ImageIcon s10;
	private ImageIcon sa;
	private ImageIcon sj;
	private ImageIcon sq;
	private ImageIcon sk;
	private ImageIcon c9;
	private ImageIcon c10;
	private ImageIcon ca;
	private ImageIcon cj;
	private ImageIcon cq;
	private ImageIcon ck;
	//Other card images that are not exactly "cards".
	private ImageIcon back;
	private ImageIcon sidewaysBack;
	private ImageIcon empty;
	private ImageIcon sidewaysEmpty;
	
	/**
	 * Constructs a new picture manager object.
	 */
	public PictureManager(){
		//assign the various pictures to their variables.
		h9 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/9h.png"));
		h10 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/10h.png"));
		ha = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/ah.png"));
		hj = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/jh.png"));
		hq = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/qh.png"));
		hk = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/kh.png"));
		d9 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/9d.png"));
		d10 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/10d.png"));
		da = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/ad.png"));
		dj = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/jd.png"));
		dq = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/qd.png"));
		dk = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/kd.png"));
		s9 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/9s.png"));
		s10 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/10s.png"));
		sa = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/as.png"));
		sj = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/js.png"));
		sq = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/qs.png"));
		sk = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/ks.png"));
		c9 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/9c.png"));
		c10 = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/10c.png"));
		ca = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/ac.png"));
		cj = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/jc.png"));
		cq = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/qc.png"));
		ck = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/kc.png"));
		back = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back.png"));
		sidewaysBack = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/back_sideways.png"));
		empty = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty.png"));
		sidewaysEmpty = new javax.swing.ImageIcon(getClass().getResource("/euchre/gui/pictures/empty_sideways.png"));
				
	}
	
	/**
	 * Given the suite of the card and its value this method will return the corresponding picture. 
	 * 
	 * suit:
	 * h - hearts
	 * d - diamonds
	 * s - spades
	 * c - clubs
	 * b - back-side of a card (not a literal suit)
	 * e - empty card place-holder (not a literal suite, if picked value does not matter)
	 * value:
	 * 9 - 9
	 * 0 - 10
	 * j - jack
	 * q - queen
	 * k - king
	 * a - ace
	 * s - sideways (for use w/ b-suit, if the back-side card is not sideways pass anything but this value)
	 * 
	 * @param suit The suit to find the picture of.
	 * @param value The value to find the picture of.
	 * @return The picture of the card, null if no match found.
	 */
	public ImageIcon getPicture(char suit, char value){
		if (suit == 'e'){
			if (value == 's'){
				return sidewaysEmpty;
			}else{
				return empty;
			}
		}else if (suit == 'b'){
			if (value == 's'){
				return sidewaysBack;
			}else{
				return back;
			}
		}else if (suit == 'h'){
			switch (value) {
			case '9':
				return h9;
			case '0':
				return h10;
			case 'a':
				return ha;
			case 'j':
				return hj;
			case 'q':
				return hq;
			case 'k':
				return hk;
			default:
				break;
			}
		}else if (suit == 'd'){
			switch (value) {
			case '9':
				return d9;
			case '0':
				return d10;
			case 'a':
				return da;
			case 'j':
				return dj;
			case 'q':
				return dq;
			case 'k':
				return dk;
			default:
				break;
			}
		}else if (suit == 's'){
			switch (value) {
			case '9':
				return s9;
			case '0':
				return s10;
			case 'a':
				return sa;
			case 'j':
				return sj;
			case 'q':
				return sq;
			case 'k':
				return sk;
			default:
				break;
			}
		}else if (suit == 'c'){
			switch (value) {
			case '9':
				return c9;
			case '0':
				return c10;
			case 'a':
				return ca;
			case 'j':
				return cj;
			case 'q':
				return cq;
			case 'k':
				return ck;
			default:
				break;
			}
		}
		return null;
	}
}
