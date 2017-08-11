package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class QuestInput extends QuestInputOutputSquare implements Serializable {

    static final long serialVersionUID = 42L;
    DecisionInput target;
    
    public QuestInput(Quest owner, int size, Color color) {
        super(owner, size, color);
        this.target = null;
    }

    @Override
    public void upadatePosition() {
        this.posX = owner.questBubble.posX - 15;
        this.posY = owner.questBubble.posY + 13 * owner.inputs.indexOf(this);
    }

    @Override                                                //unused here
    public void buildInnerSquare(int posistionInLinkedList, int windowWidth) {
        this.innerPosX = 0;
        this.innerPosY = posistionInLinkedList*33;
        this.innerSize = 30;
    }

    public DecisionInput getTarget() {
        return target;
    }
    
    
    
}
