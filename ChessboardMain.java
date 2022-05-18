import javax.swing.*;

public class ChessboardMain {

    static String chessBoard[][] = {
            {"r", "k", "b", "q", "e", "b", "k", "r"},
            {"p", "p", "p", "p", "p", "p", "p", "p"},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {" ", " ", " ", " ", " ", " ", " ", " "},
            {"P", "P", "P", "P", "P", "P", "P", "P"},
            {"R", "K", "B", "Q", "E", "B", "K", "R"}
    } ;
    // rook, knight, bishop, queen, emperor/king, pawn

    static int kingCoorBig, kingCoorSmall ;

    public static void main(String[] args){

        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserInterface userInterface = new UserInterface();
        frame.add(userInterface) ;
        frame.setSize(700,700) ;
        frame.setVisible(true);
        System.out.println(possibleMove());
    }

    public static String possibleMove(){
        String move = "" ;
        for (int i=0 ; i < 64 ; i++){
            switch (chessBoard[i/8][i%8]) {
                case "p" : move += possibleP(i) ; break ;
                case "R" : move += possibleR(i) ; break ;
                case "K" : move += possibleK(i) ; break ;
                case "B" : move += possibleB(i) ; break ;
                case "Q" : move += possibleQ(i) ; break ;
                case "E" : move += possibleE(i) ; break ;
            }
        }
        return move ; //x2, y1, x2, y2, capturedPieces
     }

