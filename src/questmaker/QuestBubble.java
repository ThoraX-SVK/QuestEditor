package questmaker;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Tom
 */
public class QuestBubble {
    
    String questName;
    int posX;
    int posY;
    int bubbleSize;
    Color bubbleColor;
    
    Quest quest;

    public QuestBubble(String questName,int posX, int posY, Color bubbleColor, Quest quest) {
        this.questName = questName;
        this.posX = posX;
        this.posY = posY;
        this.bubbleSize = questName.length() * 13;
        this.bubbleColor = bubbleColor;
        this.quest = new Quest(this);
        this.quest.addInput();
        this.quest.addInput();
        this.quest.addInput();
        this.quest.addOutput();
        this.quest.addOutput();
        this.quest.addOutput();
        
    }
    
    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX+bubbleSize &&
               mousePosition.y > posY && mousePosition.y < posY+20; // 20 -> Nahardkodene v paintbuffer() v QuestMainFrameDraw
    }

    
    
    
    
}
