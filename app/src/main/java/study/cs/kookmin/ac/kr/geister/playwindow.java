package study.cs.kookmin.ac.kr.geister;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class playwindow extends ActionBarActivity {

    ImageView[]  imgBoard = new ImageView[36];

    View turnWhite;
    View turnBlack;

    Pieces[] board = new Pieces[36];
    Pieces [] moveBoard = new Pieces[4];
    int [] moveCoordinate = new int[4];

    View[] view = new View[36];

    int state = 0;//from 0 ~ to 5
    int chooseCount = 0;

    int whiteGood =0;
    int whiteBed =8;

    int blackGood =0;
    int blackBed =8;

    int from;



    private Button.OnTouchListener mTouchevent = new Button.OnTouchListener()
    {
        public boolean onTouch(View v, MotionEvent event)
        {
            int action=event.getAction();

            if(action==MotionEvent.ACTION_DOWN)
            {
                if(v.getId() == R.id.white_button)
                    whitePiecesSet();
                else if(v.getId() == R.id.black_button)
                    blackPiecesSet();
            }
            else if(action==MotionEvent.ACTION_UP){
                piecesSet();
            }
            return true;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playwindow);

        turnWhite = findViewById(R.id.white_button);
        turnBlack = findViewById(R.id.black_button);
        turnBlack.setVisibility(View.INVISIBLE);
        turnWhite.setVisibility(View.INVISIBLE);

        turnWhite.setOnTouchListener(mTouchevent);
        turnBlack.setOnTouchListener(mTouchevent);
        int tmpID;

        setBoard();
        for(int i=0; i<36; i++) {
            imgBoard[i] = (ImageView) findViewById(R.id.s0 + i);
        }
        piecesSet();
    }

    public void setBoard() {
        for (int i=1; i<5; i++) {
            for (int j=0; j<2; j++) {
                board[i+6*j] = new Black();
            }
            for (int k=4; k<6; k++) {
                board[i+6*k] = new White();
            }
        }
    }
    public void onClickBoard(View v){
        ImageView image = (ImageView) v;
        int num = getNum(image);

        if (state == 0) {//white select good
            whitePiecesSet();
            if(board[num] instanceof White && board[num].getBed()) {
                board[num].setGood();
                whitePiecesSet();
                whiteBed--;
                whiteGood++;
            }
            if (whiteGood == 4) {
                piecesSet();
                state++;
            }
        }
        else if (state == 1) {//black select good
            blackPiecesSet();
            if(board[num] instanceof Black && board[num].getBed()) {
                board[num].setGood();
                blackPiecesSet();
                blackBed--;
                blackGood++;
            }
            if (blackGood == 4) {
                piecesSet();
                turnWhite.setVisibility(View.VISIBLE);
                state++;
            }
        }
        else if (state == 2) {
            if(board[num] instanceof White) {
                int count = checkCoordinate(num);
                if(checkCountWhite(count))
                    return;
                chooseWhiteTern(count);
                from = num;
                state ++;
            }
        }
        else if (state == 3) {
            if(checkTo(num)) {
                if(moveWhite(from, num)) {
                    piecesSet();
                    state++;
                    turnWhite.setVisibility(View.INVISIBLE);
                    turnBlack.setVisibility(View.VISIBLE);
                    checkVictory();
                }
            }
            else {
                state--;
                piecesSet();
                return;
            }
        }
        else if (state == 4) {
            if(board[num] instanceof Black) {
                int count = checkCoordinate(num);
                if(checkCountBlack(count))
                    return;
                chooseBlackTern(count);
                from = num;
                state ++;
            }
        }
        else if (state == 5) {
            if(checkTo(num)) {
                if(moveBlack(from, num)) {
                    piecesSet();
                    state = 2;
                    turnBlack.setVisibility(View.INVISIBLE);
                    turnWhite.setVisibility(View.VISIBLE);
                    checkVictory();
                }
            }
            else {
                state--;
                piecesSet();
                return;
            }
        }
        setUpdateCount();
    }
    int checkCoordinate (int a) {// a위치를 b위치로 이동한다라는 방식
        if(a % 6 == 0) {
            if (a == 0){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a+6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a+6;
                return 2;
            }
            else if (a == 30){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                return 2;
            }
            else {
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a+6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a+6;
                return 3;
            }
        }
        if(a % 6 == 1) {

            if (a == 1){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a+6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a+6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else if (a == 31){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else {
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveBoard[3] = board[a+6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                moveCoordinate[3] = a+6;
                return 4;
            }
        }
        if(a % 6 == 2) {

            if (a == 2){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a+6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a+6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else if (a == 32){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else {
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveBoard[3] = board[a+6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                moveCoordinate[3] = a+6;
                return 4;
            }
        }
        if(a % 6 == 3) {
            if (a == 3){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a+6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a+6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else if (a == 33){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else {
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveBoard[3] = board[a+6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                moveCoordinate[3] = a+6;
                return 4;
            }
        }
        if(a % 6 == 4) {
            if (a == 4){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a+6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a+6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else if (a == 34){
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                return 3;
            }
            else {
                moveBoard[0] = board[a+1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a-1];
                moveBoard[3] = board[a+6];
                moveCoordinate[0] = a+1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a-1;
                moveCoordinate[3] = a+6;
                return 4;
            }
        }
        if(a % 6 == 5) {
            if (a == 5){
                moveBoard[0] = board[a-1];
                moveBoard[1] = board[a+6];
                moveCoordinate[0] = a-1;
                moveCoordinate[1] = a+6;
                return 2;
            }
            else if (a == 35){
                moveBoard[0] = board[a-1];
                moveBoard[1] = board[a-6];
                moveCoordinate[0] = a-1;
                moveCoordinate[1] = a-6;
                return 2;
            }
            else {
                moveBoard[0] = board[a-1];
                moveBoard[1] = board[a-6];
                moveBoard[2] = board[a+6];
                moveCoordinate[0] = a-1;
                moveCoordinate[1] = a-6;
                moveCoordinate[2] = a+6;
                return 3;
            }
        }
        return -1;
    }


    boolean checkCountWhite (int count) {
        int num =0;
        for (int i=0; i<count; i++) {
            if(moveBoard[i] instanceof White)
                num++;
        }
        if(num == count)
            return true;
        else
            return false;
    }
    boolean checkCountBlack (int count) {
        int num =0;
        for (int i=0; i<count; i++) {
            if(moveBoard[i] instanceof Black)
                num++;
        }
        if(num == count)
            return true;
        else
            return false;
    }

    public void chooseWhiteTern(int count) {
        for(int i=0; i<count; i++) {
            if(moveBoard[i] != null && board[moveCoordinate[i]] instanceof Black)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose_black);
            else if(moveBoard[i] == null)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose);
        }
    }

    public void chooseBlackTern(int count) {
        for(int i=0; i<count; i++) {
            if(moveBoard[i] != null && board[moveCoordinate[i]] instanceof White)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose_white);
            else if(moveBoard[i] == null)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose);
        }
    }

    public boolean checkTo(int to) {
        for (int i=0; i<4; i++) {
            if(moveCoordinate[i] == to) {
                if(state == 3 && !(board[to] instanceof White))
                    return true;
                else if(state == 5 && !(board[to] instanceof Black))
                    return true;
            }
        }
        return false;
    }

    public boolean moveWhite(int from, int to) {
        if (board[to] instanceof White)
            return false;

        if (board[to] != null ) {
            if(board[to].getGood())
                blackGood--;
            else if(board[to].getBed())
                blackBed--;
        }

        board[to] = board[from];
        board[from] = null;
        return true;
    }
    public boolean moveBlack(int from, int to) {
        if (board[to] instanceof Black)
            return false;

        if (board[to] != null ) {
            if(board[to].getGood())
                whiteGood--;
            else if(board[to].getBed())
                whiteBed--;
        }

        board[to] = board[from];
        board[from] = null;
        return true;
    }



    public int getNum(ImageView image) {
        for(int i=0; i<36; i++) {
            if(imgBoard[i].getId() == image.getId())
                return i;
        }
        return -1;
    }

    public void piecesSet() {
        for (int i=0; i<36; i++) {
            if(board[i] instanceof White)
                imgBoard[i].setImageResource(R.drawable.ghost_white);
            else if(board[i] instanceof Black)
                imgBoard[i].setImageResource(R.drawable.ghost_black);
            else
                imgBoard[i].setImageResource(R.drawable.blank);

        }
    }

    public void whitePiecesSet() {
        for (int i=0; i<36; i++) {
            if(board[i] instanceof White && board[i].getGood())
                imgBoard[i].setImageResource(R.drawable.ghost_blue);
            else if(board[i] instanceof White && board[i].getBed())
                imgBoard[i].setImageResource(R.drawable.ghost_red);
        }
    }

    public void blackPiecesSet() {
        for (int i=0; i<36; i++) {
            if(board[i] instanceof Black && board[i].getGood())
                imgBoard[i].setImageResource(R.drawable.ghost_black_blue);
            else if(board[i] instanceof Black && board[i].getBed())
                imgBoard[i].setImageResource(R.drawable.ghost_black_red);
        }
    }

    public void checkVictory() {
        if (whiteBed == 0 | blackGood == 0 | (board[0] instanceof White && board[0].getGood()) | (board[5] instanceof White && board[5].getGood())) {
            new AlertDialog.Builder(this)
                    .setTitle("종료")
                    .setMessage("White Victory")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    })
                    .show();
        }
        else if (whiteGood == 0 | blackBed == 0 | (board[30] instanceof Black && board[30].getGood()) | (board[35] instanceof White && board[35].getGood())) {
            new AlertDialog.Builder(this)
                    .setTitle("종료")
                    .setMessage("Black Victory")
                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            finish();
                        }
                    })
            .show();
        }
    }

    public void setUpdateCount() {
        TextView tvText0 = (TextView) findViewById(R.id.blackRedCount);
        TextView tvText1 = (TextView) findViewById(R.id.blackBlueCount);
        TextView tvText2 = (TextView) findViewById(R.id.whiteRedCount);
        TextView tvText3 = (TextView) findViewById(R.id.whiteBlueCount);

        tvText0.setText(""+blackBed);
        tvText1.setText(""+blackGood);
        tvText2.setText(""+whiteBed);
        tvText3.setText(""+whiteGood);
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
