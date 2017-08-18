package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import mainMenu.MainMenuFrame;
import questmaker.Answer;
import questmaker.Decision;
import questmaker.DoubleBuffer;
import questmaker.ProgramStart;
import questmaker.SaveFile;
import questmaker._AnswerOuput;
import questmaker._DecisionInput;
import questmaker._FunctionInput;
import questmaker._FunctionOutput;
import questmaker._QuestInput;
import questmaker._QuestOutput;
import questmaker._Rectangle;

/**
 *
 * @author Tom
 */
public class GameDraw extends DoubleBuffer implements MouseListener, MouseMotionListener, Runnable {

    GameFrame owner;
    SaveFile toLoad;
    LinkedList<_Rectangle> blockToDraw;
    ProgramStart start;

    Decision toDraw;
    String question;
    LinkedList<Answer> answers;

    _Rectangle current;

    _QuestInput tempQuestInput;
    _QuestOutput tempQuestOutput;
    _AnswerOuput tempAnswerOutput;
    _DecisionInput tempDecisionInput;

    LinkedList<AnswerBubble> answerBubbles;

    boolean drawEndScreen;
    String retry;
    String exit;
    _Rectangle retryRect;
    _Rectangle exitRect;

    public GameDraw(SaveFile toLoad, GameFrame owner) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;
        this.toLoad = toLoad;
        this.answerBubbles = new LinkedList<>();
        this.drawEndScreen = false;

        this.retry = "Start again";
        this.retryRect = new _Rectangle(0, 0, retry.length() * 20, 60, Color.GREEN);

        this.exit = "Main Menu";
        this.exitRect = new _Rectangle(0, 0, exit.length() * 25, 60, Color.RED);

        blockToDraw = this.toLoad.getBlocksToDraw();
        start = this.toLoad.getProgramStart();

    }

    @Override
    public void run() {

        if (current == null) {
            current = start.getTarget();
        }
            
        try {
            while (!(current instanceof _DecisionInput)) {
                if (current instanceof _QuestInput) {
                    tempQuestInput = (_QuestInput)current;
                    current = tempQuestInput.getTarget();
                } else if (current instanceof _DecisionInput) {
                    ////ZMAZAŤ niekedy
                    tempDecisionInput = (_DecisionInput) current;
                    toDraw = tempDecisionInput.getOwner();
                } else if (current instanceof _AnswerOuput) {
                    tempAnswerOutput = (_AnswerOuput) current;
                    current = tempAnswerOutput.getTarget();
                } else if (current instanceof _QuestOutput) {
                    tempQuestOutput = (_QuestOutput) current;
                    current = tempQuestOutput.getTarget();
                } else if (current instanceof _FunctionInput) {
                    _FunctionInput fi = (_FunctionInput) current;
                    current = fi.getOwner().getTarget();
                } else if (current instanceof _FunctionOutput) {
                    _FunctionOutput fo = (_FunctionOutput) current;
                    current = fo.getTarget();
                } else if (current == null) {
                        /* Output pre danú answer nemá target */
                        endScreenRoutine();
                        break;
                }
            }
            
            if (current instanceof _DecisionInput) {
            _DecisionInput di = (_DecisionInput) current;
            toDraw = di.getOwner();
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

            g.setFont(new Font("Tw Cen MT Condensed", 1, 25));
            drawQuestion(g);
            drawAnswers(g);

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
                    if (ab.isMouseOver(e.getPoint())) {
                        current = ab.answer.getOutput();

                        answerBubbles.clear();
                        toDraw = null;
                        this.run();
                    }
                }
            } else {
                endScreenRoutine();
            }
        } else if (drawEndScreen) {
            if (retryRect.isMouseOver(e.getPoint())) {
                resetState();
            } else if (exitRect.isMouseOver(e.getPoint())) {
                //back to main menu
                MainMenuFrame mmf = new MainMenuFrame();
                owner.dispose();
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

    @Override
    public void mouseDragged(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (answerBubbles != null) {
            if (!answerBubbles.isEmpty()) {
                for (AnswerBubble ab : answerBubbles) {
                    if (ab.isMouseOver(e.getPoint())) {
                        ab.setColor(Color.WHITE);
                    } else {
                        ab.setColor(Color.YELLOW);
                    }
                }
            }
        }

        this.repaint();
    }

    private void resetState() {

        drawEndScreen = false;
        current = null;
        this.run();
    }

    private void drawEndScreen(Graphics g) {
        g.setFont(new Font("Tw Cen MT Condensed", 1, 80));

        g.setColor(Color.WHITE);
        g.drawString("This is the end!", this.getWidth() / 2 - "This is the end".length() * 13, 300);

        g.setFont(new Font("Tw Cen MT Condensed", 1, 50));

        retryRect.setPosX(this.getWidth() - 50 - retryRect.getWidth());
        retryRect.setPosY(this.getHeight() - 100);
        retryRect.draw(g);
        g.drawString(retry, retryRect.getPosX() + 13, retryRect.getPosY() + 45);

        exitRect.setPosX(50);
        exitRect.setPosY(retryRect.getPosY());
        exitRect.draw(g);
        g.drawString(exit, exitRect.getPosX() + 13, exitRect.getPosY() + 45);
    }

    private void drawQuestion(Graphics g) {
        question = toDraw.getQuestion();
        g.setColor(Color.GREEN);
        g.drawString(question, 200, 200);
    }

    private void drawAnswers(Graphics g) {
        answers = toDraw.getAnswers();

        if (answerBubbles.isEmpty()) {
            buildAnswers();
        }

        for (AnswerBubble ab : answerBubbles) {
            ab.draw(g);
        }

    }

    private void buildAnswers() {
        for (int i = 0; i < answers.size(); i++) {
            AnswerBubble ab = new AnswerBubble(answers.get(i), 0, 0, 0, 0, Color.YELLOW);
            ab.setPosX(200);
            ab.setPosY(250 + 33 * i);
            answerBubbles.add(ab);
        }
    }

    private void endScreenRoutine() {

        answerBubbles.clear();
        answers = null;
        drawEndScreen = true;
        this.repaint();
    }
}
