package study.cs.kookmin.ac.kr.geister;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class playwindow extends ActionBarActivity {

    ImageView[]  imgBoard = new ImageView[36];
    Board[] board = new Board[36];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playwindow);


        int tmpID;

        for(int i=0; i<10; i++) {
            tmpID = getResources().getIdentifier("s" + i, "id", "playwindow");
            imgBoard[i] = (ImageView) findViewById(tmpID);
        }
    }
    public void onClickBoard(View v){
        ImageView image = (ImageView) v;
        image.setImageResource(R.drawable.ghost_black);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_playwindow, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
