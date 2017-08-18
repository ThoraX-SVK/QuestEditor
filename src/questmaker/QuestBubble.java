package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

public class QuestBubble extends _Rectangle implements Serializable {

    static final long serialVersionUID = 42L;
    String questName;
    Quest quest;

    public QuestBubble(String questName, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, questName.length() * 13, 20, c);
        this.questName = questName;
        this.quest = new Quest(this);
    }

    public void update() {
        this.width = questName.length() * 13;

        for (_QuestOutput qo : quest.outputs) {
            qo.updatePosition();
        }
        for (_QuestInput qi :quest.inputs) {
            qi.updatePosition();
        }
    }

    public void delete() {
        quest.delete();
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(this.questName, this.posX, this.posY + 18);

        if (!this.quest.inputs.isEmpty()) {
            for (_QuestInput qi : this.quest.inputs) {
                qi.draw(g);
            }
        }
        if (!this.quest.outputs.isEmpty()) {
            for (_QuestOutput qo : this.quest.outputs) {
                qo.draw(g);
            }
        }

    }
}
