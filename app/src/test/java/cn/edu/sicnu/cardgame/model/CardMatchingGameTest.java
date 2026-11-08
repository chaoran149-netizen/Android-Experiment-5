package cn.edu.sicnu.cardgame.model;
import org.junit.Test;import java.util.*;import static org.junit.Assert.*;
public class CardMatchingGameTest{
 private CardMatchingGame game(Card... c){return new CardMatchingGame(Arrays.asList(c));}
 @Test public void rankTriple(){CardMatchingGame g=game(new Card(0,0),new Card(1,0),new Card(2,0));g.chooseCardAtIndex(0);g.chooseCardAtIndex(1);assertEquals(-2,g.getScore());g.chooseCardAtIndex(2);assertEquals(21,g.getScore());assertTrue(g.isComplete());g.chooseCardAtIndex(0);assertEquals(21,g.getScore());}
 @Test public void suitTriple(){CardMatchingGame g=game(new Card(0,0),new Card(0,1),new Card(0,2));g.chooseCardAtIndex(0);g.chooseCardAtIndex(1);g.chooseCardAtIndex(2);assertEquals(9,g.getScore());}
 @Test public void mismatch(){CardMatchingGame g=game(new Card(0,0),new Card(1,1),new Card(2,2));for(int i=0;i<3;i++)g.chooseCardAtIndex(i);assertEquals(-5,g.getScore());for(int i=0;i<3;i++)assertFalse(g.cardAtIndex(i).isChosen());assertFalse(g.hasPossibleMatch());}
 @Test public void closingDoesNotCharge(){CardMatchingGame g=new CardMatchingGame(24);g.chooseCardAtIndex(0);g.chooseCardAtIndex(0);assertEquals(-1,g.getScore());}
 @Test public void resetClearsAllState(){CardMatchingGame g=new CardMatchingGame(24);g.chooseCardAtIndex(0);g.reset();assertEquals(0,g.getScore());assertEquals(24,g.size());for(int i=0;i<24;i++)assertFalse(g.cardAtIndex(i).isChosen());g.reset(30);assertEquals(30,g.size());}
 @Test public void deckUnique(){Deck d=new Deck();Set<Integer> ids=new HashSet<>();for(int i=0;i<52;i++){Card c=d.drawRandomCard();assertTrue(ids.add(c.getSuit()*13+c.getRank()));}assertNull(d.drawRandomCard());}
}
