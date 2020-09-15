
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jbarrett
 */
public class Coin extends Rectangle{
    int value;
    boolean available;
    Image coin=new ImageIcon("coin.png").getImage();

    public Coin(int x1, int y1) {
        super(x1, y1, 8, 20);
        value = 1;
        available = true;
    }
    
    public void draw(Graphics g){
        g.drawImage(coin, x, y,width,height,null);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    
}
