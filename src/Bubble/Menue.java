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


/**
 *
 * @author Саня
 */
public class Menue implements UpdateDraw{
    
    //Fields
    private int buttonWidth;
    private int buttonHeight;
    private int y;
    private int dx;
    private Color color1;
    private int transp = 0;
    private boolean activeButton;
    private String text;
    private int fontSize;
    private GamePanel.STATES st;
    
    
    //Constructor
    public Menue(int y, int dx, int fontSize, GamePanel.STATES st, String text, boolean activeButton) {
        buttonWidth = 120;
        buttonHeight = 60;
        
        color1 = Color.yellow;
        this.y = y;
        this.dx = dx;
        this.fontSize = fontSize;
        this.st = st;
        this.text = text;
        this.activeButton = activeButton;
    }    

    //Funtions
    @Override
    public void update() {
        
        if(GamePanel.mouseX > GamePanel.WIDTH / 2 - buttonWidth / 2&& 
                GamePanel.mouseX < GamePanel.WIDTH/ 2 + buttonWidth / 2 &&
                GamePanel.mouseY > y    && 
                GamePanel.mouseY < y + buttonHeight &&  activeButton){
                transp = 60;
            if(GamePanel.leftMouse){
                GamePanel.state = st;
            }
        }else{
            transp = 0;
        }
    }

    @Override
    public void draw(Graphics2D g) {
        
        g.setColor(color1);
        g.setStroke(new BasicStroke(3));
        g.drawRect(GamePanel.WIDTH / 2 - buttonWidth / 2, y, buttonWidth, buttonHeight); 
        g.setColor(new Color(255, 255, 255, transp));
        g.fillRect(GamePanel.WIDTH / 2 - buttonWidth / 2, y, buttonWidth, buttonHeight); 
        g.setStroke(new BasicStroke(1));
        
        g.setColor(color1);
        g.setFont(new Font("Consolas", Font.BOLD, fontSize)); 
        long lenghtWord = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        g.drawString(text, (int)(GamePanel.WIDTH / 2 - lenghtWord /2 - dx),
                (int)(y + buttonHeight / 2 + 10)); 
    }

    public void setActiveButton(boolean activeButton) {
        this.activeButton = activeButton;
    }

    public boolean isActiveButton() {
        return activeButton;
    }

    public String getText() {
        return text;
    }
}
