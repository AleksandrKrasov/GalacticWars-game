/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import Bubble.Audio.Audio;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

/**
 *
 * @author Саня
 */
//Timer for wave and creaton enemies
public class Wave implements UpdateDraw{
    
    //Fields
    public static int waveNumber;
    private long waveTimer;
    private long waveDelay;
    private long waveTimeDiff;
    private String waveText;
    private int waveMultiplayer;
    
    private Audio a_siren;
    
    
    //Constructor

    public Wave() {
        waveNumber = 1;
        waveTimer = 0;
        waveDelay = 5000;
        waveTimeDiff = 0;
        waveText = "В О Л Н А -";
        waveMultiplayer = 5;
        
        a_siren = new Audio("res/audio/siren.WAV");
    }
    
    
    //Functions
    @Override
    public void update() {
        if(GamePanel.enemies.size() == 0 && waveTimer == 0)
            waveTimer = System.nanoTime();
        if(waveTimer > 0){
            waveTimeDiff += (System.nanoTime() - waveTimer) / 1000000;
            waveTimer = System.nanoTime();
        }
        if(waveTimeDiff > waveDelay){
            createEnemies();
            waveTimer = 0;
            waveTimeDiff = 0;
        }
        
        //TODO Delete
        // Stope game and go to Menue 
        if(Wave.waveNumber > 4){
            GamePanel.state = GamePanel.STATES.MENUE;
            setStaringCondition();
        }
    }

    @Override
    public void draw(Graphics2D g) {
        //Set transparency of the message
        double divider = waveDelay / 180;
        double alpha = waveTimeDiff / divider;
        alpha = 255 * Math.sin(Math.toRadians(alpha));
        if(alpha < 0)
            alpha = 0;
        if(alpha > 255)
            alpha = 255;
        g.setFont(new Font("consolas", Font.PLAIN, 20));
        g.setColor(new Color(255, 255, 255, (int)alpha));
        
        String s = " - " + waveNumber + "-ая " + waveText;
        long length = (int)g.getFontMetrics().getStringBounds(s, g).getWidth();
        g.drawString(s, GamePanel.WIDTH / 2 - (int)length /2, GamePanel.HEIGHT / 2 - (int)length /2);
        //a_siren.play();
    }
    
    public void createEnemies(){
        int enemyCounter = waveNumber * waveMultiplayer;
        if(waveNumber < 4){
            while(enemyCounter > 0){
                int type = 1;
                int rank = 1;
                GamePanel.enemies.add(new Enemy(type, rank));
                enemyCounter -= type * rank;
            } 
        }
        waveNumber++;
        //ADD bullets to player's 
        if(waveNumber > 2){
            int currentMagazine = GamePanel.player.getMagazine();
            GamePanel.player.setMagazine(currentMagazine + 10 * waveNumber);
        }
    }
    
    public boolean showWave(){
        if(waveTimer != 0)
            return true;
        return false;
    }

    public static void setWaveNumber(int waveNumber) {
        Wave.waveNumber = waveNumber;
    }
    
    public void setStaringCondition(){
        GamePanel.player.setMagazine(30);
        GamePanel.player.setHealth(6);
        GamePanel.enemies.removeAll(GamePanel.enemies);
        GamePanel.bullets.removeAll(GamePanel.bullets);
        setWaveNumber(1);
        GamePanel.player.setX(GamePanel.WIDTH / 2);
        GamePanel.player.setY(GamePanel.HEIGHT / 2);
        Player.setDown(false);
        Player.setUp(false);
        Player.setLeft(false);
        Player.setRight(false);
        GamePanel.menue.get(1).setActiveButton(false);
        GamePanel.menue.get(2).setActiveButton(false);
        GamePanel.player.setMoney(0);
    }
}
