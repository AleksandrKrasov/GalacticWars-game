/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.*;

/**
 *
 * @author Саня
 */
class GameBackground implements UpdateDraw{
    
    //Fields 
    Image img1 = new ImageIcon("res/background2.jpg").getImage();
    Image img2 = new ImageIcon("res/background1.jpg").getImage();
    Image img3 = new ImageIcon("res/storeBackground.jpg").getImage().getScaledInstance(
            GamePanel.WIDTH, GamePanel.HEIGHT, 0);

    public GameBackground() {
    }
    
    //Functions
    @Override
    public void update(){
        
    }
    
    @Override
    public void draw(Graphics2D g){
        
        g.drawImage(img2, (int)0, (int)0, null);
        
        if(GamePanel.state == GamePanel.STATES.STORE){
            g.drawImage(img3, (int)0, (int)0, null);
        }
        if(GamePanel.state == GamePanel.STATES.PLAY){
            g.drawImage(img1, (int)0, (int)0, null);
        }
        if(GamePanel.state == GamePanel.STATES.PLAY || GamePanel.state == GamePanel.STATES.STORE){
            //Background of indicators
            Color newColor = new Color(0, 200, 250);
            g.setColor(newColor);
            g.fillRect(0, 0, GamePanel.WIDTH, 20);
            g.setColor(Color.WHITE);
            Font font = new Font("Arial", Font.ITALIC, 20);
            g.setFont(font);
            
            //real health is health divide by 2
            
            ((Graphics2D)g).drawString("Здоровье: " + (int)GamePanel.player.getHealth() /2 , 10, 15);
            ((Graphics2D)g).drawString("| Патроны: " + GamePanel.player.getMagazine(), 130, 15);
            ((Graphics2D)g).drawString("| Враги: " + GamePanel.enemies.size(), 280, 15);
            ((Graphics2D)g).drawString("| броня: " + GamePanel.player.getArmor(), 380, 15);
            ((Graphics2D)g).drawString("| $: " + GamePanel.player.getMoney(), 485, 15);
            
        }
    }
}
