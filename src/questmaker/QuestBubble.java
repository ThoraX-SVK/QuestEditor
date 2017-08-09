package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class QuestBubble {

    String questName;
    int posX;
    int posY;
    int bubbleSize;
    Color bubbleColor;
    Quest quest;

    public QuestBubble(String questName, int posX, int posY, Color bubbleColor, Quest quest) {
        this.questName = questName;
        this.posX = posX;
        this.posY = posY;
        this.bubbleSize = questName.length() * 13;
        this.bubbleColor = bubbleColor;
        this.quest = new Quest(this);

    }

    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX + bubbleSize
                && mousePosition.y > posY && mousePosition.y < posY + 20; // 20 -> Nahardkodene v paintbuffer() v QuestMainFrameDraw
    }

    public void update() {
        this.bubbleSize = questName.length() * 13;

        for (QuestOutputNew qo : quest.outputs) {
            qo.upadatePosition();
        }
    }

    public void delete() {
        quest.delete();
    }

    public void draw(Graphics g) {
        g.setColor(this.bubbleColor);

        //pozor na MouseOverlapsSquare() v QuestBubble, hodnota 20 je tam nahardkodena
        g.drawRect(this.posX, this.posY, this.bubbleSize, 20);
        g.drawString(this.questName, this.posX, this.posY + 18);

        if (!this.quest.inputs.isEmpty()) {
            for (QuestInputNew qi : this.quest.inputs) {
                qi.draw(g);
            }
        }
        if (!this.quest.outputs.isEmpty()) {
            for (QuestOutputNew qo : this.quest.outputs) {
                qo.draw(g);
            }
        }

    }
}
