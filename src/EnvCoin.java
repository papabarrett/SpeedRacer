
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
 * @author stu44100
 */
public class EnvCoin extends Rectangle{
    
    //Image block=new ImageIcon("do it").getImage();
    int life;
    final int ENV_TIME=150;
    boolean available;
    
    public EnvCoin(int x, int y) {
        super( x,  y,  8,  20);
        available = true;
        life=ENV_TIME;
    }
    public void draw(Graphics g){
        life--;
        g.setColor(new Color(44, 44, 44));
        g.fillRect(x, y, 8, 20);
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
    
    public boolean isAvailable() {
        return available;
    }
    
    
    public boolean over(){
        return life<1;
    }
}

