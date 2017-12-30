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
public class Aim implements UpdateDraw{
    
    //Fields
    private double x;
    private double y;
    private double w;
    private double h;
    private double dx;
    private double dy;
    private String img;
    
    
    //Constructor

    public Aim(double x, double y, double w, double h, double dx, double dy, String img) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.dx = dx;
        this.dy = dy;
        this.img = img;
    }
    
    
    //Functions
    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, (int)w, (int)h);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    @Override
    public void update() {
        
        //find out if isIs_capture is true and capture the aim at enemy
        if(Listeners.is_capture){
            //dx = 1233;
            //dy = 232;
            System.out.println(GamePanel.aim.dx + " " + GamePanel.aim.dy);
            for(int i=0; i<GamePanel.enemies.size(); i++){
                Enemy e = GamePanel.enemies.get(i);
                double ex = e.getX();
                double ey = e.getY();
                double eh = e.getH();
                double ew = e.getW(); 
                //find central aim
                double ax = GamePanel.aim.getX();
                double ay = GamePanel.aim.getY();
                double ah = GamePanel.aim.getH();
                double aw = GamePanel.aim.getW();
                
                if ((ax > ex - aw) && (ax < ex + ew) && (ay > ey - ah) && (ay < ey + eh)) {
                    this.x = ex - 17.5;
                    this.y = ey - 6;
                    GamePanel.aim.x =  ex + ew / 2 ;
                    GamePanel.aim.y =  ey + eh / 2 ; 
                }
            }
        }
        else{
            GamePanel.aim.dx = 27;
            GamePanel.aim.dy = 12;
            this.x = GamePanel.mouseX + dx;
            this.y = GamePanel.mouseY + dy;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        //change aim1 if is_capture is true
        if(Listeners.is_capture){
            GamePanel.aim1.img = "res/aim2.png";
            GamePanel.aim.img = "";
        }
        else{
            GamePanel.aim1.img = "res/aim1.png";
            GamePanel.aim.img = "res/aim.png";
        }
        
        Image in = new ImageIcon(img).getImage();
        g.drawImage(in, (int)this.x, (int)this.y, null);
    }
}
