package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class EditQuestDialogCanvas extends DoubleBuffer implements MouseListener,MouseMotionListener {

    Quest quest;
    Decision tempDecision;
    Decision lastDecisionMouseOver;
    boolean mouseHoldDecisionSelected;
    
    
    public EditQuestDialogCanvas(Quest quest) {
        this.quest = quest;
        this.mouseHoldDecisionSelected = false;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.setBackground(Color.BLACK);
    }
    
    @Override
    public void paintBuffer(Graphics g) {
        
        if (!quest.inputs.isEmpty()) {
            for (int i = 0; i < quest.inputs.size(); i++) {
                QuestInput qi = quest.inputs.get(i);
                g.setColor(Color.RED);
                g.drawRect(0, 33*i, 30, 30);
            }
        }
        
        if (!quest.outputs.isEmpty()) {
            for (int i = 0; i < quest.outputs.size(); i++) {
                QuestOutput qo = quest.outputs.get(i);
                g.setColor(Color.BLUE);
                g.drawRect(this.getWidth()-30, 33*i, 30, 30);
            }
        }
        
        if (!quest.decisions.isEmpty()) {
            for (int i = 0; i < quest.decisions.size(); i++) {
                Decision de = quest.decisions.get(i);
                g.setColor(de.color);
                g.setFont(new Font("OCR A Extended", 1, 20));
                g.drawRect(de.posX, de.posY, de.size, 20);
                g.drawString(de.popis, de.posX, de.posY + 18);
                
                g.setColor(Color.RED);
                g.drawRect(de.decisionInput.posX, de.decisionInput.posY,
                        de.decisionInput.size, de.decisionInput.size);
                
                for (int j = 0; j < de.answers.size(); j++) {
                    Answer ans = de.answers.get(j);
                    g.setColor(Color.YELLOW);
                    g.drawRect(ans.posX, ans.posY, ans.size, 20);
                    
                    g.setColor(Color.BLUE);
                    g.drawRect(ans.output.posX, ans.output.posY,
                            ans.output.size, ans.output.size);
                }
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
        
        if (tempDecision == null)
            return;
        
        mouseHoldDecisionSelected = true;
        tempDecision.color = Color.RED;
        this.repaint();
       
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        
        mouseHoldDecisionSelected = false;
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
            tempDecision.posX = e.getX() - tempDecision.size/2;
            tempDecision.posY = e.getY() - 10;
            tempDecision.decisionInput.updatePosition();
            
            for (int i = 0; i < tempDecision.answers.size(); i++)
                tempDecision.answers.get(i).updatePosition();
            
            
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
    
    private Decision selectDecisionOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty())
            return null;
        
        System.out.println("Ay som tu");
        
        Decision de = null;
        for (int i = 0; i < quest.decisions.size(); i++) {
            de = quest.decisions.get(i);
            if (de.MouseOverlaps(mouseCursor))
                return de;
        }
        
        return null;
    }
    
}
