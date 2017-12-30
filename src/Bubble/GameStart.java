 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import javax.swing.JFrame;

/**
 *
 * @author Саня
 */
public class GameStart {
    
    public static void main(String[] args) {
        
        GamePanel panel = new GamePanel();
        JFrame startFrame = new JFrame("Galactic Wars");
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        startFrame.setContentPane(panel);
        startFrame.pack();
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
        panel.start();
    }
}
