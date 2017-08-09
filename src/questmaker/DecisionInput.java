package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class DecisionInput extends  InputOutputSquare implements Serializable {
    
    Decision owner;

    public DecisionInput(Decision owner, int size, Color color) {
        super( size, color);
        this.owner = owner;
    }
    
    public void updatePosition() {
        this.posX = owner.posX - size - 5;
        this.posY = owner.posY;
    }
    
}
