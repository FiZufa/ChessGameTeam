import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class UserInterface extends JPanel implements MouseListener, MouseMotionListener{
    static int mouseX, mouseY, newMouseX, newMouseY;
    static int squareSize=60;
    @Override
    public void paintComponent(Graphics block) {
        super.paintComponent(block);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        for (int i=0;i<64;i+=2) {
            block.setColor(new Color(255,200,100));
            block.fillRect((i%8+(i/8)%2)*squareSize, (i/8)*squareSize, squareSize, squareSize);
            block.setColor(new Color(150,50,30));
            block.fillRect(((i+1)%8-((i+1)/8)%2)*squareSize, ((i+1)/8)*squareSize, squareSize, squareSize);
        }
        Image chessPiecesImage;
        chessPiecesImage=new ImageIcon("chess.png").getImage();
        for (int i=0;i<64;i++) {
            int j=-1,k=-1;
            switch (ChessboardMain.chessBoard[i/8][i%8]) {
                case "P": j=5; k=0;
                    break;
                case "p": j=5; k=1;
                    break;
                case "R": j=4; k=0;
                    break;
                case "r": j=4; k=1;
                    break;
                case "K": j=3; k=0;
                    break;
                case "k": j=3; k=1;
                    break;
                case "B": j=2; k=0;
                    break;
                case "b": j=2; k=1;
                    break;
                case "Q": j=1; k=0;
                    break;
                case "q": j=1; k=1;
                    break;
                case "E": j=0; k=0;
                    break;
                case "e": j=0; k=1;
                    break;
            }
            if (j!=-1 && k!=-1) {
                block.drawImage(chessPiecesImage, (i%8)*squareSize, (i/8)*squareSize, (i%8+1)*squareSize, (i/8+1)*squareSize, j*200, k*200, (j+1)*200, (k+1)*200, this);
            }
        }
        /*block.setColor(Color.BLUE);
        block.fillRect(x-20, y-20, 40, 40);
        block.setColor(new Color(190,81,215));
        block.fillRect(40, 20, 80, 50);
        block.drawString("Jonathan", x, y);
        */
    }
    @Override
    public void mouseMoved(MouseEvent event) {}
    @Override
    public void mousePressed(MouseEvent event) {
        if (event.getX()<8*squareSize &&event.getY()<8*squareSize) {
            //if inside the board
            mouseX=event.getX();
            mouseY=event.getY();
            repaint();
        }
    }
    @Override
    public void mouseReleased(MouseEvent event) {
        if (event.getX()<8*squareSize &&event.getY()<8*squareSize) {
            //if inside the board
            newMouseX=event.getX();
            newMouseY=event.getY();
            if (event.getButton()==MouseEvent.BUTTON1) {
                String dragMove;
                if (newMouseY/squareSize==0 && mouseY/squareSize==1 && "P".equals(ChessboardMain.chessBoard[mouseY/squareSize][mouseX/squareSize])) {
                    //pawn promotion
                    dragMove=""+mouseX/squareSize+newMouseX/squareSize+ChessboardMain.chessBoard[newMouseY/squareSize][newMouseX/squareSize]+"QP";
                } else {
                    //regular move
                    dragMove=""+mouseY/squareSize+mouseX/squareSize+newMouseY/squareSize+newMouseX/squareSize+ChessboardMain.chessBoard[newMouseY/squareSize][newMouseX/squareSize];
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
    public void mouseClicked(MouseEvent event) {}
    @Override
    public void mouseDragged(MouseEvent event) {}
    @Override
    public void mouseEntered(MouseEvent event) {}
    @Override
    public void mouseExited(MouseEvent event) {}
}
