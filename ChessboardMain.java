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
        String move = "" ;
        return move ;
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
                        if (" ".equals(chessBoard[x+temp*j][y+temp*k])) {
                            oldPiece = chessBoard[x+temp*j][y+temp*k];
                            chessBoard[x][y] = " ";
                            chessBoard[x+temp*j][y+temp*k] = "Q";
                            int kingPlace = kingCoorBig;
                            kingCoorBig = i + (j/3) * 8 + j%3 - 9;
                            if (kingSafe()) {
                                move = move + x + y + (x+temp*j) + (y+temp*k)+oldPiece;
                            }
                            chessBoard[x][y] = "Q";
                            chessBoard[x+temp*j][y+temp*k] = oldPiece;
                            kingCoorBig = kingPlace;
                        }

                    } catch (Exception e){}
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
