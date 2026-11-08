package cn.edu.sicnu.cardgame.model;
import java.io.Serializable;
import java.util.*;
public final class CardMatchingGame implements Serializable {
 private static final long serialVersionUID=2L;
 public enum Turn {IGNORED,CLOSED,CHOSEN,RANK_MATCH,SUIT_MATCH,MISMATCH}
 private final List<Card> cards=new ArrayList<>();
 private int score;
 public CardMatchingGame(int count){reset(count);}
 public CardMatchingGame(List<Card> initial){cards.addAll(initial);}
 public void reset(){reset(cards.size());}
 public void reset(int count){if(count<3||count>52)throw new IllegalArgumentException();cards.clear();Deck deck=new Deck();for(int i=0;i<count;i++)cards.add(deck.drawRandomCard());score=0;}
 public int size(){return cards.size();}
 public int getScore(){return score;}
 public Card cardAtIndex(int i){return cards.get(i);}
 public Turn chooseCardAtIndex(int index){
  Card card=cards.get(index);
  if(card.isMatched())return Turn.IGNORED;
  if(card.isChosen()){card.setChosen(false);return Turn.CLOSED;}
  card.setChosen(true);score--;
  List<Card> chosen=new ArrayList<>();for(Card c:cards)if(c.isChosen()&&!c.isMatched())chosen.add(c);
  if(chosen.size()<3)return Turn.CHOSEN;
  int value=chosen.get(2).match(new Card[]{chosen.get(0),chosen.get(1)});
  if(value>0){score+=value*4;for(Card c:chosen)c.setMatched(true);return value==6?Turn.RANK_MATCH:Turn.SUIT_MATCH;}
  score-=2;for(Card c:chosen)c.setChosen(false);return Turn.MISMATCH;
 }
 public int matchedCount(){int n=0;for(Card c:cards)if(c.isMatched())n++;return n;}
 public boolean isComplete(){return matchedCount()==size();}
 public boolean hasPossibleMatch(){for(int i=0;i<size();i++)for(int j=i+1;j<size();j++)for(int k=j+1;k<size();k++)if(!cards.get(i).isMatched()&&!cards.get(j).isMatched()&&!cards.get(k).isMatched()&&cards.get(k).match(new Card[]{cards.get(i),cards.get(j)})>0)return true;return false;}
}
