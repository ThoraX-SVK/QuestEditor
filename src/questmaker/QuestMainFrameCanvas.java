package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class QuestMainFrameCanvas extends DoubleBuffer implements MouseListener,MouseMotionListener{

    LinkedList<QuestBubble> questBubbles;
    QuestBubble tempQuestBubble;
    QuestBubble lastMouseOver;
    boolean mouseButtonHold;
    
    public QuestMainFrameCanvas(LinkedList<QuestBubble> questBubbles) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.questBubbles = questBubbles;
        this.mouseButtonHold = false;
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

            }
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());
        if (tempQuestBubble == null)
            return;
        
        tempQuestBubble.bubbleColor = Color.RED;
        mouseButtonHold = true;
        this.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseButtonHold) {
            tempQuestBubble.bubbleColor = Color.BLUE;
            this.repaint();
        }
        mouseButtonHold = false;
        
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
        if (mouseButtonHold) {
            tempQuestBubble.posX = e.getX() - tempQuestBubble.bubbleSize/2;
            //NAHARDKODENE!!!!!!!!!
            tempQuestBubble.posY = e.getY() - 10;
            this.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
        if (!mouseButtonHold && !questBubbles.isEmpty()) {
            
            QuestBubble tempQuestBubble = selectBubbleOnMouseOver(e.getPoint());
            
            if (tempQuestBubble == null && lastMouseOver != null) {
                System.out.println("Nie som cez bubble");
                lastMouseOver.bubbleColor = Color.GREEN;
                lastMouseOver = null;
                this.repaint();
            }
            if(tempQuestBubble != null) {
                System.out.println("Som cez bubble");
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
                    System.out.println("Nieco to robi");
                }
               
            }  
        }
        
        return bestBubble;   
    }
    
    
    
}
