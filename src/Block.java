
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
public class Block extends Rectangle {
    
    Image block=new ImageIcon("walls.png").getImage();

    public Block(int x1, int y1) {
        super(x1, y1, 50, 50);
    }
    
    public void draw(Graphics g){
        g.setColor(new Color(118,10,18));
        g.drawImage(block, x, y, width, height, null);
    }
    
}
