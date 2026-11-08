package cn.edu.sicnu.cardgame;
import androidx.test.core.app.ActivityScenario;import androidx.recyclerview.widget.RecyclerView;import org.junit.Test;import static org.junit.Assert.*;
public class AppUiTest {
 @Test public void recyclerUsesCorrectCountAndSurvivesRecreation(){try(ActivityScenario<GameActivity>s=ActivityScenario.launch(GameActivity.class)){s.onActivity(a->{RecyclerView v=a.findViewById(R.id.card_grid);assertEquals(a.getResources().getInteger(R.integer.card_count),v.getAdapter().getItemCount());v.findViewHolderForAdapterPosition(0).itemView.performClick();assertEquals(-1,a.getGame().getScore());});s.recreate();s.onActivity(a->assertEquals(-1,a.getGame().getScore()));}}
 @Test public void aboutMenuOpens(){try(ActivityScenario<MainActivity>s=ActivityScenario.launch(MainActivity.class)){androidx.test.espresso.Espresso.onView(androidx.test.espresso.matcher.ViewMatchers.withText(R.string.about)).perform(androidx.test.espresso.action.ViewActions.click());androidx.test.espresso.Espresso.onView(androidx.test.espresso.matcher.ViewMatchers.withText(R.string.about_message)).check(androidx.test.espresso.assertion.ViewAssertions.matches(androidx.test.espresso.matcher.ViewMatchers.isDisplayed()));}}
 @Test public void captureBothOrientations() throws Exception {
 try(ActivityScenario<GameActivity>s=ActivityScenario.launch(GameActivity.class)){
  androidx.test.espresso.Espresso.onView(androidx.test.espresso.matcher.ViewMatchers.withId(R.id.card_grid)).check(androidx.test.espresso.assertion.ViewAssertions.matches(androidx.test.espresso.matcher.ViewMatchers.isDisplayed()));
  capture(1);
  s.onActivity(a->a.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE));
  boolean[] ready={false};for(int i=0;i<50;i++){s.onActivity(a->ready[0]=a.getResources().getConfiguration().orientation==2);if(ready[0])break;Thread.sleep(100);}
  assertTrue(ready[0]);s.onActivity(a->assertEquals(30,a.getGame().size()));capture(2);
  s.onActivity(a->a.setRequestedOrientation(android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT));
 }
 }
 private void capture(int n)throws Exception{
  android.app.Instrumentation i=androidx.test.platform.app.InstrumentationRegistry.getInstrumentation();i.waitForIdleSync();i.getUiAutomation().waitForIdle(300,5000);
  android.graphics.Bitmap b=i.getUiAutomation().takeScreenshot();java.io.File f=new java.io.File(i.getTargetContext().getExternalFilesDir(null),Integer.toString(n));try(java.io.FileOutputStream o=new java.io.FileOutputStream(f)){b.compress(android.graphics.Bitmap.CompressFormat.PNG,100,o);}
 }
}