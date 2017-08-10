package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class EditQuestDialogCanvas extends DoubleBuffer implements MouseListener, MouseMotionListener {

    Quest quest;
    Decision tempDecision;
    Decision lastDecisionMouseOver;
    Point tempLineEnd;
    AnswerOutput tempAnswerOutput;
    QuestInput tempQuestInput;
    boolean mouseHoldDecisionSelected;
    boolean mouseHoldAnswerOutputSelected;
    boolean mouseHoldOnQuestInputSelected;
    boolean drawDeleteZone;

    public EditQuestDialogCanvas(Quest quest) {
        this.quest = quest;
        this.mouseHoldDecisionSelected = false;
        this.mouseHoldAnswerOutputSelected = false;
        this.drawDeleteZone = false;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(Color.BLACK);
    }

    @Override
    public void paintBuffer(Graphics g) {

        g.setFont(new Font("OCR A Extended", 1, 20));
        if (drawDeleteZone) {
            drawDeleteZone(g);
        }

        if (tempLineEnd != null) {
            g.setColor(Color.YELLOW);
            if (tempAnswerOutput != null) {
                g.drawLine(tempAnswerOutput.posX + 5, tempAnswerOutput.posY + 5,
                        tempLineEnd.x, tempLineEnd.y);
            } else if (tempQuestInput != null) {
                g.drawLine(15, quest.inputs.indexOf(tempQuestInput) * 33 + 16,
                        tempLineEnd.x, tempLineEnd.y);
            }
        }

        for (QuestInput qi : quest.inputs) {
            if (qi.target != null) {
                drawLine(qi, qi.target, g);
            }
        }

        for (Decision de : quest.decisions) {
            for (Answer an : de.answers) {
                if (an.output != null) {
                    drawLine(an.output, an.output.target, g);
                }
            }
        }

        for (QuestInput qi : quest.inputs) {
            qi.buildInnerSquare(quest.inputs.indexOf(qi), this.getWidth());
            g.setColor(Color.RED);
            qi.drawInner(g);
        }

        for (QuestOutput qo : quest.outputs) {
            qo.buildInnerSquare(quest.outputs.indexOf(qo), this.getWidth());
            g.setColor(Color.BLUE);
            qo.drawInner(g);
        }

        if (!quest.decisions.isEmpty()) {
            for (Decision de : quest.decisions) {
                de.draw(g);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {

        tempDecision = selectDecisionOnMouseOver(e.getPoint());
        tempAnswerOutput = selectAnswerOutputOnMouseOver(e.getPoint());
        DecisionAddNewAnswerSign plusSign = selectPlusSign(e.getPoint());
        Answer an = selectAnswerOnMouseOver(e.getPoint());
        DecisionInput di = selectDecisionInputOnMouseOver(e.getPoint());
        QuestOutput qo = selectQuestOutputOnMouseOver(e.getPoint());
        tempQuestInput = selectQuestInputOnMouseOver(e.getPoint());

        if (tempDecision == null && tempAnswerOutput == null && plusSign == null
                && an == null && tempQuestInput == null && di == null && qo == null) {
            return;
        }

        if (e.getButton() == MouseEvent.BUTTON1) {
            if (tempDecision != null) {
                drawDeleteZone = true;
                mouseHoldDecisionSelected = true;
                tempDecision.color = Color.RED;
                this.repaint();
            } else if (tempAnswerOutput != null && tempAnswerOutput.target == null) {
                mouseHoldAnswerOutputSelected = true;
                tempAnswerOutput.color = Color.WHITE;
                this.repaint();
            } else if (plusSign != null) {
                plusSign.belongsTo.addAnswer("nova odpoved");
                plusSign.belongsTo.updateAfterNewAnswer();
                this.repaint();
            } else if (tempQuestInput != null && tempQuestInput.target == null) {
                mouseHoldOnQuestInputSelected = true;
                tempQuestInput.color = Color.WHITE;
                this.repaint();
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            if (tempDecision != null) {
                DecisionEditDialog ded = new DecisionEditDialog(null, tempDecision);
                this.repaint();
            } else if (an != null) {
                RequestTextDialogAnswer rtg = new RequestTextDialogAnswer(null, an.popis, 300, 200);
                an.popis = rtg.textArea.getText();
                this.repaint();
            } else if (tempQuestInput != null) {
                tempQuestInput.target = null;
            } else if (tempAnswerOutput != null) {
                tempAnswerOutput.target = null;
            } else if (di != null) {

                for (Decision de : quest.decisions) {
                    for (Answer answer : de.answers) {
                        if (answer.output.target == di) {
                            answer.output.target = null;
                        }
                    }
                }

                for (QuestInput qi : quest.inputs) {
                    if (qi.target == di) {
                        qi.target = null;
                    }
                }

            } else if (qo != null) {

                for (Decision de : quest.decisions) {
                    for (Answer answer : de.answers) {
                        if (answer.output.target == qo) {
                            answer.output.target = null;
                        }
                    }
                }

            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (tempAnswerOutput != null) {
            tempAnswerOutput.color = Color.BLUE;
            DecisionInput di = selectDecisionInputOnMouseOver(e.getPoint());
            QuestOutput qo = selectQuestOutputOnMouseOver(e.getPoint());
            if (di != null) {
                tempAnswerOutput.target = di;
            } else if (qo != null) {
                tempAnswerOutput.target = qo;
            }
        } else if (tempQuestInput != null && mouseHoldOnQuestInputSelected) {
            tempQuestInput.color = Color.RED;
            DecisionInput di = selectDecisionInputOnMouseOver(e.getPoint());
            tempQuestInput.target = di;

        } else if (tempDecision != null) {
            if (e.getX() > this.getWidth() - 160 && e.getX() < this.getWidth() - 40
                    && e.getY() > this.getHeight() - 40 && e.getY() < this.getHeight() - 10) {

                for (Decision de : quest.decisions) {
                    for (Answer an : de.answers) {
                        if (an.output.target == tempDecision.decisionInput) {
                            an.output.target = null;
                        }
                    }
                }

                for (Answer an : tempDecision.answers) {
                    an.output.target = null;
                }

                for (QuestInput qi : quest.inputs) {
                    if (qi.target == tempDecision.decisionInput) {
                        qi.target = null;
                    }
                }

                tempDecision.delete();
                quest.decisions.remove(tempDecision);
            }

        }

        drawDeleteZone = false;
        tempLineEnd = null;
        mouseHoldAnswerOutputSelected = false;
        mouseHoldDecisionSelected = false;
        mouseHoldOnQuestInputSelected = false;
        this.repaint();
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
        if (mouseHoldDecisionSelected) {
            tempDecision.updatePosition(e.getPoint());
            this.repaint();
        } else if (mouseHoldAnswerOutputSelected || mouseHoldOnQuestInputSelected) {
            tempLineEnd = e.getPoint();
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        Decision de = selectDecisionOnMouseOver(e.getPoint());

        if (de != null) {
            lastDecisionMouseOver = de;
            de.color = Color.MAGENTA;
            this.repaint();
            return;
        }

        if (lastDecisionMouseOver != null) {
            lastDecisionMouseOver.color = Color.CYAN;
            lastDecisionMouseOver = null;
            this.repaint();
        }
    }

    private void drawDeleteZone(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(this.getWidth() - 160, this.getHeight() - 40, 120, 30);
        g.drawString("Delete", this.getWidth() - 140, this.getHeight() - 18);
    }

    private void drawLine(InputOutputSquare square1, InputOutputSquare square2, Graphics g) {
        g.setColor(Color.WHITE);
        if (square1 instanceof QuestInput && square2 instanceof DecisionInput) {
            QuestInput qi = (QuestInput) square1;
            DecisionInput di = (DecisionInput) square2;

            g.drawLine(qi.innerPosX + 15, qi.innerPosY + 15, di.posX + 5, di.posY + 5);
        } else if (square1 instanceof AnswerOutput && square2 instanceof DecisionInput) {
            AnswerOutput ao = (AnswerOutput) square1;
            DecisionInput di = (DecisionInput) square2;

            g.drawLine(ao.posX + 5, ao.posY + 5, di.posX + 5, di.posY + 5);
        } else if (square1 instanceof AnswerOutput && square2 instanceof QuestOutput) {
            AnswerOutput ao = (AnswerOutput) square1;
            QuestOutput qo = (QuestOutput) square2;

            g.drawLine(ao.posX + 5, ao.posY + 5, qo.innerPosX + 15, qo.innerPosY + 15);
        }
    }

    private Decision selectDecisionOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty()) {
            return null;
        }

        Decision de = null;
        for (int i = 0; i < quest.decisions.size(); i++) {
            de = quest.decisions.get(i);
            if (de.MouseOverlaps(mouseCursor)) {
                return de;
            }
        }

        return null;
    }

    private AnswerOutput selectAnswerOutputOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty()) {
            return null;
        }

        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            for (int j = 0; j < de.answers.size(); j++) {
                Answer an = de.answers.get(j);
                if (an.output.MouseOverlaps(mouseCursor)) {
                    return an.output;
                }
            }
        }
        return null;
    }

    private DecisionInput selectDecisionInputOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty()) {
            return null;
        }

        for (Decision de : quest.decisions) {
            if (de.decisionInput.MouseOverlaps(mouseCursor)) {
                return de.decisionInput;
            }
        }
        return null;
    }

    private DecisionAddNewAnswerSign selectPlusSign(Point mouseCursor) {
        if (quest.decisions.isEmpty()) {
            return null;
        }

        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            if (de.plusSign.MouseOverlaps(mouseCursor)) {
                return de.plusSign;
            }
        }
        return null;

    }

    private Answer selectAnswerOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty()) {
            return null;
        }

        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            for (int j = 0; j < de.answers.size(); j++) {
                Answer an = de.answers.get(j);
                if (an.MouseOverlaps(mouseCursor)) {
                    return an;
                }
            }
        }
        return null;
    }

    private QuestOutput selectQuestOutputOnMouseOver(Point mouseCursor) {
        if (quest.outputs.isEmpty()) {
            return null;
        }

        for (QuestOutput qo : quest.outputs) {
            if (qo.overlapsInner(mouseCursor)) {
                return qo;
            }
        }
        return null;
    }

    private QuestInput selectQuestInputOnMouseOver(Point mouseCursor) {
        if (quest.inputs.isEmpty()) {
            return null;
        }

        for (QuestInput qi : quest.inputs) {
            if (qi.overlapsInner(mouseCursor)) {
                return qi;
            }
        }

        return null;
    }

}
