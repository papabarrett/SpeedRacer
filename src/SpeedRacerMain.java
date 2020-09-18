
/**
 *
 * @author NAME
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.lang.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SpeedRacerMain implements ActionListener, KeyListener {

    javax.swing.Timer timer;
    JFrame frame;
    JPanel display;
    Car car, chaser;
    ArrayList<EnvCoin> envcoins;
    ArrayList<Block> blocks;
    ArrayList<Coin> coins;
    ArrayList<Explosion> explosions;
    boolean running;
    int level;
    final int LEVEL_MAX=3;
    Image titleScreen;
    long startTime;
    Image grassImage = new ImageIcon("Grass.png").getImage();
    int justEnded;
    boolean coingrabed;

    public static void main(String[] args) throws Exception {
        File f = new File("course4.txt");
        f.createNewFile();
        new SpeedRacerMain();
    }

    public SpeedRacerMain() {
        justEnded = 0;
        running = false;
        coingrabed = false;
        titleScreen = new ImageIcon("titleScreen.jpg").getImage();
        frame = new JFrame("Speed Racer");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display = new DisplayPanel();
        frame.add(display);
        //put constructor code here
        startTime = System.currentTimeMillis();
        System.out.println(startTime);
        car = new Car(Color.GREEN, 0, 250, 250, 25);
        chaser = new Car(Color.GREEN, 0, 200, 250, 25);
        blocks = new ArrayList<Block>();
        coins = new ArrayList<Coin>();
        envcoins = new ArrayList<EnvCoin>();
        explosions = new ArrayList<Explosion>();
        level=0;
        try {
            Scanner scan = new Scanner(new File("course"+level+".txt"));
            int countLines = 0;
            while (scan.hasNext()) {
                String line = scan.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'x') {
                        blocks.add(new Block(i * 50, countLines * 50));
                    }
                    if (line.charAt(i) == 'c') {
                        coins.add(new Coin(i * 50, countLines * 50));
                    }
                    if (line.charAt(i) == 'y') {
                        envcoins.add(new EnvCoin(i * 50, countLines * 50));
                    }
                }
                countLines++;
            }
        } catch (Exception e) {
        }

        //end your constructor code
        timer = new javax.swing.Timer(10, this);
        timer.start();
        frame.addKeyListener(this);
        frame.setFocusable(true);
        frame.setVisible(true);
    }

    public void startOver() {
        running = false;
        startTime = System.currentTimeMillis();
        car = new Car(Color.GREEN, 0, 250, 250, 25);
        blocks = new ArrayList<Block>();
        coins = new ArrayList<Coin>();
        envcoins = new ArrayList<EnvCoin>();
        level++;
        if(level>LEVEL_MAX)level=0;
        try {
            Scanner scan = new Scanner(new File("course"+level+".txt"));
            int countLines = 0;
            while (scan.hasNext()) {
                String line = scan.nextLine();
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == 'x') {
                        blocks.add(new Block(i * 50, countLines * 50));
                    }
                    if (line.charAt(i) == 'c') {
                        coins.add(new Coin(i * 50, countLines * 50));
                    }
                    if (line.charAt(i) == 'y') {
                        envcoins.add(new EnvCoin(i * 50, countLines * 50));
                    }
                }
                countLines++;
            }
        } catch (Exception e) {
        }

        //end your constructor code
    }

    public void endGame() {
        running = false;
        startOver();
        justEnded = 50;
    }

    public void startGame() {
        running = true;
        startTime = System.currentTimeMillis();
    }

    public void actionPerformed(ActionEvent e) {
        //type what needs to be performed every time the timer ticks
        justEnded--;
        justEnded = Math.max(0, justEnded);
        if (!running) {
            return;
        }
        System.out.println(officialTime());
        car.movePlayer();
        chaser.movePlayerChaser(car);
        for (Block block : blocks) {
            if (car.intersects(block)) {
                explosions.add(new Explosion((int)car.getX()-3,(int)car.getY()-6));
                car.getHit(block);
                if (car.dead()) {
                    endGame();
                    JOptionPane.showMessageDialog(frame, "You crashed.");
                }
            }
        }
        for(int i=0; i<explosions.size(); i++){ //check to see if any explosions need to be removed
            if(explosions.get(i).over())
                explosions.remove(i);
        }
        for (Coin coin : coins) {
            if (car.intersects(coin)) {
                coin.setAvailable(false);
            }
        }
        
        if (coins.size() < 1) {
            JOptionPane.showMessageDialog(frame, "You win!");
            endGame();
        }
        for (int i = 0; i < coins.size(); i++) {    //go through all coins
            if (!coins.get(i).isAvailable()) //if the selected coin is not available
            {
                coins.remove(i);                    //remove that coin from the list
            }
        }
        for (EnvCoin envcoin : envcoins) {
            if(car.intersects(envcoin)){
                if(envcoin.available){
                    envcoin.setAvailable(false);
                    car.setEnvtime(150);
                    
                }
            }
        }
        
        //end your code for timer tick code
        display.repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (!running) {
            return;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            car.setUp(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            car.setDown(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            car.setLeft(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            car.setRight(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
        if (!running && justEnded == 0) {
            startGame();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            car.setUp(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            car.setDown(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            car.setLeft(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            car.setRight(false);
        }
    }

    class DisplayPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //draw your graphics here
         //   g.drawImage(grassImage, 0, 0, frame.getWidth(), frame.getHeight(), null);
            car.draw(g);
            chaser.draw(g);
            for (Block block : blocks) {
                block.draw(g);
            }
            for (Coin coin : coins) {
                coin.draw(g);
            }
            for (Explosion explosion : explosions) {
                explosion.draw(g);
            }
            for (EnvCoin envcoin : envcoins) {
                if(envcoin.isAvailable())
                envcoin.draw(g);}
            g.setColor(Color.white);
            g.fillRect(0, 0, 60, 15);
            g.setColor(Color.black);
            g.drawString(officialTime(), 2, 13);
            
            if (!running) {
                g.drawImage(titleScreen, 0, 0, frame.getWidth(), frame.getHeight(), null);
            }
            
        }
    }

    public String officialTime() {
        long now = System.currentTimeMillis();
        long difference = now - startTime;
        long seconds = difference / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;
        long millis = difference % 1000;
        return String.format("%02d:%02d:%03d", minutes, seconds, millis);
    }
}
