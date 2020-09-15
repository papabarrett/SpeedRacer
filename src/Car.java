
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
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
public class Car extends Rectangle {

    Color color;
    double angle, xPos, yPos, velocity;
    boolean up, down, left, right;
    final double MAX_SPEED = 10;
    final double ANGULAR_VELOCITY = .5, CAR_ACCEL = .25;
    Image carImage;
    int healthPoints;
    final int MAX_HEALTH_POINTS;

    public Car(Color color, double angle, int x1, int y1, int h) {
        super(x1, y1, 30, 10);
        this.color = color;
        this.angle = angle;
        up = down = left = right = false;
        xPos = x + width / 2.0;
        yPos = y + height / 2.0;
        velocity = 0;
        carImage = new ImageIcon("greenCar.png").getImage();
        MAX_HEALTH_POINTS = h;
        healthPoints = h;
    }

    public void move() {
        if (velocity > 0) {
            velocity -= CAR_ACCEL / 4;
        }
        if (up) {
            velocity += CAR_ACCEL;
        }
        if (down) {
            velocity -= CAR_ACCEL / 2;
        }
        if( velocity < -MAX_SPEED)
            velocity=-MAX_SPEED;
        if (right) {
            angle += ANGULAR_VELOCITY;
        }
        if (left) {
            angle -= ANGULAR_VELOCITY;
        }
        velocity = Math.min(velocity, MAX_SPEED);
        xPos += velocity * Math.cos(angle / 360.0 * Math.PI * 2);
        yPos += velocity * Math.sin(angle / 360.0 * Math.PI * 2);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform t = g2.getTransform();
        x = (int) Math.round(xPos - width / 2);
        y = (int) Math.round(yPos - height / 2);
        g2.rotate(angle / 360.0 * Math.PI * 2, xPos, yPos);
        /*
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
         */
        g.drawImage(carImage, x, y, width, height, null);
        //g.drawString("angle " + angle, x, y);    
        g2.setTransform(t);
        g2.setColor(Color.red);
        g2.fillRect(x, y, width, 3);
        g2.setColor(Color.white);
        g2.fillRect(x, y, (int) (width * 1.0 * healthPoints / MAX_HEALTH_POINTS), 3);
    }

    public void getHit(Block block) {
        System.out.println("xPos " + xPos + " Velocity:" + velocity);
        healthPoints--;
        velocity = -velocity;
        System.out.println("Swapped Velocity:" + velocity);
        while (this.intersects(block)) {
            xPos += velocity * Math.cos(angle / 360.0 * Math.PI * 2);
            yPos += velocity * Math.sin(angle / 360.0 * Math.PI * 2);
            x = (int) Math.round(xPos - width / 2);
            y = (int) Math.round(yPos - height / 2);
        }
        xPos += velocity * Math.cos(angle / 360.0 * Math.PI * 2);
        yPos += velocity * Math.sin(angle / 360.0 * Math.PI * 2);
        up = false;
        down = false;
        System.out.println("xPos " + xPos + " Velocity:" + velocity);
    }

    public boolean dead() {
        return healthPoints < 1;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getxPos() {
        return xPos;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

}
