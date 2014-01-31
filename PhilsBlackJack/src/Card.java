
import java.util.*;

public class Card {

    public final static int SPADES = 0, // Codes for the 4 suits.
                            HEARTS = 1,
                            DIAMONDS = 2,
                            CLUBS = 3;
    private int value;				// from 1 to 13 (Ace to King)
    private int suit;
    // the 4 suits from 0 to 3

    // constructor
    Card(int value, int suit) {
        this.value = value;
        this.suit = suit;
    }

    public String getSuitAsString() {

        switch (suit) {
            case SPADES:
                return "Spades";
            case HEARTS:
                return "Hearts";
            case DIAMONDS:
                return "Diamonds";
            case CLUBS:
                return "Clubs";
            default:
                return "??";
        }
    }

    public String getValueAsString() {

        switch (value) {
            case 1:
                return "A";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "10";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return "??";
        }
    }

    // accessors
    int getSuit() {
        return suit;
    }

    int getValue() {
        return value;
    }

    // returns the card definition
    public String toString() {
        return "Card Value: " + value + " Suit: " + suit;
    }
}
