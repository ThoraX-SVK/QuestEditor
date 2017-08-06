package questmaker;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Tom
 */
public class AnswerOutput {
    
    Answer belongsTo;
    Decision goingToDecision;
    QuestOutput goingToQuestOutput;
    int posX;
    int posY;
    int size;
    Color color;

    public AnswerOutput(Answer belongsTo) {
        this.belongsTo = belongsTo;
        goingToDecision = null;
        goingToQuestOutput = null;
        posX = belongsTo.posX + belongsTo.size + 5;
        posY = belongsTo.posY;
        size = 10;
        color = Color.BLUE;
    }
    
    public void updatePosition() {
        posX = belongsTo.posX + belongsTo.size + 5;
        posY = belongsTo.posY;
    }
    
    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX+size &&
               mousePosition.y > posY && mousePosition.y < posY+size;
    }
    
}
