package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class Answer extends _Rectangle implements Serializable {

    static final long serialVersionUID = 42L;
    static int answerId;
    Decision decision;
    _AnswerOuput output;
    String answer;
    String popis;

    public Answer(Decision decision, String answer, String popis, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.decision = decision;
        this.answer = answer;
        if ("".equals(answer)) {
            this.answer = "null";
        }
        this.popis = popis;
        
        output = new _AnswerOuput(this,0,0,10,10,Color.BLUE);
        output.updatePosition();
    }
    
    public void updatePosition() {
        this.posX = decision.posX;
        this.posY = decision.posY + 20 * decision.answers.indexOf(this) + 20;
        output.updatePosition();
    }

    public void updateSize() {
        this.width = decision.width;
        output.updatePosition();
    }

    public void delete() {
        output.target = null;
        decision = null;
    }
    
    @Override
    public void draw (Graphics g) {
        super.draw(g);
        g.drawString(this.popis, this.posX, this.posY + 18);
        this.output.draw(g);
    }

    public String getAnswer() {
        return answer;
    }

    public _AnswerOuput getOutput() {
        return output;
    }
    
    
    
    

}
