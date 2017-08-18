package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _AnswerOuput extends _DecisionIO implements Serializable{

    Answer owner;
    _Rectangle target;

    public _AnswerOuput(Answer owner, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.owner = owner;
    }

    public _Rectangle getTarget() {
        return target;
    }
    
    @Override
    public void updatePosition() {
        this.posX = owner.posX + owner.width + 5;
        this.posY = owner.posY;
    }
    
}
