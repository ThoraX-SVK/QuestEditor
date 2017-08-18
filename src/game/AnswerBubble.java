package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import questmaker.Answer;
import questmaker._Rectangle;

/**
 *
 * @author Tom
 */
public class AnswerBubble extends _Rectangle {
    
    Answer answer;

    public AnswerBubble(Answer answer, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, answer.getAnswer().length() * 12, 25, c);
        this.answer = answer;
    }
    
    
    
    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        super.draw(g);
        g.drawString(answer.getAnswer(),this.getPosX()+2 , this.getPosY()+20);
    }
}
