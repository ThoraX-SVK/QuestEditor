package questmaker;

/**
 *
 * @author Tom
 */
public class AnswerOutput {
    
    Answer belongsTo;
    Decision goingToDecision;
    int posX;
    int posY;
    int size;

    public AnswerOutput(Answer belongsTo) {
        this.belongsTo = belongsTo;
        goingToDecision = null;
        posX = belongsTo.posX + belongsTo.size + 5;
        posY = belongsTo.posY;
        size = 10;
    }
    
    public void updatePosition() {
        posX = belongsTo.posX + belongsTo.size + 5;
        posY = belongsTo.posY;
    }
    
}
