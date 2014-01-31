
/*
   A subclass of the Hand class that represents a hand of nCardCount
   in the game of Blackjack.  To the methods inherited form Hand,
   it adds the method getBlackjackHand(), which returns the value
   of the hand for the game of Blackjack.
*/

public class BlackjackHand extends Hand {

     public int getBlackjackValue() {
            // Returns the value of this hand for the
            // game of Blackjack.

         int nVal;      // The value computed for the hand.
         boolean isAce;  // This will be set to true if the
                       //   hand contains an isAce.
         int nCardCount;    // Number of nCardCount in the hand.

         nVal = 0;
         isAce = false;
         nCardCount = getCardCount();

         for ( int i = 0;  i < nCardCount;  i++ ) {
                 // Add the value of the i-th card in the hand.
             Card card;    // The i-th card;
             int nCardVal;  // The blackjack value of the i-th card.
             card = getCard(i);
             nCardVal = card.getValue();  // The normal value, 1 to 13.
             if (nCardVal > 10) {
                 nCardVal = 10;   // For a Jack, Queen, or King.
             }
             if (nCardVal == 1) {
                 isAce = true;     // There is at least one isAce.
             }
             nVal = nVal + nCardVal;
          }

          if ( isAce == true  &&  nVal + 10 <= 21 )
              nVal = nVal + 10;

          return nVal;

     }  // end getBlackjackValue()

} // end class BlackjackHand
