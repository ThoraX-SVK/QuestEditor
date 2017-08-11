package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import questmaker.Answer;
import questmaker.AnswerOutput;
import questmaker.Decision;
import questmaker.DecisionInput;
import questmaker.DoubleBuffer;
import questmaker.InputOutputSquare;
import questmaker.ProgramStart;
import questmaker.QuestBubble;
import questmaker.QuestInput;
import questmaker.QuestOutput;
import questmaker.SaveFile;

/**
 *
 * @author Tom
 */
public class GameDraw extends DoubleBuffer implements MouseListener, Runnable {

    SaveFile toLoad;
    LinkedList<QuestBubble> questBubbles;
    ProgramStart start;

    Decision toDraw;
    String question;
    LinkedList<Answer> answers;
    
    
    
    
    InputOutputSquare current;
    
    
    QuestInput tempQuestInput;
    QuestOutput tempQuestOutput;
    AnswerOutput tempAnswerOutput;
    DecisionInput tempDecisionInput;

    public GameDraw(SaveFile toLoad) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        
        this.toLoad = toLoad;

        questBubbles = this.toLoad.getQuestbubbles();
        start = this.toLoad.getProgramStart();

    }

    @Override
    public void run() {

        if (current == null) {
            current = start.getTarget();
        }
        

        if (current instanceof QuestInput) {
            tempQuestInput = (QuestInput)current;
            toDraw = tempQuestInput.getTarget().getOwner();
        } else if (current instanceof QuestOutput) {
            tempQuestOutput = (QuestOutput)current;
            toDraw = tempQuestOutput.getTarget().getTarget().getOwner();  
        } else if (current instanceof AnswerOutput) {
            tempAnswerOutput = (AnswerOutput)current;
            current = tempAnswerOutput.getTarget();

            if (current instanceof DecisionInput) {
                tempDecisionInput = (DecisionInput)current;
                toDraw = tempDecisionInput.getOwner();
            } else if (current instanceof QuestOutput) {
                tempQuestOutput = (QuestOutput)current;
                toDraw = tempQuestOutput.getTarget().getTarget().getOwner();          
            } 
        }
        
        this.repaint();
    }

    public void paintBuffer(Graphics g) {
        
        if (toDraw != null) {
            question = toDraw.getQuestion();
            answers = toDraw.getAnswers();
            
            g.setFont(new Font("OCR A Extended", 1, 20));
            g.setColor(Color.red);
            g.drawString(question, 200, 200);
        
            for (int i = 0; i < answers.size(); i++) {
                g.drawString(answers.get(i).getAnswer(), 200, 250 + 30*i);
            }
        
        
        
        
        
        
        
        }
        
    }
    
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {

        
        current = toDraw.getAnswers().get(0).getOutput().getTarget();
        toDraw = null;
        
        
        
        
        this.run();

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