     public static String possibleP(int i) {
        String move=" ", oldPiece;
        int x=i/8, y=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//capture
                if (Character.isLowerCase(chessBoard[x-1][y+j].charAt(0)) && i>=16) {
                    oldPiece=chessBoard[r-1][c+j];
                    chessBoard[x][y]=" ";
                    chessBoard[x-1][y+j]="P";
                    if (kingSafe()) {
                        move=move+x+y+(x-1)+(y+j)+oldPiece;
                    }
                    chessBoard[x][y]="P";
                    chessBoard[x-1][y+j]=oldPiece;
                }
            } catch (Exception e) {}
            try {//promotion && capture
                if (Character.isLowerCase(chessBoard[x-1][y+j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessBoard[x-1][y+j];
                        chessBoard[x][y]=" ";
                        chessBoard[x-1][y+j]=temp[k];
                        if (kingSafe()) {
                            //column1,column2,captured-piece,new-piece,P
                            move=move+y+(y+j)+oldPiece+temp[k]+"P";
                        }
                        chessBoard[x][y]="P";
                        chessBoard[x-1][y+j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {//move one up
            if (" ".equals(chessBoard[x-1][y]) && i>=16) {
                oldPiece=chessBoard[x-1][y];
                chessBoard[x][y]=" ";
                chessBoard[x-1][y]="P";
                if (kingSafe()) {
                    move=move+x+y+(x-1)+y+oldPiece;
                }
                chessBoard[x][y]="P";
                chessBoard[x-1][y]=oldPiece;
            }
        } catch (Exception e) {}
        try {//promotion && no capture
            if (" ".equals(chessBoard[x-1][y]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessBoard[x-1][y];
                    chessBoard[x][y]=" ";
                    chessBoard[x-1][y]=temp[k];
                    if (kingSafe()) {
                        //column1,column2,captured-piece,new-piece,P
                        move=move+y+y+oldPiece+temp[k]+"P";
                    }
                    chessBoard[x][y]="P";
                    chessBoard[x-1][y]=oldPiece;
                }
            }
        } catch (Exception e) {}
        try {//move two up
            if (" ".equals(chessBoard[x-1][y]) && " ".equals(chessBoard[x-2][y]) && i>=48) {
                oldPiece=chessBoard[x-2][y];
                chessBoard[x][y]=" ";
                chessBoard[x-2][y]="P";
                if (kingSafe()) {
                    move=move+x+y+(x-2)+y+oldPiece;
                }
                chessBoard[x][y]="P";
                chessBoard[x-2][y]=oldPiece;
            }
        } catch (Exception e) {}
        return move;
    }
    public static String possibleR(int i) {
        String move = "" ;
        return move ;
    }
    public static String possibleB(int i) {
        String move = "" ;
        return move ;
    }
    public static String possibleK(int i) {
        String move = "" ;
        String oldPiece ;
        int x=i/8 , y=i%8 ;
        int temp = 1 ;
        for (int j=-1 ; j <= 1 ; j+=2){
            for (int k=-1 ; j<=1 ; k+=2){
                try{
                    if ( Character.isLowerCase(chessBoard[x+j][y+k*2].charAt(0))|| " ".equals((chessBoard[x+j][y+k*2]))){
                        oldPiece = chessBoard[x+j][y+k*2] ;
                        chessBoard[x][y] = " " ;
                        if (kingSafe()){
                            move = move + x + y + (x+j) + (y+k*2) + oldPiece ;
                        }
                        chessBoard[x][y] = "K" ;
                        chessBoard[x+j][y+k*2] = oldPiece ;
                    }
                } catch (Exception e){}

                try{
                    if (Character.isLowerCase(chessBoard[x+j*2][y+k].charAt(0)) || " ".equals(chessBoard[x+j*2][y+k])){
                        oldPiece = chessBoard[x+j*2][y+k] ;
                        chessBoard[x][y] = " ";
                        if (kingSafe()){
                            move = move + x + y + (x+j*2) + (y+k) + oldPiece ;
                        }
                        chessBoard[x][y] = "K" ;
                        chessBoard[x+j*2][y+k] = oldPiece ;
                    }
                } catch (Exception e){}
            }
        }
        return move ;
    }
    public static String possibleQ(int i) {
        String move = "" ;oldPiece ;
        int x = i/8, y = i%8 ;
        int index = 1;
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {

                 //error handling
                    try {
                        while (" ".equals(chessBoard[x+temp*j][y+temp*k])) {
                            oldPiece = chessBoard[x+temp*j][y+temp*k];
                            chessBoard[x][y] = " ";
                            chessBoard[x+temp*j][y+temp*k] = "Q";
                            if (kingSafe()) {
                                move = move + x + y + (x+temp*j) + (y+temp*k)+oldPiece;
                            }
                            chessBoard[x][y] = "Q";
                            chessBoard[x+temp*j][y+temp*k] = oldPiece;
                            index++;
                        }
                        if (Character.isLowerCase(chessBoard[x+temp*j][y+temp*k].charAt(0))) {
                            oldPiece=chessBoard[x+temp*j][y+temp*k];
                            chessBoard[x][y]=" ";
                            chessBoard[x+temp*j][y+temp*k]="Q";
                            if (kingSafe()) {
                                move=move+x+y+(x+temp*j)+(y+temp*k) + oldPiece;
                            }
                            chessBoard[x][y]="Q";
                            chessBoard[x+temp*j][y+temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return move ;
    }
    public static String possibleE(int i) {
        String move = "", oldPiece ;
        int x = i/8, y=i%8 ;
        for (int j=0 ; j<9 ; j++){
            if(j != 4) {

                 //error handling
                try {
                    if (Character.isLowerCase(chessBoard[x - 1 + j/3][y - 1 + j%3].charAt(0)) ||
                            " ".equals(chessBoard[x - 1 + j/3][y - 1 + j%3])) {
                        oldPiece = chessBoard[x - 1 + j/3][y - 1 + j%3];
                        chessBoard[x][y] = " ";
                        chessBoard[x - 1 + j / 3][y - 1 + j % 3] = "E";
                        int kingPlace = kingCoorBig;
                        kingCoorBig = i + (j/3) * 8 + j%3 - 9;
                        if (kingSafe()) {
                            move = move + x + y + (x - 1 + j/3) + (y - 1 + j%3)+oldPiece;
                        }
                        chessBoard[x][y] = "E";
                        chessBoard[x - 1 + j/3][y - 1 + j%3] = oldPiece;
                        kingCoorBig = kingPlace;
                    }

                } catch (Exception e){}
            }
        }
        return move ; // need to add casting
    }

    public static boolean kingSafe(){
        return true ;
    }

}
