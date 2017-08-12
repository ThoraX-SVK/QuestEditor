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
import questmaker.MyRectangle;
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

    MyRectangle current;

    QuestInput tempQuestInput;
    QuestOutput tempQuestOutput;
    AnswerOutput tempAnswerOutput;
    DecisionInput tempDecisionInput;
    
    LinkedList<AnswerBubble> answerBubbles;

    public GameDraw(SaveFile toLoad) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);

        this.toLoad = toLoad;
        answerBubbles = new LinkedList<>();

        questBubbles = this.toLoad.getQuestbubbles();
        start = this.toLoad.getProgramStart();

    }

    @Override
    public void run() {

        if (current == null) {
            current = start.getTarget();
        }

        try {
            if (current instanceof QuestInput) {
                tempQuestInput = (QuestInput) current;
                toDraw = tempQuestInput.getTarget().getOwner();
            } else if (current instanceof QuestOutput) {
                tempQuestOutput = (QuestOutput) current;
                toDraw = tempQuestOutput.getTarget().getTarget().getOwner();
            } else if (current instanceof AnswerOutput) {
                tempAnswerOutput = (AnswerOutput) current;
                current = tempAnswerOutput.getTarget();

                if (current instanceof DecisionInput) {
                    tempDecisionInput = (DecisionInput) current;
                    toDraw = tempDecisionInput.getOwner();
                } else if (current instanceof QuestOutput) {
                    tempQuestOutput = (QuestOutput) current;
                    toDraw = tempQuestOutput.getTarget().getTarget().getOwner();
                }
            }
            this.repaint();
        }
        catch (NullPointerException ex) {
            System.exit(0);
            
        }
              
    }

    public void paintBuffer(Graphics g) {

        if (toDraw != null) {
            question = toDraw.getQuestion();
            answers = toDraw.getAnswers();

            g.setFont(new Font("Tw Cen MT Condensed", 1, 25));
            g.setColor(Color.GREEN);
            g.drawString(question, 200, 200);

            answerBubbles.clear();
            for (int i = 0; i < answers.size(); i++) {
                AnswerBubble ab = new AnswerBubble(Color.yellow, answers.get(i));
                ab.setPosX(200);
                ab.setPosY(250 + 33*i);
                answerBubbles.add(ab);
            }
            
            for (AnswerBubble ab : answerBubbles) {
                ab.draw(g);
            }

        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {

        for (AnswerBubble ab : answerBubbles) {
            if (ab.MouseOverlaps(e.getPoint())) {
                current = ab.answer.getOutput().getTarget();
                toDraw = null;
                this.run();
            }
        }
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
