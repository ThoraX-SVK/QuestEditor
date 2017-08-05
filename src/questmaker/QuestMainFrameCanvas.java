package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

public class QuestMainFrameCanvas extends DoubleBuffer implements MouseListener,MouseMotionListener {
    
    QuestMainFrame owner;
    LinkedList<QuestBubble> questBubbles;
    LinkedList<LineOutputInput> lines;
    QuestBubble tempQuestBubble;
    QuestBubble lastMouseOver;
    QuestOutput tempQuestOutput;
    Point tempLineEnd;
    boolean mouseButtonHoldOnQuestBuble;
    boolean mouseButtonHoldOnQuestOutput;
    
    public QuestMainFrameCanvas(LinkedList<QuestBubble> questBubbles, QuestMainFrame qmf) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.questBubbles = questBubbles;
        this.lines = new LinkedList<>();
        this.mouseButtonHoldOnQuestBuble = false;
        this.mouseButtonHoldOnQuestOutput = false;
    }
    
    @Override
    public void paintBuffer(Graphics g) {
        if (questBubbles.size() != 0) {
            for (int i = 0; i < questBubbles.size(); i++) {
                QuestBubble qb = questBubbles.get(i);
                g.setColor(qb.bubbleColor);
                g.setFont(new Font("OCR A Extended", 1, 20));
                //pozor na MouseOverlapsSquare() v QuestBubble, hodnota 20 je tam nahardkodena
                g.drawRect(qb.posX, qb.posY, qb.bubbleSize, 20); 
                g.drawString(qb.questName, qb.posX, qb.posY+18);
                
                if (!qb.quest.inputs.isEmpty()) {     
                    for (int j = 0; j < qb.quest.inputs.size(); j++) {
                        QuestInput qi = qb.quest.inputs.get(j);
                        g.setColor(qi.color);
                        g.drawRect(qi.posX, qi.posY, qi.size, qi.size);
                    }  
                }
                
                if (!qb.quest.outputs.isEmpty()) {
                    for (int j = 0; j < qb.quest.outputs.size(); j++) {
                        QuestOutput qi = qb.quest.outputs.get(j);
                        g.setColor(qi.color);
                        g.drawRect(qi.posX, qi.posY, qi.size, qi.size);
                    }  
                }
                
                if (tempQuestOutput != null) {
                    g.setColor(Color.ORANGE);
                    g.drawLine(tempQuestOutput.posX+5, tempQuestOutput.posY+5,
                            tempLineEnd.x, tempLineEnd.y);
                }
            }
        }
        
        if (!lines.isEmpty()) {
            for (int i = 0; i < lines.size(); i++) {
                LineOutputInput lineIO = lines.get(i);
                g.setColor(Color.WHITE);
                g.drawLine(lineIO.qo.posX+5, lineIO.qo.posY+5,lineIO.qi.posX+5, lineIO.qi.posY+5);

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
            tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());
            tempQuestOutput = selectOutputOnMouseOver(e.getPoint());
            if (tempQuestBubble == null && tempQuestOutput == null)
                return;

            if (tempQuestBubble != null) {
                tempQuestBubble.bubbleColor = Color.RED;
                mouseButtonHoldOnQuestBuble = true;
                this.repaint();
            }
            else if (tempQuestOutput != null && tempQuestOutput.goingToInput == null) {
                tempQuestOutput.color = Color.WHITE;
                mouseButtonHoldOnQuestOutput = true;
                this.repaint();
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());
            
            if (tempQuestBubble != null) {
                EditQuestDialog eqd = new EditQuestDialog(owner, tempQuestBubble.quest);
            }
            
        }
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseButtonHoldOnQuestBuble) {
            tempQuestBubble.bubbleColor = Color.BLUE;
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
        
        
        mouseButtonHoldOnQuestOutput = false;
        mouseButtonHoldOnQuestBuble = false;     
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
        if (mouseButtonHoldOnQuestBuble) {
            tempQuestBubble.posX = e.getX() - tempQuestBubble.bubbleSize/2;
            tempQuestBubble.posY = e.getY() - 10;   //NAHARDKODENE!!!!!!!!!
            for (int i = 0; i < tempQuestBubble.quest.inputs.size(); i++) {
                tempQuestBubble.quest.inputs.get(i).updatePosition();
            }
            for (int i = 0; i < tempQuestBubble.quest.outputs.size(); i++) {
                tempQuestBubble.quest.outputs.get(i).updatePosition();
            }
            this.repaint();
        }
        else if (mouseButtonHoldOnQuestOutput) {
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
            if(tempQuestBubble != null) {
                lastMouseOver = tempQuestBubble;
                tempQuestBubble.bubbleColor = Color.BLUE;
                this.repaint();
            }
            
        }
        
        
    }
    
    private QuestBubble selectBubbleOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty())
                return null;
        
        QuestBubble bestBubble = null;
        double distanceToMiddle = Double.MAX_VALUE;
        double tmpdist;
        for (int i = 0; i < questBubbles.size(); i++) {
            tempQuestBubble = questBubbles.get(i);
            if (tempQuestBubble.MouseOverlaps(mouseCursor)) {
                //opat 20 je nahardkodene, pozor na to!!!!
                tmpdist = Point.distance(tempQuestBubble.posX + tempQuestBubble.bubbleSize/2,
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
        if (questBubbles.isEmpty())
            return null;
        
        QuestBubble actualQuestBubble;
        QuestOutput qo;
        
        for (int i = 0; i < questBubbles.size(); i++) {
            actualQuestBubble = questBubbles.get(i);
            for (int j = 0; j < actualQuestBubble.quest.outputs.size(); j++) {
                qo = actualQuestBubble.quest.outputs.get(j);
                if (qo.MouseOverlaps(mouseCursor))
                    return qo;
            }
        }
        return null;
    }
    
    private QuestInput selectInputOnMouseOver(Point mouseCursor) {
        if (questBubbles.isEmpty())
            return null;
        
        QuestBubble actualQuestBubble;
        QuestInput qi;
        
        for (int i = 0; i < questBubbles.size(); i++) {
            actualQuestBubble = questBubbles.get(i);
            for (int j = 0; j < actualQuestBubble.quest.inputs.size(); j++) {
                qi = actualQuestBubble.quest.inputs.get(j);
                if (qi.MouseOverlaps(mouseCursor))
                    return qi;
            }
        }
        return null;
    }
    
    
}
