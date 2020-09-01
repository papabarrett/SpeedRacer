/**
 *
 * @author NAME
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.*;

public class SpeedRacerMain implements ActionListener, KeyListener {

    javax.swing.Timer timer;
    JFrame frame;
    JPanel display;
    Car car;

    public static void main(String[] args) throws Exception {
        new SpeedRacerMain();
    }

    public SpeedRacerMain() {
        frame = new JFrame("Speed Racer");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display = new DisplayPanel();
        frame.add(display);
        //put constructor code here
        car=new Car(Color.GREEN, 0, 250, 250);
        
        
        
        //end your constructor code
        timer = new javax.swing.Timer(10, this);
        timer.start();
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        //type what needs to be performed every time the timer ticks
        car.move();
        
        //end your code for timer tick code
        display.repaint();
    }

    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP)
            car.setUp(true);
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            car.setDown(true);
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            car.setLeft(true);
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            car.setRight(true);
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP)
            car.setUp(false);
        if(e.getKeyCode()==KeyEvent.VK_DOWN)
            car.setDown(false);
        if(e.getKeyCode()==KeyEvent.VK_LEFT)
            car.setLeft(false);
        if(e.getKeyCode()==KeyEvent.VK_RIGHT)
            car.setRight(false);
    }

    class DisplayPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw your graphics here
            car.draw(g);
            
            
        }
    }
}