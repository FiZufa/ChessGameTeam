import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int userHandX, userHandY, newUserHandX, newUserHandY ;
    static int userWhite = -1 ;
    static int globalDepth = 4 ;

    static int x=0 ;
    static int y=0 ;
    static int boardSize = 32 ;
    public void paintComponent(Graphics block){
        super.paintComponent(block);
        //this.setBackground(Color.yellow);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
       for (int i=0 ; i < 64 ; i+=2){
            block.setColor(Color.PINK);
            block.fillRect((i%8 + (i/8)%2)*boardSize, (i/8)*boardSize,boardSize,boardSize);
            block.setColor(Color.white);
            block.fillRect(((i+1)%8 - ((i+1)/8)%2)*boardSize, ((i+1)/8)*boardSize,boardSize,boardSize);
        }
        Image ChessPiecess ;
        ChessPiecess = new ImageIcon("chess.png").getImage() ;

        for (int i=0 ; i< 64 ; i++){
            int j = -1 ;
            int k = -1 ;
            switch (ChessboardMain.chessBoard[i/8][i%8]) {
                case "P" : j=5 ; k=0 ; break ;
                case "p" : j=5 ; k=1 ; break ;
                case "R" : j=2 ; k=0 ; break ;
                case "r" : j=2 ; k=1 ; break ;
                case "K" : j=4 ; k=0 ; break ;
                case "k" : j=4 ; k=1 ; break ;
                case "B" : j=3 ; k=0 ; break ;
                case "b" : j=3 ; k=1 ; break ;
                case "Q" : j=1 ; k=0 ; break ;
                case "q" : j=1 ; k=1 ; break ;
                case "E" : j=0 ; k=0 ; break ;
                case "e" : j=0 ; k=1 ; break ;
            }
            if (j != -1 && k!= -1){
               block.drawImage(ChessPiecess, (i%8)*boardSize, (i/8)*boardSize, (i%8+1)*boardSize, (i/8+1)*boardSize, j*200, k*200, (j+1)*200, (k+1)*200, this) ;
              
            }
        }
        /*
        block.setColor(Color.MAGENTA) ;
        block.fillRect(40,40,70,70);
        block.setColor(Color.lightGray);
        block.fillRect(110,40,70,70);
        block.drawString("HELO", x, y);
        Image ChessPiecess ;
        ChessPiecess = new ImageIcon("ChessPiecess.png").getImage() ;
        block.drawImage(ChessPiecess, x, 0, x+100, 100, x, 0, x+100, 100, this) ; */
    }
    @Override
    public void mouseMoved(MouseEvent event){
    }
    @Override
    public void mousePressed(MouseEvent event){
        if(event.getX() < 8*boardSize && event.getY() < 8*boardSize){
            userHandX = event.getX() ;
            userHandY = event.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent event){
        if(event.getX() < 8*boardSize && event.getY() < 8*boardSize){
            newUserHandX = event.getX() ;
            newUserHandY = event.getY();
            if (event.getButton() == MouseEvent.BUTTON1){
                String moveDrag ;
                if(newUserHandY/boardSize == 0 && userHandY/boardSize == 1 && "P".equals(ChessboardMain.chessBoard[userHandY/boardSize][userHandX/boardSize])){
                    moveDrag = "" + userHandY/boardSize + newUserHandX/boardSize + ChessboardMain.chessBoard[newUserHandY/boardSize][newUserHandX/boardSize] +"QP" ;
                        //pawn
                } else {
                    // regular move
                    moveDrag = "" + userHandY/boardSize + userHandX/boardSize + newUserHandY/boardSize + newUserHandX/boardSize + ChessboardMain.chessBoard[newUserHandY/boardSize][newUserHandX/boardSize] ;
                }
                String userPossible = ChessboardMain.possibleMove();
                if (userPossible.replaceAll(moveDrag, "").length() < userPossible.length()){
                    ChessboardMain.makeMove(moveDrag) ; //??????????
                    ChessboardMain.flipBoard() ;
                    ChessboardMain.makeMove(ChessboardMain.chessGame(ChessboardMain.globalDepth, 1000000, -1000000, "", 0));
                    ChessboardMain.flipBoard() ;
                    repaint() ;
                }

            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseDragged(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
