package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

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
    
    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX+size &&
               mousePosition.y > posY && mousePosition.y < posY+size;
    }
    
    public void delete() {
        this.inputForQuest = null;
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawRect(this.posX, this.posY, this.size, this.size);
    }
}
