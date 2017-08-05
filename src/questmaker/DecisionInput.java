package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class DecisionInput {

    static int decisionInputId = 0;
    Decision inputToDecision;
    int posX;
    int posY;
    int size;
    int id;
    Color color;

    public DecisionInput(Decision inputToDecision) {
        this.inputToDecision = inputToDecision;
        this.size = 10;
        this.posX = inputToDecision.posX - size - 5;
        this.posY = inputToDecision.posY;
        this.id = decisionInputId;
        decisionInputId++;
        this.color = Color.RED;        
    }
    
    public void updatePosition() {
        this.posX = inputToDecision.posX - size - 5;
        this.posY = inputToDecision.posY;
    }
    
    
   
   
}
