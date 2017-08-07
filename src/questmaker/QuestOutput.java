package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class QuestOutput {

    Quest outputForQuest;
    QuestInput goingToInput;
    AnswerOutput fromAnswerOutput;
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
        this.goingToInput = null;
        this.fromAnswerOutput = null;
    }

    public void updatePosition() {
        this.posX = outputForQuest.questBubble.posX + outputForQuest.questBubble.bubbleSize + 5;
        this.posY = outputForQuest.questBubble.posY + 13 * outputForQuest.outputs.indexOf(this);
    }

    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX + size
                && mousePosition.y > posY && mousePosition.y < posY + size;
    }

    public void setQi(QuestInput qi) {
        this.goingToInput = qi;
    }
    
    public void delete() {
        outputForQuest = null;
        goingToInput = null;
        fromAnswerOutput = null;
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawRect(this.posX, this.posY, this.size, this.size);
    }
}
