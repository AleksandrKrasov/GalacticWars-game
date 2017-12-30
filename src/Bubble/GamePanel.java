/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import Bubble.Audio.Audio;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Саня
 */
public class GamePanel extends JPanel implements Runnable{
    
    //Fields
    public static int WIDTH = 1024;
    public static int HEIGHT = 680;
    private Thread thread;
    private BufferedImage image;
    private Graphics2D g;
    public static GameBackground background;
    public static Player player;
    public static ArrayList<Bullet> bullets;
    public static ArrayList<Enemy> enemies;
    public static Wave wave;
    private int FPS;
    private double millisToFPS;
    private long timerFPS;
    private int sleepTime;
    public enum STATES{
        MENUE,
        PLAY,
        STORE,
        RESTART,
        EXIT
    }
    
    public static STATES state = STATES.MENUE;
    public static ArrayList<Menue> menue;
    public static double mouseX;
    public static double mouseY;
    public static boolean leftMouse;
    public static Aim aim1;
    public static Aim aim;
    private Audio a_boom;
    private Audio a_crash;
    public static Store store;
    
    //Constructor
    public GamePanel() {
        super();
        //Set the dimenesion of a frame
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(true);
        requestFocus();
        
        addKeyListener(new Listeners());
        addMouseListener(new Listeners());
        addMouseMotionListener(new Listeners());
    }
    
    public void start(){
        thread = new Thread(this);
        thread.start();
    }
    
    //Functions
    @Override
    public void run() {
        
        FPS = 30;
        millisToFPS = 1000 / FPS;
        sleepTime = 0;
        
        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB); //TYPE_INT_RGB == 1;
        g = (Graphics2D) image.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        
        background = new GameBackground();
        player = new Player();
        bullets = new ArrayList<>();
        enemies = new ArrayList<>();
        wave = new Wave();
        leftMouse = false;
        aim1 = new Aim(mouseX, mouseY, 72, 76, 0, 0, "res/aim1.png");
        aim  = new Aim(GamePanel.mouseX, GamePanel.mouseY, 4, 4, 27, 12, "res/aim.png");
        
        a_boom = new Audio("res/audio/boom.WAV");
        a_crash = new Audio("res/audio/crash.WAV");
        
        menue = new ArrayList<>();
        menue.add(new Menue(160, 0, 30, STATES.PLAY, "Играть", true));
        menue.add(new Menue(260, 0, 28, STATES.RESTART, "Рестарт", false));
        menue.add(new Menue(360, 0, 28, STATES.STORE, "Магазин", false));
        menue.add(new Menue(460, 0, 28, STATES.EXIT, "Выйти", true));
        
        store = new Store();
        
        //Cursor unvisible
        Toolkit kit = Toolkit.getDefaultToolkit();
        Cursor myCursor = kit.createCustomCursor(kit.getImage(""), new Point(0, 0), "myCursor");
        //end

        while(true){
            //System.out.println(menue.get(1).isActiveButton());
            
            timerFPS = System.nanoTime();
            
            if(state.equals(STATES.MENUE)){
                setCursor(Cursor.getDefaultCursor());
                background.update();
                background.draw(g);
                for(Menue m : menue){
                m.update();
                m.draw(g);
                }
                gameDraw();
            }
            if(state.equals(STATES.PLAY)){
                setCursor(myCursor);
                menue.get(1).setActiveButton(true);
                menue.get(2).setActiveButton(true);
                gameUpdate();
                gameRender();
                gameDraw();
            }
            if(state.equals(STATES.RESTART)){
                wave.setStaringCondition();
                state = STATES.PLAY;
            }
            if(state.equals(STATES.STORE)){
                background.update();
                background.draw(g);
                store.update();
                store.draw(g);
                gameDraw();
            }
            if(state.equals(STATES.EXIT)){
                System.exit(0);
            }
            
            timerFPS = System.nanoTime();
            
            
            //Set FPS
            timerFPS = (System.nanoTime() - timerFPS) / 1000000;
            if(millisToFPS > timerFPS){
                sleepTime = (int)(millisToFPS - timerFPS);
            }else sleepTime = 1;
            
            try{
                thread.sleep(30);//sleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            timerFPS = 0;
            sleepTime = 1;
        }
    }
    
    public void gameUpdate(){
        //Background update
        background.update();
        
        //Pleayer update
        player.update();
        
        //Aim1 update
        aim1.update();
        
        //Aim update
        aim.update();
        
        //What to do if player died
        if((int)player.getHealth() /2 <= 0){
            JOptionPane.showMessageDialog(null, "ВЫ ПОГИБЛИ!!!");
            state = STATES.MENUE;
            wave.setStaringCondition();
            }
        
        //Bullets update
        for(int i=0; i<bullets.size(); i++){
            bullets.get(i).update();
            bullets.get(i).remove();
            boolean remove = bullets.get(i).remove();
            if(remove){
                bullets.remove(i);
                i--;
            }
        }
        
        //Enemies update
        for(int i=0; i<enemies.size(); i++)
            enemies.get(i).update();
        
        //Bullets - enemies collide and deleting bullet if it reaches aim while is_capure is true
        for(int i=0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);
            double ex = e.getX();
            double ey = e.getY();
            double ew = e.getW();
            double eh = e.getH();
            
            for(int j=0; j<bullets.size(); j++){
                Bullet b = bullets.get(j);
                double bx = b.getX();
                double by = b.getY();
                double bw = b.getW();
                double bh = b.getH();
                
                if((bx > ex - bw) && (bx < ex + ew) && (by > ey - bh) && (by < ey + eh)){
                    e.hit();
                    a_boom.play();
                    bullets.remove(j);
                    j--;
                    boolean remove = e.remove();
                    if(remove){
                        enemies.remove(i);
                        player.addMoney();
                        i--;
                        break;
                    }
                }
                
                //deleting bullet if it reaches aim while is_capure is true
                double ax = GamePanel.aim.getX();
                double ay = GamePanel.aim.getY();
                double ah = GamePanel.aim.getH();
                double aw = GamePanel.aim.getW();
                
                if((bx > ax - bw) && (bx < ax + aw) && (by > ay - bh) && (by < ay + ah) && bullets.size() >= 0){
                    bullets.remove(j); //TODO fix an exception
                    break;
                }
            }
        }
        
        //Player - enemy collides
        for(Enemy e : enemies){
             if(player.getRect().intersects(e.getRect())){
                e.hit();
                player.hit();
                a_crash.play();
                //Delete enemy if it health <= 0
                boolean remove = e.remove();
                if(remove){
                    enemies.remove(e);
                    player.addMoney();
                    break;
                }
            }
        }
        //Wave update
        wave.update();
    }
    
    public void gameRender(){
        //Background draw
        background.draw(g);
        
        //Player draw
        player.draw(g);
        
        //Aim1 draw
        aim1.draw(g);
        
        //Aim1 draw
        aim.draw(g);
        
        //Bullet draw
        for(int i=0; i<bullets.size(); i++)
            bullets.get(i).draw(g);
        
        //Enemies draw
        for(int i=0; i<enemies.size(); i++)
            enemies.get(i).draw(g);
        
        //Wave draw
        if(wave.showWave())
            wave.draw(g);
    }
    
    private void gameDraw(){
        //Connect Graphic drawing with Background
        Graphics g2 = this.getGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.dispose(); 
    }
}
