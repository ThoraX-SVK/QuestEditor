package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class QuestOutput {
    Quest outputForQuest;
    int id;
    int posX;
    int posY;
    int size;
    Color color;

    public QuestOutput(Quest outputForQuest, int id, int posX, int posY, int size, Color color) {
        this.outputForQuest = outputForQuest;
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.color = color;
    }

    public void updatePosition() {
        this.posX = outputForQuest.questBubble.posX + outputForQuest.questBubble.bubbleSize + 5;  
        this.posY = outputForQuest.questBubble.posY + 13*outputForQuest.outputs.indexOf(this);
    }
    
    
}
