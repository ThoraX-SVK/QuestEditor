package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class DecisionInput extends  MyRectangle implements Serializable {
    
    static final long serialVersionUID = 42L;
    Decision owner;

    public DecisionInput(Decision owner, int size, Color color) {
        super( size, color);
        this.owner = owner;
    }
    
    public void updatePosition() {
        this.posX = owner.posX - size - 5;
        this.posY = owner.posY;
    }

    public Decision getOwner() {
        return owner;
    }
    
    
    
}
