/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Саня
 */
public class Listeners implements KeyListener, MouseListener, MouseMotionListener{

    private boolean isFiring_on;
    public static boolean is_capture;
    
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_W)
            Player.up = true;
        if(key == KeyEvent.VK_S)
            Player.down = true;  
        if(key == KeyEvent.VK_A)
            Player.left = true;  
        if(key == KeyEvent.VK_D)
            Player.right = true;  
        if(key == KeyEvent.VK_SPACE){
            if(isFiring_on){
                Player.isFiring = true;
                isFiring_on = false;
            }
        }
        if(key == KeyEvent.VK_ESCAPE)
            GamePanel.state = GamePanel.STATES.MENUE;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if(key == KeyEvent.VK_W)
            Player.up = false;
        if(key == KeyEvent.VK_S)
            Player.down = false;  
        if(key == KeyEvent.VK_A)
            Player.left = false;  
        if(key == KeyEvent.VK_D)
            Player.right = false;
        if(key == KeyEvent.VK_SPACE){
            Player.isFiring = false;
            isFiring_on = true;
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            GamePanel.leftMouse = true;
            if(GamePanel.state.equals(GamePanel.STATES.PLAY)){
                GamePanel.player.isFiring = true;
                GamePanel.leftMouse = true;
            }
        }
        if(e.getButton() == MouseEvent.BUTTON3)
            is_capture = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1){
            GamePanel.player.isFiring = false;
            GamePanel.leftMouse = false;
        }
        //if(e.getButton() == MouseEvent.BUTTON3)
            //GamePanel.aim.setIs_capture(false);
        if(e.getButton() == MouseEvent.BUTTON3)
            is_capture = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        GamePanel.mouseX = e.getX();
        GamePanel.mouseY = e.getY();
    }
    
}
