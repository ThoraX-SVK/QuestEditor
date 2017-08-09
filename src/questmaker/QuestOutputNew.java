package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class QuestOutputNew extends QuestInputOutput {

    QuestInputNew target;
    
    public QuestOutputNew(Quest owner, int size, Color color) {
        super(owner, size, color);
        this.target = null;
    }
    
    @Override
    public void upadatePosition() {
        this.posX = owner.questBubble.posX + owner.questBubble.bubbleSize + 5;
        this.posY = owner.questBubble.posY + 13 * owner.outputs.indexOf(this);
    }

    @Override
    public void buildInnerSquare(int positionInLinkedList, int windowWidth) {
        this.innerPosX = windowWidth - 30;
        this.innerPosY = 33*positionInLinkedList;
        this.innerSize = 30;
    }
    
}
