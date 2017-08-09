package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

public class QuestMainFrameCanvas extends DoubleBuffer implements MouseListener, MouseMotionListener {

    ProgramStart programStart;
    QuestMainFrame owner;
    LinkedList<QuestBubble> questBubbles;
    QuestBubble tempQuestBubble;
    QuestBubble lastMouseOver;
    QuestOutput tempQuestOutput;
    QuestInput tempQuesInput;
    Point tempLineEnd;
    boolean mouseButtonHoldOnQuestBuble;
    boolean mouseButtonHoldOnQuestOutput;
    boolean mouseButtonHoldOnProgramStart;
    boolean showDeleteZone;
    boolean deleteOutputLines;
    boolean deleteInputLines;

    public QuestMainFrameCanvas(LinkedList<QuestBubble> questBubbles, QuestMainFrame qmf) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.questBubbles = questBubbles;
        this.mouseButtonHoldOnQuestBuble = false;
        this.mouseButtonHoldOnQuestOutput = false;
        this.showDeleteZone = false;
        this.deleteInputLines = false;
        this.deleteOutputLines = false;
        this.mouseButtonHoldOnProgramStart = false;
        this.programStart = new ProgramStart(null, 30, Color.GREEN);
    }

    @Override
    public void paintBuffer(Graphics g) {

        programStart.draw(g);
        if (programStart.target != null) {
            g.setColor(Color.CYAN);
            g.drawLine(programStart.posX + programStart.size/2, programStart.posY+programStart.size/2,
                    programStart.target.posX + 5, programStart.target.posY + 5);
        }
        
        
        g.setFont(new Font("OCR A Extended", 1, 20));
        if (showDeleteZone) {
            drawDeleteZone(g);
        }

        if (tempLineEnd != null) {
            if (tempQuestOutput != null) {
            g.setColor(Color.ORANGE);
            g.drawLine(tempQuestOutput.posX + 5, tempQuestOutput.posY + 5,
                    tempLineEnd.x, tempLineEnd.y);
            }
            else if (mouseButtonHoldOnProgramStart) {
                g.setColor(Color.CYAN);
                g.drawLine(programStart.posX + programStart.size/2, programStart.posY + programStart.size/2,
                    tempLineEnd.x, tempLineEnd.y);
            }
        }

        if (!questBubbles.isEmpty()) {
            for (QuestBubble qb : questBubbles) {
                qb.draw(g);
                for (QuestOutput qo : qb.quest.outputs) {
                    if (qo.target != null) {
                        drawLine(qo, qo.target, g);
                    }
                }

            }
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());
            tempQuestOutput = selectOutputOnMouseOver(e.getPoint());

            

            if (tempQuestBubble == null && tempQuestOutput == null) {
                if (!programStart.MouseOverlaps(e.getPoint())) {
                    return;
                }
            }

            if (tempQuestBubble != null) {
                tempQuestBubble.bubbleColor = Color.RED;
                mouseButtonHoldOnQuestBuble = true;
                showDeleteZone = true;
                this.repaint();
            } else if (tempQuestOutput != null && tempQuestOutput.target == null) {
                tempQuestOutput.color = Color.WHITE;
                mouseButtonHoldOnQuestOutput = true;
                this.repaint();
            } else if (programStart.MouseOverlaps(e.getPoint())) {
                mouseButtonHoldOnProgramStart = true;
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());
            tempQuestOutput = selectOutputOnMouseOver(e.getPoint());
            tempQuesInput = selectInputOnMouseOver(e.getPoint());

            if (tempQuestBubble != null) {
                EditQuestDialog eqd = new EditQuestDialog(owner, tempQuestBubble.quest);
            } else if (tempQuestOutput != null) {

                tempQuestOutput.target = null;
                this.repaint();
            } else if (tempQuesInput != null) {

                for (QuestBubble qb : questBubbles) {
                    for (QuestOutput qo : qb.quest.outputs) {
                        if (qo.target == tempQuesInput) {
                            qo.target = null;
                        }
                    }
                }
                this.repaint();
            }

        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseButtonHoldOnQuestBuble) {
            //ak sa nachadza v cervenom delete obdlzniku
            if (e.getX() > this.getWidth() - 160 && e.getX() < this.getWidth() - 40
                    && e.getY() > this.getHeight() - 40 && e.getY() < this.getHeight() - 10) {

                for (QuestInput qi : tempQuestBubble.quest.inputs) {
                    for (QuestBubble qb : questBubbles) {
                        for (QuestOutput qo : qb.quest.outputs) {
                            if (qo.target == qi) {
                                qo.target = null;
                            }
                        }
                    }
                }

                for (QuestOutput qo : tempQuestBubble.quest.outputs) {
                    qo.target = null;
                }

                tempQuestBubble.delete();
                questBubbles.remove(tempQuestBubble);
            } else {
                tempQuestBubble.bubbleColor = Color.BLUE;
            }

            this.repaint();
        } else if (mouseButtonHoldOnQuestOutput) {
            tempQuestOutput.color = Color.BLUE;
            QuestInput qi = selectInputOnMouseOver(e.getPoint());
            if (qi != null) {
                tempQuestOutput.target = qi;
            }

            tempQuestOutput = null;
            this.repaint();
        } else if (mouseButtonHoldOnProgramStart) {
            QuestInput qi = selectInputOnMouseOver(e.getPoint());
            if (qi != null) {
                programStart.target = qi;
            }
        }
        
        tempLineEnd = null;
        showDeleteZone = false;
        mouseButtonHoldOnProgramStart = false;
        mouseButtonHoldOnQuestOutput = false;
        mouseButtonHoldOnQuestBuble = false;
        this.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseButtonHoldOnQuestBuble) {
            tempQuestBubble.posX = e.getX() - tempQuestBubble.bubbleSize / 2;
            tempQuestBubble.posY = e.getY() - 10;   //NAHARDKODENE!!!!!!!!!
            for (QuestInput qi : tempQuestBubble.quest.inputs) {
                qi.upadatePosition();
            }
            for (QuestOutput qo : tempQuestBubble.quest.outputs) {
                qo.upadatePosition();
            }
            this.repaint();
        } else if (mouseButtonHoldOnQuestOutput || mouseButtonHoldOnProgramStart) {
            tempLineEnd = new Point(e.getPoint());
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        if (!mouseButtonHoldOnQuestBuble && !questBubbles.isEmpty()) {
            QuestBubble tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());

            if (tempQuestBubble == null && lastMouseOver != null) {
                lastMouseOver.bubbleColor = Color.GREEN;
                lastMouseOver = null;
                this.repaint();
            }
            if (tempQuestBubble != null) {
                lastMouseOver = tempQuestBubble;
                tempQuestBubble.bubbleColor = Color.BLUE;
                this.repaint();
            }
        }
    }

    private QuestBubble selectBubbleOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty()) {
            return null;
        }

        QuestBubble bestBubble = null;
        double distanceToMiddle = Double.MAX_VALUE;
        double tmpdist;
        for (int i = 0; i < questBubbles.size(); i++) {
            tempQuestBubble = questBubbles.get(i);
            if (tempQuestBubble.MouseOverlaps(mouseCursor)) {
                //opat 20 je nahardkodene, pozor na to!!!!
                tmpdist = Point.distance(tempQuestBubble.posX + tempQuestBubble.bubbleSize / 2,
                        tempQuestBubble.posY + 10,
                        mouseCursor.x,
                        mouseCursor.y);
                if (tmpdist < distanceToMiddle) {
                    bestBubble = tempQuestBubble;
                }

            }
        }

        return bestBubble;
    }

    private QuestOutput selectOutputOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty()) {
            return null;
        }

        for (QuestBubble qb : questBubbles) {
            for (QuestOutput qo : qb.quest.outputs) {
                if (qo.MouseOverlaps(mouseCursor)) {
                    return qo;
                }
            }
        }
        return null;
    }

    private QuestInput selectInputOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty()) {
            return null;
        }

        for (QuestBubble qb : questBubbles) {
            for (QuestInput qi : qb.quest.inputs) {
                if (qi.MouseOverlaps(mouseCursor)) {
                    return qi;
                }
            }
        }
        return null;
    }

    private void drawLine(QuestOutput qo, QuestInput qi, Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(qo.posX + 5, qo.posY + 5, qi.posX + 5, qi.posY + 5);
    }

    private void drawDeleteZone(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(this.getWidth() - 160, this.getHeight() - 40, 120, 30);
        g.drawString("Delete", this.getWidth() - 140, this.getHeight() - 18);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
