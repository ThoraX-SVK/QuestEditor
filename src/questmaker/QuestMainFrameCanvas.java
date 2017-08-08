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
    LinkedList<LineOutputInput> lines;
    QuestBubble tempQuestBubble;
    QuestBubble lastMouseOver;
    QuestOutput tempQuestOutput;
    QuestInput tempQuesInput;
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
        this.lines = new LinkedList<>();
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
            }
        }

        if (!lines.isEmpty()) {
            for (LineOutputInput loi : lines) {
                loi.draw(g);
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
            } else if (tempQuestOutput != null && tempQuestOutput.goingToInput == null) {
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
                Set<LineOutputInput> toDelete = new HashSet<>();
                LinkedList<QuestOutput> llqo = new LinkedList<>();
                llqo.add(tempQuestOutput);
                toDelete.addAll(selectLinesContainingOutputs(llqo, lines));

                for (LineOutputInput loi : toDelete) {
                    lines.remove(loi);
                }

                this.repaint();
            } else if (tempQuesInput != null) {
                Set<LineOutputInput> toDelete = new HashSet<>();
                LinkedList<QuestInput> llqi = new LinkedList<>();
                llqi.add(tempQuesInput);
                toDelete.addAll(selectLinesContainingInputs(llqi, lines));

                for (LineOutputInput loi : toDelete) {
                    lines.remove(loi);
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

                Set<LineOutputInput> toDelete = new HashSet<>();
                toDelete.addAll(selectLinesContainingOutputs(tempQuestBubble.quest.outputs, lines));
                toDelete.addAll(selectLinesContainingInputs(tempQuestBubble.quest.inputs, lines));

                for (LineOutputInput loi : toDelete) {
                    lines.remove(loi);
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
            QuestInput qi = selectInputOnMouseOver(e.getPoint());
            if (qi != null) {
                LineOutputInput lineToCreate = new LineOutputInput(tempQuestOutput, qi);
                lines.add(lineToCreate);
                tempQuestOutput.setQi(qi);
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
            for (int i = 0; i < tempQuestBubble.quest.inputs.size(); i++) {
                tempQuestBubble.quest.inputs.get(i).updatePosition();
            }
            for (int i = 0; i < tempQuestBubble.quest.outputs.size(); i++) {
                tempQuestBubble.quest.outputs.get(i).updatePosition();
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

    private QuestOutput selectOutputOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty()) {
            return null;
        }

        QuestBubble actualQuestBubble;
        QuestOutput qo;

        for (int i = 0; i < questBubbles.size(); i++) {
            actualQuestBubble = questBubbles.get(i);
            for (int j = 0; j < actualQuestBubble.quest.outputs.size(); j++) {
                qo = actualQuestBubble.quest.outputs.get(j);
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

        QuestBubble actualQuestBubble;
        QuestInput qi;

        for (int i = 0; i < questBubbles.size(); i++) {
            actualQuestBubble = questBubbles.get(i);
            for (int j = 0; j < actualQuestBubble.quest.inputs.size(); j++) {
                qi = actualQuestBubble.quest.inputs.get(j);
                if (qi.MouseOverlaps(mouseCursor)) {
                    return qi;
                }
            }
        }
        return null;
    }

    private Set<LineOutputInput> selectLinesContainingOutputs(LinkedList<QuestOutput> qos,
            LinkedList<LineOutputInput> lines) {

        Set<LineOutputInput> toSelect = new HashSet<>();

        for (LineOutputInput loi : lines) {
            for (QuestOutput qo : qos) {
                if (loi.deleteIfContainsAtLeastOne(null, qo)) {
                    toSelect.add(loi);
                }
            }
        }

        return toSelect;
    }

    private Set<LineOutputInput> selectLinesContainingInputs(LinkedList<QuestInput> qis,
            LinkedList<LineOutputInput> lines) {

        Set<LineOutputInput> toSelect = new HashSet<>();

        for (LineOutputInput loi : lines) {
            for (QuestInput qi : qis) {
                if (loi.deleteIfContainsAtLeastOne(qi, null)) {
                    toSelect.add(loi);
                }
            }
        }

        return toSelect;
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
