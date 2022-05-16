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

    public static void main(String[] args){

        JFrame frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UserInterface userInterface = new UserInterface();
        frame.add(userInterface) ;
        frame.setSize(700,700) ;
        frame.setVisible(true);
    }

    public static String possibleMove(){
        String move = "" ;
        for (int i=0 ; i < 64 ; i++){
            switch (chessBoard[i/8][i/8]) {
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
        String move = "" ;
        return move ;
    }
    public static String possibleE(int i) {
        String move = "" ;
        return move ;
    }

}
