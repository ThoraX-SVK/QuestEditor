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

public class QuestMainFrameCanvas extends DoubleBuffer implements MouseListener, MouseMotionListener {

    QuestMainFrame owner;
    LinkedList<QuestBubble> questBubbles;
    QuestBubble tempQuestBubble;
    QuestBubble lastMouseOver;
    QuestOutputNew tempQuestOutput;
    QuestInputNew tempQuesInput;
    Point tempLineEnd;
    boolean mouseButtonHoldOnQuestBuble;
    boolean mouseButtonHoldOnQuestOutput;
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
    }

    @Override
    public void paintBuffer(Graphics g) {

        g.setFont(new Font("OCR A Extended", 1, 20));
        if (showDeleteZone) {
            drawDeleteZone(g);
        }

        if (tempLineEnd != null) {
            g.setColor(Color.ORANGE);
            g.drawLine(tempQuestOutput.posX + 5, tempQuestOutput.posY + 5,
                    tempLineEnd.x, tempLineEnd.y);
        }
        

        if (!questBubbles.isEmpty()) {
            for (QuestBubble qb : questBubbles) {
                qb.draw(g);
                for (QuestOutputNew qo : qb.quest.outputs) {
                    if (qo.target != null)
                        drawLine(qo, qo.target, g);
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
                return;
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
                    for (QuestOutputNew qo : qb.quest.outputs) {
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

                for (QuestInputNew qi: tempQuestBubble.quest.inputs) {
                    for (QuestBubble qb : questBubbles) {
                        for (QuestOutputNew qo : qb.quest.outputs) {
                            if (qo.target == qi) {
                                qo.target = null;
                            }
                        }
                    }
                }
                
                for (QuestOutputNew qo: tempQuestBubble.quest.outputs) {
                    qo.target = null;
                }
 
                tempQuestBubble.delete();
                questBubbles.remove(tempQuestBubble);
            } else {
                tempQuestBubble.bubbleColor = Color.BLUE;
            }

            this.repaint();
        }
        if (mouseButtonHoldOnQuestOutput) {
            tempQuestOutput.color = Color.BLUE;
            QuestInputNew qi = selectInputOnMouseOver(e.getPoint());
            if (qi != null) {
                tempQuestOutput.target = qi;
            }
            
            tempQuestOutput = null;
            this.repaint();
        }

        tempLineEnd = null;
        showDeleteZone = false;
        mouseButtonHoldOnQuestOutput = false;
        mouseButtonHoldOnQuestBuble = false;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseButtonHoldOnQuestBuble) {
            tempQuestBubble.posX = e.getX() - tempQuestBubble.bubbleSize / 2;
            tempQuestBubble.posY = e.getY() - 10;   //NAHARDKODENE!!!!!!!!!
            for (QuestInputNew qi : tempQuestBubble.quest.inputs) {
                qi.upadatePosition();
            }
            for (QuestOutputNew qo : tempQuestBubble.quest.outputs) {
                qo.upadatePosition();
            }
            this.repaint();
        } else if (mouseButtonHoldOnQuestOutput) {
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

    private QuestOutputNew selectOutputOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty()) {
            return null;
        }

        for (QuestBubble qb : questBubbles) {
            for (QuestOutputNew qo : qb.quest.outputs) {
                if (qo.MouseOverlaps(mouseCursor)) {
                    return qo;
                }
            }
        }
        return null;
    }

    private QuestInputNew selectInputOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty()) {
            return null;
        }

        for (QuestBubble qb : questBubbles) {
            for (QuestInputNew qi : qb.quest.inputs) {
                if (qi.MouseOverlaps(mouseCursor)) {
                    return qi;
                }
            }
        }
        return null;
    }
    
    private void drawLine(QuestOutputNew qo,QuestInputNew qi, Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(qo.posX+5, qo.posY+5, qi.posX+5, qi.posY+5);
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
