package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _DecisionInput extends _DecisionIO implements Serializable {
 
    Decision owner;

    public _DecisionInput(Decision owner, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.owner = owner;
    }
    
    public Decision getOwner() {
        return owner;
    }
    
    @Override
    public void updatePosition() {
        this.posX = owner.posX - 15;
        this.posY = owner.posY;
    }
    
}
