package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class DecisionInput extends  InputOutputSquare {
    
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
