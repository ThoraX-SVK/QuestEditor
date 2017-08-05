package questmaker;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class Decision {
    
    static int decisionId = 0;
    String question;
    String popis;
    LinkedList<Answer> answers;
    int id;
    int posX;
    int posY;
    int size;
    Color color;
    DecisionInput decisionInput;

    public Decision(String question, String popis, int posX, int posY,Color c) {
        this.question = question;
        this.popis = popis;
        this.answers = new LinkedList<>();
        this.posX = posX;
        this.posY = posY;
        this.size = popis.length() * 13;
        this.color = c;
        this.id = decisionId;
        decisionInput = new DecisionInput(this);
        decisionId++;
        this.addAnswer("a1");
        this.addAnswer("a2");
        this.addAnswer("a3");
    }
    
    public void addAnswer(String text) { 
        Answer answer = new Answer(this,"",text,posX,posY + 20*answers.size() + 20,size);
        this.answers.add(answer);
    }
    
    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX+size &&
               mousePosition.y > posY && mousePosition.y < posY+20; // 20 -> Nahardkodene v paintbuffer() v QuestMainFrameDraw
    }
    
    public void reposition(Point mouseCursor) {
        
    }
    
}
