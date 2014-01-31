
/*
    An object of type Hand represents a vHand of cards.  The maximum number of
    cards in the vHand can be specified in the constructor, but by default
    is 5.  A utility function is provided for computing the value of the
    vHand in the game of Blackjack.
*/

import java.util.Vector;

public class Hand {

   private Vector vHand;   // The cards in the vHand.

   public Hand() {
           // Create a Hand object that is initially empty.
      vHand = new Vector();
   }

   public void clear() {
         // Discard all the cards from the vHand.
      vHand.removeAllElements();
   }

   public void addCard(Card c) {
         // Add the card c to the vHand.  c should be non-null.  (If c is
         // null, nothing is added to the vHand.)
      if (c != null)
         vHand.addElement(c);
   }

   public void removeCard(Card c) {
         // If the specified card is in the vHand, it is removed.
      vHand.removeElement(c);
   }

   public void removeCard(int position) {
         // If the specified position is a valid position in the vHand,
         // then the card in that position is removed.
      if (position >= 0 && position < vHand.size())
         vHand.removeElementAt(position);
   }

   public int getCardCount() {
         // Return the number of cards in the vHand.
      return vHand.size();
   }

   public Card getCard(int position) {
      if (position >= 0 && position < vHand.size())
         return (Card)vHand.elementAt(position);
      else
         return null;
   }

   public void sortBySuit() {
         // Sorts the cards in the vHand so that cards of the same suit are
         // grouped together, and within a suit the cards are sorted by value.
         // Note that aces are considered to have the lowest value, 1.
      Vector vNewHand = new Vector();
      while (vHand.size() > 0) {
         int pos = 0;  // Position of minimal card.
         Card c = (Card)vHand.elementAt(0);  // Minumal card.
         for (int i = 1; i < vHand.size(); i++) {
            Card c1 = (Card)vHand.elementAt(i);
            if ( c1.getSuit() < c.getSuit() ||
                    (c1.getSuit() == c.getSuit() && c1.getValue() < c.getValue()) ) {
                pos = i;
                c = c1;
            }
         }
         vHand.removeElementAt(pos);
         vNewHand.addElement(c);
      }
      vHand = vNewHand;
   }

   public void sortByValue() {
         // Sorts the cards in the vHand so that cards of the same value are
         // grouped together.  Cards with the same value are sorted by suit.
         // Note that aces are considered to have the lowest value, 1.
      Vector vNewHand = new Vector();
      while (vHand.size() > 0) {
         int pos = 0;  // Position of minimal card.
         Card c = (Card)vHand.elementAt(0);  // Minumal card.
         for (int i = 1; i < vHand.size(); i++) {
            Card c1 = (Card)vHand.elementAt(i);
            if ( c1.getValue() < c.getValue() ||
                    (c1.getValue() == c.getValue() && c1.getSuit() < c.getSuit()) ) {
                pos = i;
                c = c1;
            }
         }
         vHand.removeElementAt(pos);
         vNewHand.addElement(c);
      }
      vHand = vNewHand;
   }

}