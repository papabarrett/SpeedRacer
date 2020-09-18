
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

    int envtime;
    Color color;
    double angle, xPos, yPos, velocity;
    boolean up, down, left, right;
    final double MAX_SPEED = 10;
    final double ANGULAR_VELOCITY = 3, CAR_ACCEL = .25;
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
        envtime = 0;
    }

    public int getEnvtime() {
        return envtime;
    }

    public void setEnvtime(int envtime) {
        this.envtime = envtime;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public void movePlayerChaser(Car player) {
        velocity += Math.random() * .4;
        if (velocity > 0) {
            velocity -= CAR_ACCEL / 4;
        }
        double xDiff = player.getxPos() - xPos;
        double yDiff = player.getyPos() - yPos;
        double hypot = Math.hypot(xDiff, yDiff);
        double angleTarget = -Math.atan(yDiff / xDiff) * 360 / Math.PI / 2;
        if (xDiff < 0) {
            angleTarget += 180;
        }
        angleTarget = Math.round(angleTarget);
        angleTarget += 360;
        angleTarget %= 360;
        angle += 360;
        angle %= 360;
        System.out.println(angleTarget);
        if (Math.abs(angle - angleTarget) > 2) {
            if (angle < angleTarget) {
                if (angleTarget - angle < 180) {
                    angle += ANGULAR_VELOCITY;
                } else {
                    angle -= ANGULAR_VELOCITY;
                }
            } else if (angle > angleTarget) {
                if (angle - angleTarget < 180) {
                    angle -= ANGULAR_VELOCITY;
                } else {
                    angle += ANGULAR_VELOCITY;
                }

            }
        }

        double xMove=hypot/10 * Math.cos(-angle / 360.0 * Math.PI * 2);
        if(xDiff<0)
            xMove=-Math.abs(xMove);
        double yMove=hypot/10 * Math.sin(-angle / 360.0 * Math.PI * 2);
        if(yDiff<0)
            yMove=-Math.abs(yMove);
        xPos += xMove;
         yPos += yMove;
//        if(xDiff<0)
//            xPos+=1;
//        if(xDiff>0)
//            xPos-=1;
//        if(yDiff<0)
//            yPos+=1;
//        if(yDiff>0)
//            yPos-=1;
    }

    public void movePlayer() {
        if (velocity > 0) {
            velocity -= CAR_ACCEL / 4;
        }
        if (up) {
            velocity += CAR_ACCEL;
        }
        if (down) {
            velocity -= CAR_ACCEL / 2;
        }
        if (velocity < -MAX_SPEED) {
            velocity = -MAX_SPEED;
        }
        if (right) {
            angle -= ANGULAR_VELOCITY;
        }
        if (left) {
            angle += ANGULAR_VELOCITY;
        }
        angle += 360;
        angle %= 360;
        velocity = Math.min(velocity, MAX_SPEED);
        xPos += velocity * Math.cos(-angle / 360.0 * Math.PI * 2);
        yPos += velocity * Math.sin(-angle / 360.0 * Math.PI * 2);
        envtime--;
        if (envtime < 1) {
            envtime = 0;
        }

    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform t = g2.getTransform();
        x = (int) Math.round(xPos - width / 2);
        y = (int) Math.round(yPos - height / 2);
        g2.rotate(-angle / 360.0 * Math.PI * 2, xPos, yPos);
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
        if (envtime > 0) {
            g2.setColor(Color.CYAN);
        }
        g2.fillRect(x, y, (int) (width * 1.0 * healthPoints / MAX_HEALTH_POINTS), 3);
        double distanceX = 20 * Math.cos(-angle / 360.0 * Math.PI * 2);
        double distanceY = 20 * Math.sin(-angle / 360.0 * Math.PI * 2);
        g.setColor(Color.BLACK);
        g.drawLine(x, y, (int) (x + distanceX), (int) (y + distanceY));
        g.drawString("" + angle, x, y);
    }

    public void getHit(Block block) {
        System.out.println("xPos " + xPos + " Velocity:" + velocity);
        if (envtime < 1) {
            healthPoints--;
        }
        velocity = -velocity;
        System.out.println("Swapped Velocity:" + velocity);
        while (this.intersects(block)) {
            xPos += velocity * Math.cos(-angle / 360.0 * Math.PI * 2);
            yPos += velocity * Math.sin(-angle / 360.0 * Math.PI * 2);
            x = (int) Math.round(xPos - width / 2);
            y = (int) Math.round(yPos - height / 2);
        }
        xPos += velocity * Math.cos(-angle / 360.0 * Math.PI * 2);
        yPos += velocity * Math.sin(-angle / 360.0 * Math.PI * 2);
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
