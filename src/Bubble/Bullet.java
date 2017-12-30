/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

/**
 *
 * @author Саня
 */
public class Bullet implements UpdateDraw{
    
    //Fields
    private double x;
    private double y;
    private double w;
    private double h;

    private double speed;
    private double distX;
    private double distY;
    private double angl;
    private double dist;
    
    private ImageIcon imgIcn = new ImageIcon("res/bullet.png");
    private Image img;
    
    //Costructor

    public Bullet() {
        
        img = imgIcn.getImage();
        
        x = 29 + GamePanel.player.getX();
        y = 25 + GamePanel.player.getY();
        w = imgIcn.getIconWidth();
        h = imgIcn.getIconHeight();
        
        speed = 10;
        
        distX = GamePanel.aim.getX() - x;
        distY = y - GamePanel.aim.getY();
    }
    
    @Override
    public void update(){
        y = y - speed * distY / (Math.sqrt(distX * distX + distY * distY));
        x = x + speed * distX / (Math.sqrt(distX * distX + distY * distY));
        
        if(Listeners.is_capture){
            distX = GamePanel.aim.getX() - x;
            distY = y - GamePanel.aim.getY();
            dist = (Math.sqrt(distX * distX + distY * distY));
            if (distX > 0) 
                angl = Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
            if (distX < 0) 
                angl = -Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
        }
        else{
            if(distX > 0) 
                angl = Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
            if(distX < 0) 
                angl = - Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
        }
    }
    
    @Override
    public void draw(Graphics2D g){//g.drawImage(img, (int)x, (int)y, null);
        AffineTransform origXForm;
        origXForm = g.getTransform();
        AffineTransform newXForm = (AffineTransform)(origXForm.clone());
        newXForm.rotate(angl, x, y);
        g.setTransform(newXForm);
        g.drawImage(img, (int)x, (int)y, null);
        g.setTransform(origXForm);
    }
    
    public boolean remove(){
        if(y < 0 )
            return true;
        return false;
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
}
