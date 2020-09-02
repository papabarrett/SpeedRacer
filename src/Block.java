
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
public class Block extends Rectangle {

    public Block(int x1, int y1) {
        super(x1, y1, 50, 50);
    }
    
    public void draw(Graphics g){
        g.setColor(new Color(118,10,18));
        g.fillRect(x, y, width, height);
        //end basic, optional after
        g.setColor(Color.white);
        g.drawRect(x,y,width/2,height/2);
        g.drawRect(x+width/2, y, width/2, height/2);
        g.drawRect(x, y+height/2, width, height/2);
    }
    
}
