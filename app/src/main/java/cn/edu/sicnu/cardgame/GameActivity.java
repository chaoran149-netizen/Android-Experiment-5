package cn.edu.sicnu.cardgame;
import android.app.*;import android.os.Bundle;import android.widget.TextView;
import androidx.recyclerview.widget.*;
import cn.edu.sicnu.cardgame.model.*;
public class GameActivity extends Activity{
 private CardMatchingGame game;private CardAdapter adapter;private int message=R.string.game_ready;
 protected void onCreate(Bundle state){super.onCreate(state);setContentView(R.layout.activity_game);
 int count=getResources().getInteger(R.integer.card_count);
 game=state==null?null:(CardMatchingGame)state.getSerializable(getString(R.string.key_game));
 if(game==null)game=new CardMatchingGame(count);else if(game.size()!=count)game.reset(count);
 if(state!=null)message=state.getInt(getString(R.string.key_message),R.string.game_ready);
 RecyclerView list=findViewById(R.id.card_grid);list.setLayoutManager(new GridLayoutManager(this,getResources().getInteger(R.integer.columns)));
 adapter=new CardAdapter(game,this::choose);list.setAdapter(adapter);list.setItemAnimator(null);
 findViewById(R.id.reset_game).setOnClickListener(v->new AlertDialog.Builder(this).setTitle(R.string.restart_title).setMessage(R.string.restart_message).setNegativeButton(R.string.cancel,null).setPositiveButton(R.string.restart_confirm,(d,w)->{game.reset();message=R.string.game_ready;render();}).show());
 findViewById(R.id.exit_game).setOnClickListener(v->finish());render();}
 private void choose(int p){CardMatchingGame.Turn t=game.chooseCardAtIndex(p);message=t==CardMatchingGame.Turn.MISMATCH?R.string.triple_fail:t==CardMatchingGame.Turn.RANK_MATCH||t==CardMatchingGame.Turn.SUIT_MATCH?R.string.triple_match:t==CardMatchingGame.Turn.CLOSED?R.string.game_closed:R.string.triple_pending;
 ((CardGameApplication)getApplication()).getProfile().recordScore(game.getScore());render();}
 private void render(){adapter.notifyDataSetChanged();((TextView)findViewById(R.id.score)).setText(getString(R.string.score,game.getScore()));((TextView)findViewById(R.id.progress)).setText(getString(R.string.progress,game.matchedCount(),game.size()));((TextView)findViewById(R.id.game_status)).setText(game.isComplete()?R.string.game_complete:!game.hasPossibleMatch()?R.string.game_no_pairs:message);}
 CardMatchingGame getGame(){return game;}
 protected void onSaveInstanceState(Bundle out){out.putSerializable(getString(R.string.key_game),game);out.putInt(getString(R.string.key_message),message);super.onSaveInstanceState(out);}
}
