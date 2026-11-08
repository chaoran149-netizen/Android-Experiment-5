package cn.edu.sicnu.cardgame;
import android.view.*;
import android.widget.Button;
import androidx.recyclerview.widget.RecyclerView;
import cn.edu.sicnu.cardgame.model.*;
public final class CardAdapter extends RecyclerView.Adapter<CardAdapter.Holder>{
 public interface Listener{void onCardClick(int position);}
 private final CardMatchingGame game;private final Listener listener;
 public CardAdapter(CardMatchingGame game,Listener listener){this.game=game;this.listener=listener;setHasStableIds(true);}
 public long getItemId(int p){return p;}
 public int getItemCount(){return game.size();}
 public Holder onCreateViewHolder(ViewGroup parent,int type){
  Button b=new Button(parent.getContext());b.setAllCaps(false);b.setPadding(0,0,0,0);b.setMinWidth(0);b.setMinimumWidth(0);b.setBackgroundTintList(null);
  RecyclerView.LayoutParams p=new RecyclerView.LayoutParams(-1,(int)(76*parent.getResources().getDisplayMetrics().density));p.setMargins(4,4,4,4);b.setLayoutParams(p);return new Holder(b);
 }
 public void onBindViewHolder(Holder h,int position){
  Card c=game.cardAtIndex(position);Button b=h.button;android.content.Context context=b.getContext();
  String face=context.getString(R.string.card_face,context.getResources().getStringArray(R.array.card_suits)[c.getSuit()],context.getResources().getStringArray(R.array.card_ranks)[c.getRank()]);
  boolean open=c.isChosen()||c.isMatched();b.setText(open?face:context.getString(R.string.card_back));
  b.setTextColor(context.getColor(open?(c.getSuit()<2?R.color.red:R.color.ink):R.color.gold));
  b.setBackgroundResource(c.isMatched()?R.drawable.card_matched:open?R.drawable.card_face:R.drawable.card_back);
  b.setEnabled(!c.isMatched()&&game.hasPossibleMatch());b.setAlpha(c.isMatched()?0.65f:1f);
  b.setContentDescription(open?context.getString(c.isMatched()?R.string.card_matched_description:R.string.card_open_description,position+1,face):context.getString(R.string.card_hidden_description,position+1));
  b.setOnClickListener(v->{int p=h.getBindingAdapterPosition();if(p!=RecyclerView.NO_POSITION)listener.onCardClick(p);});
 }
 public static final class Holder extends RecyclerView.ViewHolder{final Button button;Holder(Button b){super(b);button=b;}}
}
