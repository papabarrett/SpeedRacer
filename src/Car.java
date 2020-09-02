
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

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
    final double ANGULAR_VELOCITY=.5, CAR_ACCEL=.25;

    public Car(Color color, double angle, int x1, int y1) {
        super(x1, y1, 30, 10);
        this.color = color;
        this.angle = angle;
        up = down = left = right = false;
        xPos = x + width / 2.0;
        yPos = y + height / 2.0;
        velocity = 0;
    }

    public void move() {
        velocity-=CAR_ACCEL/4;
        if (up) {
            velocity += CAR_ACCEL;
        }
        if (down) {
            velocity -= CAR_ACCEL/2;
            velocity = Math.max(velocity, -MAX_SPEED / 2);
        } else {
            velocity = Math.max(velocity, 0);
        }
        if(right)
            angle+=ANGULAR_VELOCITY;
        if(left)
            angle-=ANGULAR_VELOCITY;
        velocity = Math.min(velocity, MAX_SPEED);
        xPos += velocity * Math.cos(angle/360.0*Math.PI*2);
        yPos += velocity * Math.sin(angle/360.0*Math.PI*2);
    }

    public void draw(Graphics g) {
        Graphics2D g2=(Graphics2D) g;
        AffineTransform t=g2.getTransform();
        x = (int) Math.round(xPos - width / 2);
        y = (int) Math.round(yPos - height / 2);
        g2.rotate(angle/360.0*Math.PI*2, xPos, yPos);
        g.setColor(color);
        g.fillRect(x, y, width, height);
        g.setColor(Color.BLACK);
        g.drawString("angle " + angle, x, y);    
        g2.setTransform(t);
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
