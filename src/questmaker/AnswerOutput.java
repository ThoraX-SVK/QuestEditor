package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class AnswerOutput extends InputOutputSquare implements Serializable {
    
    Answer owner;
    InputOutputSquare target;

    public AnswerOutput(Answer owner, int size, Color color) {
        super(size, color);
        this.owner = owner;
    }
    
    public void updatePosition() {
        posX = owner.posX + owner.size + 5;
        posY = owner.posY;
    }
    
   
}
