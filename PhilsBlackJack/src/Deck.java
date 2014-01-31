
/*
    An object of type Deck represents an ordinary deck of 52 playing cards.
    The deck can be shuffled, and cards can be dealt from the deck.
*/

public class Deck {

    private Card[] deck;   // An array of 52 Cards, representing the deck.
    private int nCardsUsed; // How many cards have been dealt from the deck.

    public Deck() {
           // Create an unshuffled deck of cards.
       deck = new Card[52];
       int cardCt = 0; // How many cards have been created so far.
       for ( int nSuit = 0; nSuit <= 3; nSuit++ ) {
          for ( int nValue = 1; nValue <= 13; nValue++ ) {
             deck[cardCt] = new Card(nValue,nSuit);
             cardCt++;
          }
       }
       nCardsUsed = 0;
    }

    public void shuffle() {
          
        for ( int i = 51; i > 0; i-- ) {
            int nRand = (int)(Math.random()*(i+1));
            Card tempCard = deck[i];
            deck[i] = deck[nRand];
            deck[nRand] = tempCard;
        }
        nCardsUsed = 0;
    }

    public int cardsLeft() {
        return 52 - nCardsUsed;
    }

    public Card dealCard() {
         
        if (nCardsUsed == 52)
           shuffle();
        nCardsUsed++;
        return deck[nCardsUsed - 1];
    }

} // end class Deck