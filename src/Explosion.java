
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jbarrett
 */
public class Explosion extends Rectangle{

    int life;
    final int EXPO_TIME=15;
    public Explosion(int x1, int y1) {
        super(x1, y1, 20, 20);
        life=EXPO_TIME;
    }
    public void draw(Graphics g){
        life--;
        g.setColor(Color.red);
        g.fillRect(x, y, width, height);
    }
    public boolean over(){
        return life<1;
    }
    
    
}
