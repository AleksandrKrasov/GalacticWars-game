/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import Bubble.Audio.Audio;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;

/**
 *
 * @author Саня
 */
public class Player implements UpdateDraw{
    
    //Fields
    private double x;
    private double y;
    private double w;
    private double h;
    
    public static boolean up;
    public static boolean down;
    public static boolean left;
    public static boolean right;
    private int speed;
    
    private double dx; //Move coef
    private double dy;
    public static boolean isFiring; 
    private double health;
    private int magazine;
    
    private double angl;
    private double distX;
    private double distY;  private double dist;
    
    private ImageIcon imgIcn = new ImageIcon("res/player.png");
    private Image img;
    private double money;
    private int armor;
    
    //TODO change
    Audio a_shot;

    public Player() {
        x = GamePanel.WIDTH / 2;
        y = GamePanel.HEIGHT / 2;
        w = imgIcn.getIconWidth();
        h = imgIcn.getIconHeight();
        
        img = imgIcn.getImage();
        
        up = false;
        down = false;
        left = false;
        right = false;
        
        //real health is health divided by 2
        health = 6;
        //end
        speed = 5;
        magazine = 30;
        dx = 0;
        dy = 0;
        
        isFiring = false;
        money = 0;
        
        a_shot = new Audio("res/audio/shot.WAV");
        armor = 0;
    }
    
    
    
    //Function
    public Rectangle getRect(){
        return new Rectangle((int)x, (int)y, (int)w, (int)h);
    }
    
    @Override
    public void update(){
        
        //for rotating
        distX = GamePanel.mouseX - x;
        distY = y - GamePanel.mouseY;
        dist = (Math.sqrt(distX * distX + distY * distY));
        
        if(distX > 0) angl = Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
        if(distX < 0) angl =- Math.acos(distY / (Math.sqrt(distX * distX + distY * distY)));
        //end
        
        if(up &&  y > 20)
            y -= speed;
        if(down && y < GamePanel.HEIGHT - h)
            y += speed;
        if(left && x > 0)
            x -= speed;
        if(right && x < GamePanel.WIDTH - w)
            x += speed;
        if(up && left || up && right || down && left || down && right){
            double angle = Math.toRadians(45);
            dy = dy * Math.sin(angle);
            dx = dx * Math.cos(angle);
        }
        
        y += dy;
        x += dx;
        
        dy = 0;
        dx = 0;
        
        if(magazine <=0){
            magazine = 0;
            isFiring = false;
        }
        
        if(isFiring){
            GamePanel.bullets.add(new Bullet());
            a_shot.play();
            isFiring = false;
            magazine --;
        } 
    }
    
    @Override
    public void draw(Graphics2D g){
        
        //rotate
        AffineTransform origXForm;
        origXForm = g.getTransform();
        AffineTransform newXForm = (AffineTransform)(origXForm.clone());
        newXForm.rotate(angl, x + 29, y + 25);
        g.setTransform(newXForm);
        g.drawImage(img, (int)x, (int)y, null);
        g.setTransform(origXForm);
    }
    
    public void addMoney(){
        money += 10;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getHealth() {
        return health;
    }

    public int getMagazine() {
        return magazine;
    }

    public void setMagazine(int magazine) {
        this.magazine = magazine;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setHealth(double health) {
        this.health = health;
    }
    
    public void hit() {
        if(armor > 0)
            //real is 20 when colaps
            armor -= 20 / 2;
        else
            health --;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public static void setUp(boolean up) {
        Player.up = up;
    }

    public static void setDown(boolean down) {
        Player.down = down;
    }

    public static void setLeft(boolean left) {
        Player.left = left;
    }

    public static void setRight(boolean right) {
        Player.right = right;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }
}
