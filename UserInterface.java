import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
    static int handX, handY, newHandX, newHandY;
    static int blockSize = 80;
    @Override
    public void paintComponent(Graphics block) {
        super.paintComponent(block);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0;i<64;i+=2) {
            block.setColor(Color.PINK);
            block.fillRect((i%8+(i/8)%2)* blockSize, (i/8)* blockSize, blockSize, blockSize);
            block.setColor(Color.white);
            block.fillRect(((i+1)%8-((i+1)/8)%2)* blockSize, ((i+1)/8)* blockSize, blockSize, blockSize);
        }
        Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("chess.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (ChessboardMain.chessBoard[i/8][i%8]) {
                case "P": j=5; k=0 ; break;
                case "p": j=5; k=1 ; break;
                case "R": j=4; k=0 ; break;
                case "r": j=4; k=1 ; break;
                case "K": j=3; k=0 ; break;
                case "k": j=3; k=1 ; break;
                case "B": j=2; k=0 ; break;
                case "b": j=2; k=1 ; break;
                case "Q": j=1; k=0 ; break;
                case "q": j=1; k=1 ; break;
                case "E": j=0; k=0 ; break;
                case "e": j=0; k=1 ; break;
            }
            if (j!=-1 && k!=-1) {
                block.drawImage(chessPiecesImage, (i%8)* blockSize, (i/8)* blockSize, (i%8+1)* blockSize, (i/8+1)* blockSize, j*200, k*200, (j+1)*200, (k+1)*200, this);
            }
        }

    }
    @Override
    public void mouseMoved(MouseEvent event) {}
    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getX()<8* blockSize &&event.getY()<8* blockSize) {
            //if inside the board
            handX =event.getX();
            handY =event.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getX()<8* blockSize &&event.getY()<8* blockSize) {
            //if inside the board
            newHandX =event.getX();
            newHandY =event.getY();
            if (event.getButton()==MouseEvent.BUTTON1) {
                String dragMove;
                if (newHandY / blockSize ==0 && handY / blockSize ==1 && "P".equals(ChessboardMain.chessBoard[handY / blockSize][handX / blockSize])) {
                    //pawn promotion
                    dragMove=""+ handX / blockSize + newHandX / blockSize +ChessboardMain.chessBoard[newHandY / blockSize][newHandX / blockSize]+"QP";
                } else {
                    //regular move
                    dragMove=""+ handY / blockSize + handX / blockSize + newHandY / blockSize + newHandX / blockSize +ChessboardMain.chessBoard[newHandY / blockSize][newHandX / blockSize];
                }
                String userPosibilities=ChessboardMain.posibleMoves();
                if (userPosibilities.replaceAll(dragMove, "").length()<userPosibilities.length()) {
                    //if valid move
                    ChessboardMain.makeMove(dragMove);
                    ChessboardMain.flipBoard();
                    ChessboardMain.makeMove(ChessboardMain.chessGame(ChessboardMain.globalDepth, 1000000, -1000000, "", 0));
                    ChessboardMain.flipBoard();
                    repaint();
                }
            }
        }
    }
    @Override
    public void mouseClicked(MouseEvent event) {

    }
    @Override
    public void mouseDragged(MouseEvent event) {

    }
    @Override
    public void mouseEntered(MouseEvent event) {

    }
    @Override
    public void mouseExited(MouseEvent event) {

    }
}
