import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int userHandX, userHandY, newUserHandX, newUserHandY ;

    static int x=0 ;
    static int y=0 ;
    static int boardSize = 32 ;
    public void paintComponent(Graphics block){
        super.paintComponent(block);
        this.setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0 ; i < 64 ; i+=2){
            block.setColor(new Color(255,200,100));
            block.fillRect((i%8 + (1/8)%2)*boardSize, (i/8)*boardSize,boardSize,boardSize);
            block.setColor((new Color(150, 50, 30)));
            block.fillRect(((i+1)%8 - ((i+1)/8)%2)*boardSize, ((i+1)/8)*boardSize,boardSize,boardSize);
        }
        Image ChessPiecess ;
        ChessPiecess = new ImageIcon("piecesOfChess.png").getImage() ;

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
                block.drawImage(ChessPiecess, (i%8)*boardSize, (i/8)*boardSize, (i%8+1)*boardSize, (i/8+1)*boardSize, j*64, k*64, (j+1)*64, (k+1)*64, this) ;
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

    public void mouseMoved(MouseEvent event){
        x = event.getX();
        y= event.getY();
        repaint();
    }
    public void mousePressed(MouseEvent event){
        if(event.getX() < 8*boardSize && event.getY() < 8*boardSize){
            userHandX = event.getX() ;
            userHandY = event.getY();
            repaint();
        }
    }
    public void mouseReleased(MouseEvent event){
        String moveDrag ;
        if(event.getX() < 8*boardSize && event.getY() < 8*boardSize){
            newUserHandX = event.get() ;
            newUserHandY = event.getY();
            if (event.getButton() == MouseEvent.BUTTON1){
                if(newUserHandY/boardSize == 0 && userHandY/boardSize == 1 && "p".equals(ChessboardMain.chessBoard[userHandY/boardSize][userHandX/boardSize])){
                    moveDrag = "" + userHandY/boardSize + userHandX/boardSize + ChessboardMain.chessBoard[newUserHandY/boardSize][newUserHandX/boardSize]"QP" ;
                        //pawn
                } else {
                    // regular move
                    moveDrag = "" + userHandY/boardSize + userHandX/boardSize + newUserHandY/boardSize + newUserHandX/boardSize ;
                }
                String userPossible = ChessboardMain.possibleMove();
                if (userPossible.replace(moveDrag, ""). length() < userPossible.length()){
                    ChessboardMain.makeMove(moveDrag) ;
                }

            }
            repaint();
        }
    }
    public void mouseClicked(MouseEvent event){

    }
    public void mouseDragged(MouseEvent event){

    }
    public void mouseEntered(MouseEvent event){

    }
    public void mouseExited(MouseEvent event){

    }
}
