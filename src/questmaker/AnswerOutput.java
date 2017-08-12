package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class AnswerOutput extends MyRectangle implements Serializable {
    
    static final long serialVersionUID = 42L;
    Answer owner;
    MyRectangle target;

    public AnswerOutput(Answer owner, int size, Color color) {
        super(size, color);
        this.owner = owner;
    }
    
    public void updatePosition() {
        posX = owner.posX + owner.size + 5;
        posY = owner.posY;
    }

    public MyRectangle getTarget() {
        return target;
    }
    
    
    
   
}
