/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clusters;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author anton
 */
public class ColorGetter {
    
    static public ArrayList<Color> allColors;
    
    public static void make() {
        
        allColors = new ArrayList<Color>();
        
        for (int ri = 0; ri < 256; ri += 25) {
            for (int gi = 0; gi < 256; gi += 25) {
                for (int bi = 0; bi < 256; bi += 25) {
                    allColors.add(new Color(ri, gi, bi));
                }
            }
        }
        
    }
    
    public static ArrayList<Color> getColors(int count) {
        
        ArrayList<Color> c = new ArrayList<Color>();
        
        int now = 0;
        
        int d = allColors.size() / count;
        
        for (int q = 0; q < count; q++) {
            
            c.add(allColors.get(now));
            now += d;
            
        }
        
        return c;
        
    }
    
    
}
