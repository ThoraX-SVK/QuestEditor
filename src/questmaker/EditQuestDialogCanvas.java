package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EditQuestDialogCanvas extends DoubleBuffer implements MouseListener, MouseMotionListener {

    Quest quest;
    Decision tempDecision;
    Point tempLineEnd;
    _AnswerOuput tempAnswerOutput;
    _QuestInput tempQuestInput;

    _Rectangle chosenInput;
    _Rectangle chosenOutput;
    _Rectangle chosenBlock;

    _Rectangle lastRectangleMouseOver;

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

        for (_QuestInput qi : quest.inputs) {
            if (qi.target != null) {
                drawLine(qi, qi.getTarget(), g);
            }
        }

        for (Decision de : quest.decisions) {
            for (Answer an : de.answers) {
                if (an.output != null) {
                    drawLine(an.output, an.output.target, g);
                }
            }
        }

        for (_QuestInput qi : quest.inputs) {
            qi.buildInnerSquare(quest.inputs.indexOf(qi), this.getWidth());
            g.setColor(Color.RED);
            qi.drawInner(g);
        }

        for (_QuestOutput qo : quest.outputs) {
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

        if (e.getButton() == MouseEvent.BUTTON1) {

            chosenBlock = selectBlock(e.getPoint());

            if (chosenBlock instanceof Decision) {
                tempDecision = (Decision) chosenBlock;
                drawDeleteZone = true;
                mouseHoldDecisionSelected = true;
                tempDecision.color = Color.RED;
                this.repaint();
                return;
            }

            chosenOutput = selectOutput(e.getPoint());

            if (chosenOutput instanceof _AnswerOuput) {
                tempAnswerOutput = (_AnswerOuput) chosenOutput;

                if (tempAnswerOutput.target == null) {
                    /* nejde z neho čiara */
                    mouseHoldAnswerOutputSelected = true;
                    tempAnswerOutput.color = Color.WHITE;
                    this.repaint();
                    return;
                }
            } else if (chosenOutput instanceof _QuestInput) {
                tempQuestInput = (_QuestInput) chosenOutput;

                if (tempQuestInput.target == null) {
                    /* Nejde z neho čiara */
                    mouseHoldOnQuestInputSelected = true;
                    tempQuestInput.color = Color.WHITE;
                    this.repaint();
                    return;
                }
            }

            DecisionAddNewAnswerSign plusSign = selectPlusSign(e.getPoint());

            if (plusSign != null) {
                plusSign.belongsTo.addAnswer("null");
                plusSign.belongsTo.updateAfterNewAnswer();
                this.repaint();
            }

        } else if (e.getButton() == MouseEvent.BUTTON3) {

            chosenBlock = selectBlock(e.getPoint());

            if (chosenBlock instanceof Decision) {
                DecisionEditDialog ded = new DecisionEditDialog(null, tempDecision);
                this.repaint();
                return;
            } else if (chosenBlock instanceof Answer) {

                Answer an = (Answer) chosenBlock;
                RequestTextDialog rtg = new RequestTextDialog(null, an.popis, 300, 200);
                an.popis = rtg.textArea.getText();
                this.repaint();
                return;
            }

            chosenInput = selectInput(e.getPoint());

            if (chosenInput instanceof _QuestOutput) {

                _QuestOutput qo = (_QuestOutput) chosenInput;
                clearIngoingLines(qo);
            } else if (chosenInput instanceof _DecisionInput) {

                _DecisionInput din = (_DecisionInput) chosenInput;
                clearIngoingLines(din);
                this.repaint();
            }

            chosenOutput = selectOutput(e.getPoint());

            if (chosenOutput instanceof _AnswerOuput) {

                tempAnswerOutput = (_AnswerOuput) chosenOutput;
                clearOutgoingLine(tempAnswerOutput);
            } else if (chosenOutput instanceof _QuestInput) {

                _QuestInput qi = (_QuestInput) chosenOutput;
                clearOutgoingLine(qi);
                this.repaint();
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (mouseHoldAnswerOutputSelected) {
            tempAnswerOutput.color = Color.BLUE;
            _Rectangle target = selectInput(e.getPoint());

            if (target != null) {
                tempAnswerOutput.target = target;
            }

        } else if (mouseHoldOnQuestInputSelected) {
            tempQuestInput.color = Color.RED;

            _Rectangle target = selectInput(e.getPoint());

            if (target != null) {
                tempQuestInput.target = target;
            }
        } else if (mouseHoldDecisionSelected) {

            //DELETE ZONA
            if (e.getX() > this.getWidth() - 160 && e.getX() < this.getWidth() - 40
                    && e.getY() > this.getHeight() - 40 && e.getY() < this.getHeight() - 10) {

                deleteBlock(tempDecision);

            } else { //ak nie je v DELETE zone

            }

        }

        drawDeleteZone = false;
        tempLineEnd = null;
        tempAnswerOutput = null;
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

        _Rectangle mouseOn = selectBlock(e.getPoint());

        if (mouseOn != null) {
            if (lastRectangleMouseOver != null) {
                if (lastRectangleMouseOver instanceof Decision) {
                    lastRectangleMouseOver.color = Color.CYAN;
                } else if (lastRectangleMouseOver instanceof Answer) {
                    lastRectangleMouseOver.color = Color.YELLOW;
                }
            }
            lastRectangleMouseOver = mouseOn;
            mouseOn.color = Color.MAGENTA;
            this.repaint();
        }

        if (lastRectangleMouseOver != null && mouseOn == null) {

            if (lastRectangleMouseOver instanceof Decision) {
                lastRectangleMouseOver.color = Color.CYAN;
            } else if (lastRectangleMouseOver instanceof Answer) {
                lastRectangleMouseOver.color = Color.YELLOW;
            }

            lastRectangleMouseOver = null;
            this.repaint();
        }
    }

    private void drawDeleteZone(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(this.getWidth() - 160, this.getHeight() - 40, 120, 30);
        g.drawString("Delete", this.getWidth() - 140, this.getHeight() - 18);
    }

    private void drawLine(_Rectangle square1, _Rectangle square2, Graphics g) {
        g.setColor(Color.WHITE);
        if (square1 instanceof _QuestInput && square2 instanceof _DecisionInput) {
            _QuestInput qi = (_QuestInput) square1;
            _DecisionInput di = (_DecisionInput) square2;

            g.drawLine(qi.innerPosX + 15, qi.innerPosY + 15, di.posX + 5, di.posY + 5);
        } else if (square1 instanceof _QuestInput && square2 instanceof _QuestOutput) {
            _QuestInput qi = (_QuestInput) square1;
            _QuestOutput qo = (_QuestOutput) square2;

            g.drawLine(qi.innerPosX + 15, qi.innerPosY + 15, qo.innerPosX + 15, qo.innerPosY + 15);
        } else if (square1 instanceof _AnswerOuput && square2 instanceof _DecisionInput) {
            _AnswerOuput ao = (_AnswerOuput) square1;
            _DecisionInput di = (_DecisionInput) square2;

            g.drawLine(ao.posX + 5, ao.posY + 5, di.posX + 5, di.posY + 5);
        } else if (square1 instanceof _AnswerOuput && square2 instanceof _QuestOutput) {
            _AnswerOuput ao = (_AnswerOuput) square1;
            _QuestOutput qo = (_QuestOutput) square2;

            g.drawLine(ao.posX + 5, ao.posY + 5, qo.innerPosX + 15, qo.innerPosY + 15);
        }
    }

    private _Rectangle selectOutput(Point mouseCursor) {

        for (_QuestInput qi : quest.inputs) {
            if (qi.isMouseOverInner(mouseCursor)) {
                return qi;
            }
        }

        for (Decision de : quest.decisions) {
            for (Answer an : de.answers) {
                if (an.output.isMouseOver(mouseCursor)) {
                    return an.output;
                }
            }
        }

        return null;
    }

    private _Rectangle selectInput(Point mouseCursor) {

        for (_QuestOutput qo : quest.outputs) {
            if (qo.isMouseOverInner(mouseCursor)) {
                return qo;
            }
        }

        for (Decision de : quest.decisions) {
            if (de.decisionInput.isMouseOver(mouseCursor)) {
                return de.decisionInput;
            }
        }

        return null;
    }

    private _Rectangle selectBlock(Point mouseCursor) {

        for (Decision de : quest.decisions) {
            if (de.isMouseOver(mouseCursor)) {
                return de;
            } else {
                for (Answer an : de.answers) {
                    if (an.isMouseOver(mouseCursor)) {
                        return an;
                    }
                }
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

    private void clearIngoingLines(_Rectangle rec) {

        if (rec instanceof _QuestOutput) {
            _QuestOutput qo = (_QuestOutput) rec;

            for (_QuestInput qi : quest.inputs) {
                if (qi.target == qo) {
                    qi.target = null;
                }
            }

            for (Decision de : quest.decisions) {
                for (Answer an : de.answers) {
                    if (an.output.target == qo) {
                        an.output.target = null;
                    }
                }
            }

        } else if (rec instanceof _DecisionInput) {
            _DecisionInput di = (_DecisionInput) rec;

            for (_QuestInput qi : quest.inputs) {
                if (qi.target == di) {
                    qi.target = null;
                }
            }

            for (Decision de : quest.decisions) {
                for (Answer an : de.answers) {
                    if (an.output.target == di) {
                        an.output.target = null;
                    }
                }
            }
        }
    }

    private void clearOutgoingLine(_Rectangle rec) {

        if (rec instanceof _QuestInput) {
            _QuestInput qi = (_QuestInput) rec;
            qi.target = null;
        } else if (rec instanceof _AnswerOuput) {
            _AnswerOuput ao = (_AnswerOuput) rec;
            ao.target = null;
        }

    }

    private void clearBlockIngoingLines(_Rectangle rec) {

        if (rec instanceof Decision) {
            Decision de = (Decision) rec;

            clearIngoingLines(de.decisionInput);
        }
    }

    private void clearBlockOutgoingLines(_Rectangle rec) {

        if (rec instanceof Decision) {
            Decision de = (Decision) rec;

            for (Answer an : de.answers) {
                clearOutgoingLine(an.output);
            }
        }
    }

    private void deleteBlock(_Rectangle rec) {

        if (rec instanceof Decision) {
            Decision de = (Decision) rec;

            clearBlockIngoingLines(de);
            clearBlockOutgoingLines(de);
            quest.decisions.remove(de);
            de.delete();
        }
    }
}
