/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Bubble;

import java.awt.Graphics2D;

/**
 *
 * @author Саня
 */
public interface UpdateDraw {
    
    void update();
    
    /**
     *
     * @param g
     */
    void draw(Graphics2D g);
}
