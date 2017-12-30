/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Саня
 */
public class Store implements UpdateDraw{
    
    //Fields
    private ArrayList<Shape> shapes;
    private enum STATES{
        BULLET,
        ARMOR1,
        ARMOR2,
        ARMOR3
    }
    private String desription1;
    private String desription2;
    private String desription3;
    private Menue button;
    

    //Constructor
    public Store() {
        desription1 = "РАКЕТЫ\nЦена: 10 $\nКоличесвто: 10 штук";
        desription2 = "Броня\nЦена: 15 $\nУвеличивает броню до 20";
        desription3 = "Броня\nЦена: 30 $\nУвеличивает броню до 50";
        shapes = new ArrayList<>();
        shapes.add(new Shape("res/rocket_for_store.png", 150, 230, 200, 50,
        desription1, 10, STATES.BULLET));
        shapes.add(new Shape("res/armor2.png", 460, 270, 100, 80, desription2, 15, 
                STATES.ARMOR1));
        shapes.add(new Shape("res/armor1.png", 580, 270, 100, 85, desription3, 30, 
                STATES.ARMOR2));
        button = new Menue(600, 0, 30, GamePanel.STATES.MENUE, "Меню", true);
    }
    
    
    //Methods
    @Override
    public void update() {
        for(Shape s: shapes){
            s.update();
            if(GamePanel.mouseX > s.x && 
                GamePanel.mouseX < s.x  + s.w &&
                GamePanel.mouseY > s.y    && 
                GamePanel.mouseY < s.y + s.h){
                    s.activeShape = true;
            }
            else{
                s.activeShape = false;
            }
            if(s.activeShape && GamePanel.leftMouse && GamePanel.player.getMoney() >= s.price &&
                    s.st == STATES.BULLET){
                System.out.println("s");
                GamePanel.player.setMagazine(GamePanel.player.getMagazine() + 10);
                GamePanel.player.setMoney(GamePanel.player.getMoney() - s.price);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(s.activeShape && GamePanel.leftMouse && GamePanel.player.getMoney() >= s.price &&
                    s.st == STATES.ARMOR1 && GamePanel.player.getArmor() < 20){
                System.out.println("s");
                GamePanel.player.setArmor(20);
                GamePanel.player.setMoney(GamePanel.player.getMoney() - s.price);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if(s.activeShape && GamePanel.leftMouse && GamePanel.player.getMoney() >= s.price &&
                    s.st == STATES.ARMOR2 && GamePanel.player.getArmor() < 50){
                System.out.println("s");
                GamePanel.player.setArmor(60);
                GamePanel.player.setMoney(GamePanel.player.getMoney() - s.price);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Store.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        button.update();
    }

    @Override
    public void draw(Graphics2D g) {
        for(Shape s: shapes){
            s.draw(g);
        }
        button.draw(g);
    }
    
    private class Shape implements UpdateDraw{
        
        //Fields
        private double x;
        private double y;
        private double w;
        private double h;
        private String root;
        private Image img;
        private boolean activeShape;
        private String description;
        private double price;
        public STATES st;
        private int buttonWidth;
        private int buttonHigh;
        private String[] textLine;
        
        
        //Constructor
        public Shape(String root, double x, double y, int w, int h, 
                String description, double price, STATES st) {
            
            this.root = root;
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.activeShape = false;
            this.description = description;
            this.price = price;
            this.st = st;
            this.buttonWidth = 265;
            this.buttonHigh = 70;
            this.textLine = new String[3];
            
            
            
            img = new ImageIcon(root).getImage().getScaledInstance((int)this.w,
                    (int)this.h, 0);
            
        }
        
        //Methods
        @Override
        public void update() {
            textLine = this.description.split("\n");
        }

        @Override
        public void draw(Graphics2D g) {
            
            g.drawImage(img, (int)x, (int)y, null);
            
            if(activeShape){
                g.setColor(Color.BLACK);
                g.setStroke(new BasicStroke(3));
                g.drawRect((int)(x + w / 2) - buttonWidth / 2, (int)(y - h - 40), buttonWidth, buttonHigh); 
                g.setColor(new Color(255, 255, 255, 0)); 
                g.setStroke(new BasicStroke(1));
        
                g.setColor(Color.cyan);
                g.setFont(new Font("Consolas", Font.BOLD, 20)); 
                long lenghtWord1 = (int) g.getFontMetrics().getStringBounds(textLine[0], g).getWidth();
                g.drawString(textLine[0], (int)(x + w / 2)  - lenghtWord1 /2,
                (int)(y - h - 20) ); 
                g.drawString(textLine[1], (int)(x + w / 2) - buttonWidth / 2 + 5,
                (int)(y - h ) ); 
                g.drawString(textLine[2], (int)(x + w / 2) - buttonWidth / 2 + 5,
                (int)(y - h + 20) ); 
            }
        }
    }
}
