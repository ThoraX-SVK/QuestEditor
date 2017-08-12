package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import questmaker.Answer;
import questmaker.MyRectangle;

/**
 *
 * @author Tom
 */
public class AnswerBubble extends MyRectangle {
    
    Answer answer;
    
    public AnswerBubble(Color color, Answer answer) {
        super(answer.getAnswer().length() * 12, color);
        this.answer = answer;
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.drawRect(this.getPosX(), this.getPosY(), this.getSize(), 25);
        g.drawString(answer.getAnswer(),this.getPosX()+2 , this.getPosY()+20);
    }
    
    @Override
    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > this.getPosX() && mousePosition.x < this.getPosX() + this.getSize()
                && mousePosition.y > this.getPosY() && mousePosition.y < this.getPosY() + 25;
    }
    
    
    
}
