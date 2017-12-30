/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

/**
 *
 * @author Саня
 */
public class Enemy implements UpdateDraw{
    //Fields
    private double x;
    private double y;
    private double h;
    private double w;
    
    private double speed;
    private double dx;
    private double dy;
    private double health;
    
    private ImageIcon imgIcn;
    private Image img;
    
    //Constructor

    public Enemy(int type, int rank) {
        
       imgIcn = new ImageIcon("res/enemy.png");
       img = imgIcn.getImage();
        
        switch(type){
            case (1):
                switch(rank){
                    case (1):
                        x = Math.random() * GamePanel.WIDTH;
                        y = 0;
                        w = imgIcn.getIconWidth();
                        h = imgIcn.getIconHeight();
                        speed = 3;
                        health = 2;
                    
                        double angle = Math.toRadians(Math.random()*360);
                        dx = Math.sin(angle) * speed;
                        dy = Math.cos(angle) * speed;
                }
               
        }
    }

    //Functions
    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, (int)w, (int)h);
    }
    
    @Override
    public void update() {
        x += dx;
        y += dy;
        
        if(x < -100 && dx < 0) dx = -dx;
        if(x > GamePanel.WIDTH +100 && dx > 0) dx = -dx;
        if(y < -150 && dy < 0) dy = -dy;
        if(y > GamePanel.HEIGHT +150&& dy > 0) dy = -dy;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(img, (int)x, (int)y, null);
    }
    
    public void hit(){
        health--;
    }
    
    public boolean remove(){
        if(health <= 0)
            return true;
        return false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getH() {
        return h;
    }

    public double getW() {
        return w;
    }

    public double getHealth() {
        return health;
    }
}
