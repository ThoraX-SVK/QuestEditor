package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class AnswerOutputNew extends InputOutputSquare {
    
    Answer owner;
    InputOutputSquare target;

    public AnswerOutputNew(Answer owner, int size, Color color) {
        super(size, color);
        this.owner = owner;
    }
    
    public void updatePosition() {
        posX = owner.posX + owner.size + 5;
        posY = owner.posY;
    }
    
   
}
