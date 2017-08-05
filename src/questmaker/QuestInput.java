package questmaker;

import java.awt.Color;

public class QuestInput {
    
    Quest inputForQuest;
    int id;
    int posX;
    int posY;
    int size;
    Color color;

    public QuestInput(Quest inputForQuest, int id, int posX, int posY, int size, Color color) {
        this.inputForQuest = inputForQuest;
        this.id = id;
        this.posX = posX;
        this.posY = posY;
        this.size = size;
        this.color = color;
    }
    
    public void updatePosition() {
        this.posX = inputForQuest.questBubble.posX - 15;  
        this.posY = inputForQuest.questBubble.posY + 13*inputForQuest.inputs.indexOf(this);
    }
}
