import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class UserInterface extends JPanel implements MouseListener, MouseMotionListener {
    static int x=0 ;
    static int y=0 ;
    public void paintComponent(Graphics block){
        super.paintComponent(block);
        this.setBackground(Color.white);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        block.setColor(Color.MAGENTA) ;
        block.fillRect(40,40,70,70);
        block.setColor(Color.lightGray);
        block.fillRect(110,40,70,70);
        block.drawString("HELO", x, y);
        Image ChessPiecess ;
        ChessPiecess = new ImageIcon("ChessPiecess.png").getImage() ;
        block.drawImage(ChessPiecess, x, 0, x+100, 100, x, 0, x+100, 100, this) ;
    }

    public void mouseMoved(MouseEvent event){
        x = event.getX();
        y= event.getY();
        repaint();
    }
    public void mousePressed(MouseEvent event){

    }
    public void mouseReleased(MouseEvent event){

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
