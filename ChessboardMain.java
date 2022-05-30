import java.util.*;
import javax.swing.*;
public class ChessboardMain {
    static String chessBoard[][]={
            {"r","k","b","q","e","b","k","r"},
            {"p","p","p","p","p","p","p","p"},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {" "," "," "," "," "," "," "," "},
            {"P","P","P","P","P","P","P","P"},
            {"R","K","B","Q","E","B","K","R"}};
    static int kingCoorBig, kingCoorSmall;
    static int userWhite =-1;
    static int globalDepth=4;
    public static void main(String[] args) {
        while (!"E".equals(chessBoard[kingCoorBig /8][kingCoorBig %8])) {
            kingCoorBig++;
        }
        while (!"e".equals(chessBoard[kingCoorSmall /8][kingCoorSmall %8])) {
            kingCoorSmall++;
        }

        JFrame frame =new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        UserInterface UI = new UserInterface();
        frame.add(UI);
        frame.setSize(680, 680);
        frame.setVisible(true);
        System.out.println(sortMoves(posibleMoves()));
        Object[] option={"Computer","Human"};
        userWhite =JOptionPane.showOptionDialog(null, "Who will play first?", "ABC Options", JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, option, option[1]);
        if (userWhite ==0) {
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
    public static String chessGame(int depth, int beta, int alpha, String move, int player) {
        //return in the form of 1234b##########
        String list=posibleMoves();
        if (depth==0 || list.length()==0) {
            return move+(Rate.rating(list.length(), depth)*(player*2-1));
        }
        list=sortMoves(list);
        player=1-player;//either 1 or 0
        for (int i=0;i<list.length(); i+=5) {
            makeMove(list.substring(i,i+5));
            flipBoard();
            String returnString=chessGame(depth-1, beta, alpha, list.substring(i,i+5), player);
            int value=Integer.valueOf(returnString.substring(5));
            flipBoard();
            undoMove(list.substring(i,i+5));
            if (player==0) {
                if (value <= beta) {
                    beta = value;
                    if (depth == globalDepth) {
                        move=returnString.substring(0,5);
                    }
                }
            } else {
                if (value > alpha) {
                    alpha = value;
                    if (depth == globalDepth) {
                        move=returnString.substring(0,5);
                    }
                }
            }
            if (alpha >= beta) {
                if (player==0) { return move+beta;
                } else {
                    return move+alpha;
                }
            }
        }
        if (player==0) {
            return move+beta;
        } else {
            return move+alpha;
        }
    }
    public static void flipBoard() {
        String temp;
        for (int i=0;i<32;i++) {
            int x = i/8, y = i%8;
            if (Character.isUpperCase(chessBoard[x][y].charAt(0))) {
                temp=chessBoard[x][y].toLowerCase();
            } else {
                temp=chessBoard[x][y].toUpperCase();
            }
            if (Character.isUpperCase(chessBoard[7- x][7- y].charAt(0))) {
                chessBoard[x][y]=chessBoard[7- x][7- y].toLowerCase();
            } else {
                chessBoard[x][y]=chessBoard[7- x][7- y].toUpperCase();
            }
            chessBoard[7- x][7- y]=temp;
        }
        int kingTemp= kingCoorBig;
        kingCoorBig =63- kingCoorSmall;
        kingCoorSmall =63-kingTemp;
    }
    public static void makeMove(String move) {
        if (move.charAt(4)!='P') {
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))];
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=" ";
            if ("E".equals(chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))])) {
                kingCoorBig =8*Character.getNumericValue(move.charAt(2))+Character.getNumericValue(move.charAt(3));
            }
        } else {
            //if pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))]=" ";
            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(3));
        }
    }
    public static void undoMove(String move) {
        if (move.charAt(4)!='P') {
            chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))]=chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))];
            chessBoard[Character.getNumericValue(move.charAt(2))][Character.getNumericValue(move.charAt(3))]=String.valueOf(move.charAt(4));
            if ("E".equals(chessBoard[Character.getNumericValue(move.charAt(0))][Character.getNumericValue(move.charAt(1))])) {
                kingCoorBig =8*Character.getNumericValue(move.charAt(0))+Character.getNumericValue(move.charAt(1));
            }
        } else {
            //if pawn promotion
            chessBoard[1][Character.getNumericValue(move.charAt(0))]="P";
            chessBoard[0][Character.getNumericValue(move.charAt(1))]=String.valueOf(move.charAt(2));
        }
    }
    public static String posibleMoves() {
        String list="";
        for (int i=0; i<64; i++) {
            switch (chessBoard[i/8][i%8]) {
                case "P": list+=posibleP(i);
                    break;
                case "R": list+=posibleR(i);
                    break;
                case "K": list+=posibleK(i);
                    break;
                case "B": list+=posibleB(i);
                    break;
                case "Q": list+=posibleQ(i);
                    break;
                case "E": list+=posibleE(i);
                    break;
            }
        }
        return list;//x1,y1,x2,y2,captured piece
    }

   //  ------------------------------------------------- P A W N -----------------------------------------------------------
    public static String posibleP(int i) {
        String list="" ;
        String oldPiece;
        int x = i/8, y = i%8;
        for (int j=-1; j<=1; j+=2) {
            try {//capture
                if (Character.isLowerCase(chessBoard[x -1][y +j].charAt(0)) && i>=16) {
                    oldPiece=chessBoard[x -1][y +j];
                    chessBoard[x][y]=" ";
                    chessBoard[x -1][y +j]="P";
                    if (kingSafe()) {
                        list=list+ x + y +(x -1)+(y +j)+oldPiece;
                    }
                    chessBoard[x][y]="P";
                    chessBoard[x -1][y +j]=oldPiece;
                }
            } catch (Exception e) {}
            try {//promotion && capture
                if (Character.isLowerCase(chessBoard[x -1][y +j].charAt(0)) && i<16) {
                    String[] temp={"Q","R","B","K"};
                    for (int k=0; k<4; k++) {
                        oldPiece=chessBoard[x -1][y +j];
                        chessBoard[x][y]=" ";
                        chessBoard[x -1][y +j]=temp[k];
                        if (kingSafe()) {
                            //column1,column2,captured-piece,new-piece,P
                            list=list+ y +(y +j)+oldPiece+temp[k]+"P";
                        }
                        chessBoard[x][y]="P";
                        chessBoard[x -1][y +j]=oldPiece;
                    }
                }
            } catch (Exception e) {}
        }
        try {//move one up
            if (" ".equals(chessBoard[x -1][y]) && i>=16) {
                oldPiece=chessBoard[x -1][y];
                chessBoard[x][y]=" ";
                chessBoard[x -1][y]="P";
                if (kingSafe()) {
                    list=list+ x + y +(x -1)+ y +oldPiece;
                }
                chessBoard[x][y]="P";
                chessBoard[x -1][y]=oldPiece;
            }
        } catch (Exception e) {}
        try {//promotion && no capture
            if (" ".equals(chessBoard[x -1][y]) && i<16) {
                String[] temp={"Q","R","B","K"};
                for (int k=0; k<4; k++) {
                    oldPiece=chessBoard[x -1][y];
                    chessBoard[x][y]=" ";
                    chessBoard[x -1][y]=temp[k];
                    if (kingSafe()) {
                        //column1,column2,captured-piece,new-piece,P
                        list=list+ y + y +oldPiece+temp[k]+"P";
                    }
                    chessBoard[x][y]="P";
                    chessBoard[x -1][y]=oldPiece;
                }
            }
        } catch (Exception e) {}
        try {//move two up
            if (" ".equals(chessBoard[x -1][y]) && " ".equals(chessBoard[x -2][y]) && i>=48) {
                oldPiece=chessBoard[x -2][y];
                chessBoard[x][y]=" ";
                chessBoard[x -2][y]="P";
                if (kingSafe()) {
                    list=list+ x + y +(x -2)+ y +oldPiece;
                }
                chessBoard[x][y]="P";
                chessBoard[x -2][y]=oldPiece;
            }
        } catch (Exception e) {}
        return list;
    }
    public static String posibleR(int i) {
        String list="", oldPiece;
        int x=i/8, y=i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            try {
                while(" ".equals(chessBoard[x][y+temp*j]))
                {
                    oldPiece=chessBoard[x][y+temp*j];
                    chessBoard[x][y]=" ";
                    chessBoard[x][y+temp*j]="R";
                    if (kingSafe()) {
                        list=list+x+y+x+(y+temp*j)+oldPiece;
                    }
                    chessBoard[x][y]="R";
                    chessBoard[x][y+temp*j]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoard[x][y+temp*j].charAt(0))) {
                    oldPiece=chessBoard[x][y+temp*j];
                    chessBoard[x][y]=" ";
                    chessBoard[x][y+temp*j]="R";
                    if (kingSafe()) {
                        list=list+x+y+x+(y+temp*j)+oldPiece;
                    }
                    chessBoard[x][y]="R";
                    chessBoard[x][y+temp*j]=oldPiece;
                    
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(chessBoard[x+temp*j][y]))
                {
                    oldPiece=chessBoard[x+temp*j][y];
                    chessBoard[x][y]=" ";
                    chessBoard[x+temp*j][y]="R";
                    if (kingSafe()) {
                        list=list+x+y+(x+temp*j)+y+oldPiece;
                    }
                    chessBoard[x][y]="R";
                    chessBoard[x+temp*j][y]=oldPiece;
                    temp++;
                }
                if (Character.isLowerCase(chessBoard[x+temp*j][y].charAt(0))) {
                    oldPiece=chessBoard[x+temp*j][y];
                    chessBoard[x][y]=" ";
                    chessBoard[x+temp*j][y]="R";
                    if (kingSafe()) {
                        list=list+x+y+(x+temp*j)+y+oldPiece;
                    }
                    chessBoard[x][y]="R";
                    chessBoard[x+temp*j][y]=oldPiece;
                }
            } catch (Exception e) {}
            temp=1;
        }
        return list;
    }
    // --------------------------------------------------------- B I S H O P --------------------------------------------------
    public static String posibleB(int i) {
        String list="" ;
        String oldPiece;
        int x = i/8, y = i%8;
        int temp=1;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    while(" ".equals(chessBoard[x +temp*j][y +temp*k]))
                    {
                        oldPiece=chessBoard[x +temp*j][y +temp*k];
                        chessBoard[x][y]=" ";
                        chessBoard[x +temp*j][y +temp*k]="B";
                        if (kingSafe()) {
                            list=list+ x + y +(x +temp*j)+(y +temp*k)+oldPiece;
                        }
                        chessBoard[x][y]="B";
                        chessBoard[x +temp*j][y +temp*k]=oldPiece;
                        temp++;
                    }
                    if (Character.isLowerCase(chessBoard[x +temp*j][y +temp*k].charAt(0))) {
                        oldPiece=chessBoard[x +temp*j][y +temp*k];
                        chessBoard[x][y]=" ";
                        chessBoard[x +temp*j][y +temp*k]="B";
                        if (kingSafe()) {
                            list=list+ x + y +(x +temp*j)+(y +temp*k)+oldPiece;
                        }
                        chessBoard[x][y]="B";
                        chessBoard[x +temp*j][y +temp*k]=oldPiece;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        return list;
    }
    // ------------------------------------------------------ K N I G H T --------------------------------------------------
    public static String posibleK(int i) {
        String list="" ;
        String oldPiece;
        int x = i/8, y = i%8;
        for (int j=-1; j<=1; j+=2) {
            for (int k=-1; k<=1; k+=2) {
                try {
                    if (Character.isLowerCase(chessBoard[x +j][y +k*2].charAt(0)) || " ".equals(chessBoard[x +j][y +k*2])) {
                        oldPiece=chessBoard[x +j][y +k*2];
                        chessBoard[x][y]=" ";
                        if (kingSafe()) {
                            list=list+ x + y +(x +j)+(y +k*2)+oldPiece;
                        }
                        chessBoard[x][y]="K";
                        chessBoard[x +j][y +k*2]=oldPiece;
                    }
                } catch (Exception e) {}
                try {
                    if (Character.isLowerCase(chessBoard[x +j*2][y +k].charAt(0)) || " ".equals(chessBoard[x +j*2][y +k])) {
                        oldPiece=chessBoard[x +j*2][y +k];
                        chessBoard[x][y]=" ";
                        if (kingSafe()) {
                            list=list+ x + y +(x +j*2)+(y +k)+oldPiece;
                        }
                        chessBoard[x][y]="K";
                        chessBoard[x +j*2][y +k]=oldPiece;
                    }
                } catch (Exception e) {}
            }
        }
        return list;
    }
    // ----------------------------------------------- Q U E E N ---------------------------------------------------
    public static String posibleQ(int i) {
        String list="" ;
        String oldPiece;
        int x = i/8, y = i%8;
        int temp=1;
        for (int j=-1; j<=1; j++) {
            for (int k=-1; k<=1; k++) {
                if (j!=0 || k!=0) {
                    try {
                        while(" ".equals(chessBoard[x +temp*j][y +temp*k]))
                        {
                            oldPiece=chessBoard[x +temp*j][y +temp*k];
                            chessBoard[x][y]=" ";
                            chessBoard[x +temp*j][y +temp*k]="Q";
                            if (kingSafe()) {
                                list=list+ x + y +(x +temp*j)+(y +temp*k)+oldPiece;
                            }
                            chessBoard[x][y]="Q";
                            chessBoard[x +temp*j][y +temp*k]=oldPiece;
                            temp++;
                        }
                        if (Character.isLowerCase(chessBoard[x +temp*j][y +temp*k].charAt(0))) {
                            oldPiece=chessBoard[x +temp*j][y +temp*k];
                            chessBoard[x][y]=" ";
                            chessBoard[x +temp*j][y +temp*k]="Q";
                            if (kingSafe()) {
                                list=list+ x + y +(x +temp*j)+(y +temp*k)+oldPiece;
                            }
                            chessBoard[x][y]="Q";
                            chessBoard[x +temp*j][y +temp*k]=oldPiece;
                        }
                    } catch (Exception e) {}
                    temp=1;
                }
            }
        }
        return list;
    }

    // -------------------------------------------------------------- K I N G -----------------------------------------------------
    public static String posibleE(int i) {
        String list="" ;
        String oldPiece;
        int x = i/8, y = i%8;
        for (int j=0;j<9;j++) {
            if (j!=4) {
                try {
                    if (Character.isLowerCase(chessBoard[x -1+j/3][y -1+j%3].charAt(0)) || " ".equals(chessBoard[x -1+j/3][y -1+j%3])) {
                        oldPiece=chessBoard[x -1+j/3][y -1+j%3];
                        chessBoard[x][y]=" ";
                        chessBoard[x -1+j/3][y -1+j%3]="E";
                        int kingTemp= kingCoorBig;
                        kingCoorBig =i+(j/3)*8+j%3-9;
                        if (kingSafe()) {
                            list=list+ x + y +(x -1+j/3)+(y -1+j%3)+oldPiece;
                        }
                        chessBoard[x][y]="E";
                        chessBoard[x -1+j/3][y -1+j%3]=oldPiece;
                        kingCoorBig =kingTemp;
                    }
                } catch (Exception e) {}
            }
        }
        //need to add casting later
        return list;
    }
    public static String sortMoves(String list) {
        int[] score=new int [list.length()/5];
        for (int i=0;i<list.length();i+=5) {
            makeMove(list.substring(i, i+5));
            score[i/5]=-Rate.rating(-1, 0);
            undoMove(list.substring(i, i+5));
        }
        String newListA="", newListB=list;
        for (int i=0;i<Math.min(6, list.length()/5);i++) {//first few moves only
            int max=-1000000, maxLocation=0;
            for (int j=0;j<list.length()/5;j++) {
                if (score[j]>max) {max=score[j]; maxLocation=j;}
            }
            score[maxLocation]=-1000000;
            newListA+=list.substring(maxLocation*5,maxLocation*5+5);
            newListB=newListB.replace(list.substring(maxLocation*5,maxLocation*5+5), "");
        }
        return newListA+newListB;
    }
    public static boolean kingSafe() {
        //bishop or queen
        int temp=1;
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    while(" ".equals(chessBoard[kingCoorBig /8+temp*i][kingCoorBig %8+temp*j])) {temp++;}
                    if ("b".equals(chessBoard[kingCoorBig /8+temp*i][kingCoorBig %8+temp*j]) ||
                            "q".equals(chessBoard[kingCoorBig /8+temp*i][kingCoorBig %8+temp*j])) {
                        return false;
                    }
                } catch (Exception e) {}
                temp=1;
            }
        }
        //rook or queen
        for (int i=-1; i<=1; i+=2) {
            try {
                while(" ".equals(chessBoard[kingCoorBig /8][kingCoorBig %8+temp*i])) {temp++;}
                if ("r".equals(chessBoard[kingCoorBig /8][kingCoorBig %8+temp*i]) ||
                        "q".equals(chessBoard[kingCoorBig /8][kingCoorBig %8+temp*i])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
            try {
                while(" ".equals(chessBoard[kingCoorBig /8+temp*i][kingCoorBig %8])) {temp++;}
                if ("r".equals(chessBoard[kingCoorBig /8+temp*i][kingCoorBig %8]) ||
                        "q".equals(chessBoard[kingCoorBig /8+temp*i][kingCoorBig %8])) {
                    return false;
                }
            } catch (Exception e) {}
            temp=1;
        }
        //knight
        for (int i=-1; i<=1; i+=2) {
            for (int j=-1; j<=1; j+=2) {
                try {
                    if ("k".equals(chessBoard[kingCoorBig /8+i][kingCoorBig %8+j*2])) {
                        return false;
                    }
                } catch (Exception e) {}
                try {
                    if ("k".equals(chessBoard[kingCoorBig /8+i*2][kingCoorBig %8+j])) {
                        return false;
                    }
                } catch (Exception e) {}
            }
        }
        //pawn
        if (kingCoorBig >=16) {
            try {
                if ("p".equals(chessBoard[kingCoorBig /8-1][kingCoorBig %8-1])) {
                    return false;
                }
            } catch (Exception e) {}
            try {
                if ("p".equals(chessBoard[kingCoorBig /8-1][kingCoorBig %8+1])) {
                    return false;
                }
            } catch (Exception e) {}
            //king
            for (int i=-1; i<=1; i++) {
                for (int j=-1; j<=1; j++) {
                    if (i!=0 || j!=0) {
                        try {
                            if ("e".equals(chessBoard[kingCoorBig /8+i][kingCoorBig %8+j])) {
                                return false;
                            }
                        } catch (Exception e) {}
                    }
                }
            }
        }
        return true;
    }
}
