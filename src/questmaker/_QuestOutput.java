package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _QuestOutput extends _QuestIO implements Serializable{

    _Rectangle target;

    public _QuestOutput(Quest owner, int posX, int posY, int width, int height, Color c) {
        super(owner, posX, posY, width, height, c);
    }

    
    
    public _Rectangle getTarget() {
        return target;
    }

    @Override
    public void updatePosition() {
        this.posX = owner.questBubble.posX + owner.questBubble.width + 5;
        this.posY = owner.questBubble.posY + 13 * owner.outputs.indexOf(this);
    }

    @Override
    public void buildInnerSquare(int positionInLinkedList, int windowWidth) {
        this.innerPosX = windowWidth - 30;
        this.innerPosY = 33*positionInLinkedList;
        this.innerHeigth = 30;
        this.innerWidth = 30;
    }
    
}
