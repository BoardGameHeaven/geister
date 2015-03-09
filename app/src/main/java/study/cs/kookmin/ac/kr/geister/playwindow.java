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
        {//누르고 있을때 해당하는 진영의 착한, 나쁜 유령을 확인할수 있음
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

    public void setBoard() {//게임판에 말들의 위치를 선정해준다.
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
        else if (state == 2) {//white 턴에 이동할 말을 선택
            if(board[num] instanceof White) {
                int count = checkCoordinate(num);
                if(checkCountWhite(count))
                    return;
                chooseWhiteTern(count);
                from = num;
                state ++;
            }
        }
        else if (state == 3) {//이동하는 위치를 선택
            if(checkTo(num)) {
                if(moveWhite(from, num)) {
                    piecesSet();
                    checkVictory();
                    state++;
                    turnWhite.setVisibility(View.INVISIBLE);
                    turnBlack.setVisibility(View.VISIBLE);
                }
            }
            else {
                state--;
                piecesSet();
                return;
            }
        }
        else if (state == 4) {//black턴에 이동할 말을 선택
            if(board[num] instanceof Black) {
                int count = checkCoordinate(num);
                if(checkCountBlack(count))
                    return;
                chooseBlackTern(count);
                from = num;
                state ++;
            }
        }
        else if (state == 5) {//이동하는 위치를 선택
            if(checkTo(num)) {
                if(moveBlack(from, num)) {
                    piecesSet();
                    checkVictory();
                    state = 2;
                    turnBlack.setVisibility(View.INVISIBLE);
                    turnWhite.setVisibility(View.VISIBLE);
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
    int checkCoordinate (int a) {// moveboard배열과 moveCoordinate배열에 이동할 수 있는 게임판 주소와 위치값을 각각 저장
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


    boolean checkCountWhite (int count) {//count수 만큼 moveBoard의 값들을 확인한후 만약 이동할 공간이 없는 경우 true아닌경우 false를 return한다.
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
    boolean checkCountBlack (int count) {//count수 만큼 moveBoard의 값들을 확인한후 만약 이동할 공간이 없는 경우 true아닌경우 false를 return한다.
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

    public void chooseWhiteTern(int count) {//white차례일때 이동할수 있는 공간에 황금 테두리를 씌운다.
        for(int i=0; i<count; i++) {
            if(moveBoard[i] != null && board[moveCoordinate[i]] instanceof Black)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose_black);
            else if(moveBoard[i] == null)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose);
        }
    }

    public void chooseBlackTern(int count) {//black차례일때 이동할수 있는 공간에 황금 테두리를 씌운다.
        for(int i=0; i<count; i++) {
            if(moveBoard[i] != null && board[moveCoordinate[i]] instanceof White)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose_white);
            else if(moveBoard[i] == null)
                imgBoard[moveCoordinate[i]].setImageResource(R.drawable.choose);
        }
    }

    public boolean checkTo(int to) {//해당 턴 말이 이동하려는 곳의 말과 같지 않은 경우 true를 리턴한다.
        for (int i=0; i<4; i++) {
            if(moveCoordinate[i] == to) {//만약 white인 경우 이동하려는 곳이 moveCoordinate배열에 들어있고 그 위치에 White가 아닌경우 true를 리턴
                if(state == 3 && !(board[to] instanceof White))
                    return true;
                else if(state == 5 && !(board[to] instanceof Black))//만약 black인 경우 이동하려는 곳이 moveCoordinate배열에 들어있고 그 위치에 black이 아닌경우 true를 리턴
                    return true;
            }
        }
        return false;
    }

    public boolean moveWhite(int from, int to) {//white턴에 white로 이동하려고 하면 false를 리턴한다. 아닌경우 board를 바꾼후 true를 리턴한다.
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
    public boolean moveBlack(int from, int to) {//black턴에 black으로 이동하려고 하면 false를 리턴한다. 아닌경우 board를 바꾼후 true를 리턴한다.
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



    public int getNum(ImageView image) {//image와 id가 동일한 imgBoard 위치 값을 리턴한다.
        for(int i=0; i<36; i++) {
            if(imgBoard[i].getId() == image.getId())
                return i;
        }
        return -1;
    }

    public void piecesSet() {//화면에 board값에 따라 말 그림과 빈칸 그림을 입력해준다.
        for (int i=0; i<36; i++) {
            if(board[i] instanceof White)
                imgBoard[i].setImageResource(R.drawable.ghost_white);
            else if(board[i] instanceof Black)
                imgBoard[i].setImageResource(R.drawable.ghost_black);
            else
                imgBoard[i].setImageResource(R.drawable.blank);

        }
    }

    public void whitePiecesSet() {//흰말의 착한, 나쁜 말을 나타내준다.
        for (int i=0; i<36; i++) {
            if(board[i] instanceof White && board[i].getGood())
                imgBoard[i].setImageResource(R.drawable.ghost_blue);
            else if(board[i] instanceof White && board[i].getBed())
                imgBoard[i].setImageResource(R.drawable.ghost_red);
        }
    }

    public void blackPiecesSet() {//검은 말의 찬한, 나쁜 말을 나타내준다.
        for (int i=0; i<36; i++) {
            if(board[i] instanceof Black && board[i].getGood())
                imgBoard[i].setImageResource(R.drawable.ghost_black_blue);
            else if(board[i] instanceof Black && board[i].getBed())
                imgBoard[i].setImageResource(R.drawable.ghost_black_red);
        }
    }

    public void checkVictory() {//승리한 조건이 만족한 경우 그에따른 말을 출력해준다.
        if (whiteBed == 0 | blackGood == 0 | (board[0] instanceof White && board[0].getGood() && state == 5) | (board[5] instanceof White && board[5].getGood() && state == 5)) {
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
        else if (whiteGood == 0 | blackBed == 0 | (board[30] instanceof Black && board[30].getGood() && state == 3) | (board[35] instanceof Black && board[35].getGood() && state == 3)) {
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

    public void setUpdateCount() {//말의 숫자들을 계속 바꿔준다.
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
