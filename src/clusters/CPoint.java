/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author anton
 */
public class CPoint {
    
    int x, y;
    Color color;
    CPoint(int toX, int toY, Color toColor) {
        
        x = toX;
        y = toY;
        color = new Color(toColor.getRed(), toColor.getGreen(), toColor.getBlue());
        
    }
    
}
