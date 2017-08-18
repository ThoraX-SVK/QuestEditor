package questmaker;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class FunctionBlockRandom extends FunctionBlock implements Serializable {
    
    
    public FunctionBlockRandom(int posX, int posY, int width, int height, Color c) {
        super("RANDOM", posX, posY, width, height, c);
        this.addInput();
    }
    
    @Override
    public _Rectangle getTarget() {
        
        double totalSum = 0;
        
        for (_FunctionOutput fo :outputs) {
            totalSum += fo.probability;
        }
        
        double chance = Math.random()*totalSum;
        double summing = 0;
        for (_FunctionOutput fo : outputs) {
            if (chance >= summing && chance < summing + fo.probability)
                return fo;
            else
                summing += fo.probability;
        }
    
        return null;
    }
    
}
