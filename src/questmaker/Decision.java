package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class Decision extends _Rectangle implements Serializable {

    static final long serialVersionUID = 42L;
    static int decisionId = 0;
    String question;
    String popis;
    LinkedList<Answer> answers;
    _DecisionInput decisionInput;
    DecisionAddNewAnswerSign plusSign;

    public Decision(String question, String popis, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, popis.length() * 13, 20, c);
        this.question = question;
        
        if ("".equals(question)) {
            this.question = "";
        }
        this.popis = popis;
        this.answers = new LinkedList<>();
        decisionInput = new _DecisionInput(this,0,0,10,10,Color.RED);
        decisionInput.updatePosition();
        plusSign = new DecisionAddNewAnswerSign(this);
    }

    public void addAnswer(String text) {
        Answer answer = new Answer(this, "", text, posX, posY + 20 * answers.size() + 20,
                this.width,this.height,Color.YELLOW);
        this.answers.add(answer);
    }

    public void updatePosition(Point mouseCursor) {

        posX = mouseCursor.x - this.width / 2;
        posY = mouseCursor.y - this.height / 2;

        this.decisionInput.updatePosition();
        this.plusSign.updatePositon();

        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).updatePosition();
        }
    }

    public void updateAfterNewAnswer() {

        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).updatePosition();
        }
        this.decisionInput.updatePosition();
        this.plusSign.updatePositon();
    }

    public void update() {

        this.width = popis.length() * 13;
        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).updateSize();
        }
        this.decisionInput.updatePosition();
        this.plusSign.updatePositon();

    }

    public void delete() {
        for (Answer an : answers) {
            an.delete();
        }

        plusSign.delete();

    }
    
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(this.popis, this.posX, this.posY + 18);
        this.plusSign.draw(g);
        this.decisionInput.draw(g);

        for (Answer an : this.answers) {
            an.draw(g);
        }
    }

    public String getQuestion() {
        return question;
    }

    public LinkedList<Answer> getAnswers() {
        return answers;
    }
    
    
    
    

}
