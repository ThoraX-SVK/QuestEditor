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
    boolean drawEndScreen;
    String retry;
    String exit;
    MyRectangle retryRect;
    MyRectangle exitRect;

    public GameDraw(SaveFile toLoad) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);

        this.toLoad = toLoad;
        this.answerBubbles = new LinkedList<>();
        this.drawEndScreen = false;

        this.retry = "Start again";
        this.retryRect = new MyRectangle(retry.length() * 20, Color.GREEN) {
            @Override
            public void draw(Graphics g) {
                g.setColor(this.getColor());
                g.drawRect(this.getPosX(), this.getPosY(), this.getSize(), 60);
            }

        };

        this.exit = "Main Menu";

        this.exitRect = new MyRectangle(exit.length() * 25, Color.RED) {
            @Override
            public void draw(Graphics g) {
                g.setColor(this.getColor());
                g.drawRect(this.getPosX(), this.getPosY(), this.getSize(), 60);
            }

        };

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
        } catch (NullPointerException ex) {
            endScreenRoutine();
        }

    }

    @Override
    public void paintBuffer(Graphics g) {

        if (drawEndScreen) {
            drawEndScreen(g);
            return;
        }

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
                ab.setPosY(250 + 33 * i);
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
        
        if (answers != null) {
            if (!answers.isEmpty()) {
                for (AnswerBubble ab : answerBubbles) {
                    if (ab.MouseOverlaps(e.getPoint())) {
                        current = ab.answer.getOutput().getTarget();

                        if (current == null) {  /* Output pre danú answer nemá target */
                            endScreenRoutine();
                        }

                        toDraw = null;
                        this.run();
                    }
                }
            }
            else {
                endScreenRoutine();
            }
        } else if (drawEndScreen) {
            if (retryRect.MouseOverlaps(e.getPoint())) {
                resetState();
            } else if (exitRect.MouseOverlaps(e.getPoint())) {
                //back to main menu
                System.exit(0);
            }

        } else {
            //mrtvy bod, nie je tu žiadna odpoveď

            endScreenRoutine();
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

    private void resetState() {

        drawEndScreen = false;
        current = null;
        this.run();
    }

    private void drawEndScreen(Graphics g) {
        g.setFont(new Font("Tw Cen MT Condensed", 1, 80));

        g.setColor(Color.WHITE);
        g.drawString("This is the end!", this.getWidth()/2 - "This is the end".length()*13 ,300);
        
        g.setFont(new Font("Tw Cen MT Condensed", 1, 50));
        
        retryRect.setPosX(this.getWidth() - 50 - retryRect.getSize());
        retryRect.setPosY(this.getHeight() - 100);
        retryRect.draw(g);
        g.drawString(retry, retryRect.getPosX() + 13, retryRect.getPosY() + 45);

        exitRect.setPosX(50);
        exitRect.setPosY(retryRect.getPosY());
        exitRect.draw(g);
        g.drawString(exit, exitRect.getPosX() + 13, exitRect.getPosY() + 45);
    }
    
    private void endScreenRoutine() {
        
        answers = null;
        drawEndScreen = true;
        this.repaint();
    }

}
