package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _QuestInput extends _QuestIO implements Serializable{

    _Rectangle target;

    public _QuestInput(Quest owner, int posX, int posY, int width, int height, Color c) {
        super(owner, posX, posY, width, height, c);
    }

    
    
    public _Rectangle getTarget() {
        return target;
    }

    @Override
    public void updatePosition() {
        this.posX = owner.questBubble.posX - 15;
        this.posY = owner.questBubble.posY + 13 * owner.inputs.indexOf(this);
    }

    @Override                                           //used in QuestOutput only
    public void buildInnerSquare(int posistionInLinkedList, int windowWidth) {
        this.innerPosX = 0;
        this.innerPosY = posistionInLinkedList*33;
        this.innerHeigth = 30;
        this.innerWidth = 30;
    }
    
}
