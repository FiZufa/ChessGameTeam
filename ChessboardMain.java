import java.util.*;
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

    static int kingCoorBig;
    static int kingCoorSmall ;
    static int humanAsWhite=-1;//1=human as white, 0=human as black
    
    static int defaultWhite ;
    static int globalDepth = 4 ;
    
    public static void main(String[] args){

        JFrame frame = new JFrame("CHESS GAME");
        frame.setSize(700, 700) ;
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserInterface userInterface = new UserInterface();
        frame.add(userInterface) ;
        frame.setVisible(true);
        System.out.println(sortMoves(possibleMove()));
        Object[] option={"Computer","Human"};
        humanAsWhite=JOptionPane.showOptionDialog(null, "Who should play first?", "ABC Options", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        if (humanAsWhite==0) {
            long startTime=System.currentTimeMillis();
            makeMove(chessGame(globalDepth, 1000000, -1000000, "", 0));
            long endTime=System.currentTimeMillis();
            System.out.println("That took "+(endTime-startTime)+" milliseconds");
            flipBoard();
            frame.repaint();
        }
        makeMove("7655 ");
        undoMove("7655 ");
        for (int i=0;i<8;i++) {
            System.out.println(Arrays.toString(chessBoard[i]));
        }
    }

    public static String chessGame (int depth, int beta, int alfa, String moving, int player) {
        String move = possibleMove() ;
        if (depth == 0 || move.length() == 0){
            return moving + (Rate.rate(move.length(), depth)*(player*2-1)) ;
        }
        //sort LATER
        move = sortMoves(move) ;
        player = 1 - player ;
        for (int i=0; i < move.length() ; i+=5){
            makeMove(move.substring(i, i+5));
            flipBoard() ;
            String returnString = chessGame(depth-1, beta, alfa, move.substring(i, i+5), player) ;
            int value = Integer.valueOf(returnString.substring(5)) ;
            flipBoard();
            undoMove(move.substring(i, i+5));
            if (player==0){
                if(value <= beta){
                    beta = value ;
                    if (depth == globalDepth){
                        moving = returnString.substring(0,5);
                    }
                }
            } else {
                if (value > alfa){
                    alfa = value ;
                    if (depth == globalDepth){
                        moving = returnString.substring(0,5) ;
                    }
                }
            } if (alfa>=beta) {
                if (player==0) {return moving+beta;} else {return move+alfa;}
            }

        }
        if (player == 0){
            return moving+beta ;
        } else {
            return moving+alfa ;
        }

    }

    public static void flipBoard(){
        String temp;
        for (int i=0 ; i < 32 ; i++) {
            int x=i/8, y=i%8;
            if (Character.isUpperCase(chessBoard[x][y].charAt(0))) {
                temp = chessBoard[x][y].toLowerCase() ;
            } else {
                temp = chessBoard[x][y].toUpperCase();
            }
            if (Character.isUpperCase(chessBoard[7-x][7-y].charAt(0))) {
                chessBoard[x][y]=chessBoard[7-x][7-y].toLowerCase();
            } else {
                chessBoard[x][y]=chessBoard[7-x][7-y].toUpperCase();
            }
            chessBoard[7-x][7-y]=temp;
        }
        int kingTemp = kingCoorBig;
        kingCoorBig = 63 - kingCoorSmall;
        kingCoorSmall = 63 - kingTemp;
    }


    public static void makeMove(String moving){
        if(moving.charAt(4) != 'P'){
            chessBoard[Character.getNumericValue(moving.charAt(2))][Character.getNumericValue(moving.charAt(3))] = chessBoard[Character.getNumericValue(moving.charAt(0))][Character.getNumericValue(moving.charAt(1))] ;
            chessBoard[Character.getNumericValue(moving.charAt(0))][Character.getNumericValue(moving.charAt(1))] = " " ;
        } else {
            // pawn promotion
            chessBoard[1][Character.getNumericValue(moving.charAt(0))] = " " ;
            chessBoard[0][Character.getNumericValue(moving.charAt(1))] = String.valueOf(moving.charAt(3));
        }
    }
    public static void undoMove(String moving){
        if(moving.charAt(4) != 'P'){
            chessBoard[Character.getNumericValue(moving.charAt(0))][Character.getNumericValue(moving.charAt(1))] = chessBoard[Character.getNumericValue(moving.charAt(2))][Character.getNumericValue(moving.charAt(3))] ;
            chessBoard[Character.getNumericValue(moving.charAt(2))][Character.getNumericValue(moving.charAt(3))] = String.valueOf(moving.charAt(4)) ;
        } else {
            // pawn promotion
            chessBoard[1][Character.getNumericValue(moving.charAt(0))] = "P" ;
            chessBoard[0][Character.getNumericValue(moving.charAt(1))] = String.valueOf(moving.charAt(2));
        }
    }
    public static String possibleMove(){
        String move = "" ;
        for (int i=0 ; i < 64 ; i++){
            switch (chessBoard[i/8][i%8]) {
                case "P" : move += possibleP(i) ; break ;
                case "R" : move += possibleR(i) ; break ;
                case "K" : move += possibleK(i) ; break ;
                case "B" : move += possibleB(i) ; break ;
                case "Q" : move += possibleQ(i) ; break ;
                case "E" : move += possibleE(i) ; break ;
            }
        }
        return move ; //x2, y1, x2, y2, capturedPieces
     }

     public static int rating (){
        return 0 ;
     }

    //  ------------------------------------------- P A W N -------------------------------------------------------------------------------
     public static String possibleP(int i) {
        String move=" ", oldPiece;
        int x=i/8, y=i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//capture
                if (Character.isLowerCase(chessBoard[x-1][y+j].charAt(0)) && i>=16) {
                    oldPiece=chessBoard[x-1][y+j];
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
    // ----------------------------------------------------- R O O K --------------------------------------------------------------------------------
    public static String possibleR(int i) {
        String move = "" ;
        String oldPiece ;
        int temp = 1 ;
        int x = i/8, y=i%8 ;
        for (int j=-1 ; j<=1 ;j+=2){
            try {
                while (" ".equals(chessBoard[x][y+temp*j])){
                    oldPiece = chessBoard[x][y+temp*j] ;
                    chessBoard[x][y] = " " ;
                    chessBoard[x][y+temp*j] = "R" ;
                    if (kingSafe()) {
                        move = move + x + y + x + (y+temp*j)+oldPiece;
                    }
                    chessBoard[x][y] = "R" ;
                    chessBoard[x][y+temp*j] = oldPiece ;
                    temp++ ;
                }
                if (Character.isLowerCase(chessBoard[x][y+temp*j].charAt(0))){
                    oldPiece = chessBoard[x][y+temp*j] ;
                    chessBoard[x][y] = " " ;
                    chessBoard[x][y+temp*j] = "R" ;
                    if (kingSafe()) {
                        move = move + x + y + x + (y+temp*j)+oldPiece;
                    }
                    chessBoard[x][y] = "R" ;
                    chessBoard[x][y+temp*j] = oldPiece ;
                }
            } catch (Exception e){}
            temp = 1 ;
            try {
                while (" ".equals(chessBoard[x+temp*j][y])){
                    oldPiece = chessBoard[x+temp*j][y] ;
                    chessBoard[x][y] = " " ;
                    chessBoard[x+temp*j][y] = "R" ;
                    if (kingSafe()) {
                        move = move + x + y + y + (x+temp*j)+oldPiece;
                    }
                    chessBoard[x][y] = "R" ;
                    chessBoard[x+temp*j][y] = oldPiece ;
                    temp++ ;
                }
                if (Character.isLowerCase(chessBoard[x+temp*j][y].charAt(0))){
                    oldPiece = chessBoard[x+temp*j][y] ;
                    chessBoard[x][y] = " " ;
                    chessBoard[x+temp*j][y] = "R" ;
                    if (kingSafe()) {
                        move = move + x + y + y + (x+temp*j)+oldPiece;
                    }
                    chessBoard[x][y] = "R" ;
                    chessBoard[x+temp*j][y] = oldPiece ;
                }
            } catch (Exception e){}
            temp = 1 ;
        }
        return move ;
    }
    // ------------------------------------------------------- B I S H O P ----------------------------------------------------------------------------
    public static String possibleB(int i) {
        String move = "" ;
        String oldPiece ;
        int x = i/8, y = i%8 ;
        int temp = 1;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                if (j!=0 || k!=0) {
                    //error handling
                    try {
                        while (" ".equals(chessBoard[x+temp*j][y+temp*k])) {
                            oldPiece = chessBoard[x+temp*j][y+temp*k];
                            chessBoard[x][y] = " ";
                            chessBoard[x+temp*j][y+temp*k] = "B";
                            if (kingSafe()) {
                                move = move + x + y + (y+temp*k) + y +oldPiece;
                            }
                            chessBoard[x][y] = "B";
                            chessBoard[x+temp*j][y+temp*k] = oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(chessBoard[x+temp*j][y+temp*k].charAt(0))) {
                            oldPiece=chessBoard[x+temp*j][y+temp*k];
                            chessBoard[x][y]=" ";
                            chessBoard[x+temp*j][y+temp*k]="B";
                            if (kingSafe()) {
                                move = move + x + y + (x+temp*j) + (y+temp*k) +oldPiece;
                            }
                            chessBoard[x][y]="B";
                            chessBoard[x+temp*j][y+temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return move ;
    }
    // ------------------------------------------------ K N I G H T ----------------------------------------------------------------------------------
    public static String possibleK(int i) {
        String move = "" ;
        String oldPiece ;
        int x=i/8 , y=i%8 ;
        for (int j=-1 ; j <= 1 ; j+=2){
            for (int k=-1 ; k<=1 ; k+=2){
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
    // ----------------------------------------------------------- Q U E E N --------------------------------------------------------------------
    public static String possibleQ(int i) {
        String move = "" ;
        String oldPiece ;
        int x = i/8, y = i%8 ;
        int temp = 1;
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
                            temp++;
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
    // --------------------------------------------------------K I N G -----------------------------------------------------------------------
    public static String possibleE(int i) {
        String move = "" ;
        String oldPiece ;
        int x = i/8, y=i%8 ;
        for (int j=0; j<9; j++) {
            if (j != 4) {
                try {
                    if (Character.isLowerCase(chessBoard[x-1+j/3][y-1+j%3].charAt(0)) || " ".equals(chessBoard[x-1+j/3][y-1+j%3])){
                        oldPiece = chessBoard[x-1+j/3][y-1+j%3] ;
                        chessBoard[x][y] = " " ;
                        chessBoard[x-1+j/3][y-1+j%3] = "E" ;
                        int kingPlace = kingCoorBig ;
                        kingCoorBig = i + (j/3)*8 + j%3 - 9 ;
                        if(kingSafe()){
                            move = move + x +y + (x-1+j/3) + (y-1+j%3) + oldPiece ;
                        }
                        chessBoard[x][y] = "E" ;
                        chessBoard[x-1+j/3][y-1+j%3] = oldPiece ;
                        kingCoorBig = kingPlace ;
                    }

                } catch (Exception e){}
            }

                 //error handling
        }
        return move ; // need to add casting
    }

    public static String sortMoves(String move){
        int[] score = new int[move.length()/5] ;
        for (int i=0;i<move.length();i+=5) {
            makeMove(move.substring(i, i+5));
            score[i/5]=-Rate.rate(-1, 0);
            undoMove(move.substring(i, i+5));
        }
        String newListA="", newListB=move;
        for (int i=0;i<Math.min(6, move.length()/5);i++) {//first few moves only
            int max=-1000000, maxLocation=0;
            for (int j=0;j<move.length()/5;j++) {
                if (score[j]>max) {max=score[j]; maxLocation=j;}
            }
            score[maxLocation]=-1000000;
            newListA+=move.substring(maxLocation*5,maxLocation*5+5);
            newListB=newListB.replace(move.substring(maxLocation*5,maxLocation*5+5), "");
        }
        return newListA+newListB;
    }

    // --------------------------------- KING SAFE -------------------------------------------------------------------------------------
    public static boolean kingSafe(){
        // bishop/queen
        int temp = 1 ;
        for (int i=-1 ; i <= 1 ; i+=2){
            for (int j=-1 ; i<=1 ; j+=2){
                try {
                    while (" ".equals(chessBoard[kingCoorBig/8+temp*i][kingCoorBig%8+temp*j])){
                        temp++ ;
                    }
                    if ("r".equals(chessBoard[kingCoorBig/8][kingCoorBig%8+temp*i]) || "q".equals(chessBoard[kingCoorBig/8][kingCoorBig%8+temp*i])){
                        return false ;
                    }
                } catch (Exception e){}
                temp = 1 ;
                try {
                    while (" ".equals(chessBoard[kingCoorBig/8+temp*i][kingCoorBig%8])){
                        temp++ ;
                    }
                    if ("r".equals(chessBoard[kingCoorBig/8+temp*i][kingCoorBig%8]) || "r".equals(chessBoard[kingCoorBig/8+temp*i][kingCoorBig%8])){
                        return false ;
                    }
                } catch (Exception e) {}
                temp = 1 ;
            }
        }
        // knight
        for (int i=-1 ; i <= 1 ; i+=2){
            for (int j=-1 ; i<=1 ; j+=2){
                try {
                    if ("k".equals(chessBoard[kingCoorBig/8+1][kingCoorBig%8+j*2])){
                        return false ;
                    }
                } catch (Exception e){}
                try {
                    if ("k".equals(chessBoard[kingCoorBig/8+1*2][kingCoorBig%8+j])){
                        return false ;
                    }
                } catch (Exception e){}
            }
        }
        // pawn
        if (kingCoorBig >= 16){
            try {
                if ("p".equals(chessBoard[kingCoorBig/80-1][kingCoorBig%8-1])){
                    return false ;
                }
            } catch (Exception e){}
            try {
                if ("p".equals(chessBoard[kingCoorBig/80-1][kingCoorBig%8+1])){
                    return false ;
                }
            } catch (Exception e){}

            // King
            for (int i=-1 ; i <= 1 ; i++){
                for (int j=-1 ; i<=1 ; j++){
                    if (i != 0 || j != 0){
                        try {
                            if ("a".equals(chessBoard[kingCoorBig/8+1][kingCoorBig%8+j])){
                                return false ;
                            }
                        } catch (Exception e){}

                    }

                }
            }
        }
        return true ;
    }

}
