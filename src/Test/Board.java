package Test;

public class Board {
	Pieces [] board = new Pieces[36];
	
	White white = new White();
	Black black = new Black();
	
	Pieces [] moveBoard = new Pieces[4];
	int [] moveCoordinate = new int[4];
	
	int blackGood = 0;
	int whiteGood = 0;
	int blackBed = 4;
	int whiteBed = 4;
	
	boolean blackWin = false;
	boolean whiteWin = false;
	
	int turn = 0; //0일때 white 1일때 black
	
	public Board() {
		// TODO Auto-generated constructor stub
		for (int i=1; i<5; i++) {
			for (int j=0; j<2; j++) {
				board[i+6*j] = new Black();
			}
			for (int k=4; k<6; k++) {
				board[i+6*k] = new White();
			}
		}		
		
	}
	//말 움직이는 파트
	void movePiece(int a) { 
		if (turn == 0) {
			if (!board[a].equals(white)) //흰턴일때 선택이 흰놈이 아니면 걍 나감
				return;
			int count = checkCoordinate(a);	//갈수있는 장소 갯수를 나타내준다. 아직 white black 구분 안함
			checkSpaces(count);
			chooseSpace(a);
		}
		else {
			if (!board[a].equals(black)) //흰턴일때 선택이 흰놈이 아니면 걍 나감
				return;
			int count = checkCoordinate(a);
			checkSpaces(count);
			chooseSpace(a);
		}
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
				moveCoordinate[1] = a+6;
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
				moveCoordinate[1] = a-6;
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
				moveCoordinate[1] = a+6;
				moveCoordinate[2] = a+6;
				return 3;				
			}
		}
		return -1;		
	}
	
	void checkSpaces (int count) {//만약 같은 편이면 null로 바꿔준다.
		for (int i=0; i<count; i++) {
			if (turn == 0) {			
				if (moveBoard[i].equals(white)) 
					moveBoard[i] = null;
			}
			else {	
				if (moveBoard[i].equals(black)) 
					moveBoard[i] = null;
			}			
		}
	}
	
	void chooseSpace (int from) {
		while(true) {
			//여기다가 날곳을 누르는 이벤트
			int to = 1;	//moveCoordinate[to]로 쓸건데 나중에 바꾸려면 바꾸고 ㅇㅇ
			if (moveBoard[moveCoordinate[to]] == null)
				continue;
			else {
				if (turn == 0) {		
					if(board[moveCoordinate[to]].getGood())
						blackGood --;
					else if (board[moveCoordinate[to]].getBed())
						blackBed --;
				}
				else {	
					if(board[moveCoordinate[to]].getGood())
						whiteGood --;
					else if (board[moveCoordinate[to]].getBed())
						whiteBed --;
				}			
				board[moveCoordinate[to]] = board[from];
				board[from] = null;
				return;
			}				
		}
	}
	
	//이긴사람 체크
	void checkWin() {		
		if (blackGood == 0 | whiteBed == 0)
			whiteWin = true;//그냥 바로 겜종료하고 이겼다고 하는게 날듯 일단 이렇게 표현해둠
		else if ((board[0].equals(white) && board[0].getGood()) | (board[5].equals(white) && board[5].getGood()))
			whiteWin = true;
		else if (whiteGood == 0 | blackBed == 0)
			blackWin = true;
		else if ((board[30].equals(black) && board[30].getGood()) | (board[35].equals(black) && board[35].getGood()))
			blackWin = true;		
	}

	
	
	
	//board생성 파트
	void chooseGood () {
		int i =0;
		while (true) {
			//i == 선택하는 좌표
			if (setGood(i)) {
				whiteGood ++;
			}
			if (whiteGood == 4)
				break;
		}		
		while (true) {
			if (setGood(i)) {
				blackGood ++;
			}
			if (blackGood == 4)
				break;
		}
	}
	
	boolean setGood(int i) {
		if (board[i].getBed())
		{
			board[i].setGood();
			return true;
		}
		//여기로 온경우는 같은 좌표를 또 선택한 경우니 메세지 내보내면 좋을듯 다시고르세요 같은
		return false;
	}
}
