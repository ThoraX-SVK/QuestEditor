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

public class EditQuestDialogCanvas extends DoubleBuffer implements MouseListener,MouseMotionListener {

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
        if (drawDeleteZone)
            drawDeleteZone(g);
        
        if (tempLineEnd != null) {
            g.setColor(Color.YELLOW);
            if (tempAnswerOutput != null)
                g.drawLine(tempAnswerOutput.posX+5, tempAnswerOutput.posY+5,
                        tempLineEnd.x, tempLineEnd.y);
            else if (tempQuestInput != null)
                g.drawLine(15, quest.inputs.indexOf(tempQuestInput)*33 + 16,
                        tempLineEnd.x,tempLineEnd.y);
        }
        
        if (!quest.lines.isEmpty()) {
            for (int i = 0; i < quest.lines.size(); i++) {
                LineAnswerDecision lad = quest.lines.get(i);
                g.setColor(Color.WHITE);
                if (lad.di != null && lad.ao != null)
                    g.drawLine(lad.ao.posX+5, lad.ao.posY+5, lad.di.posX+5, lad.di.posY+5);
                else if (lad.qo != null && lad.ao != null)
                    g.drawLine(lad.ao.posX+5, lad.ao.posY+5, this.getWidth() - 16, 33*quest.outputs.indexOf(lad.qo) + 16);
                else if (lad.qi != null && lad.di != null)
                    g.drawLine(15, 33*quest.inputs.indexOf(lad.qi)+15, lad.di.posX+5, lad.di.posY+5);
                  
                    
            }
        }
        
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
                de.plusSign.draw(g);
                
                g.setColor(Color.RED);
                g.drawRect(de.decisionInput.posX, de.decisionInput.posY,
                        de.decisionInput.size, de.decisionInput.size);
                
                for (int j = 0; j < de.answers.size(); j++) {
                    Answer ans = de.answers.get(j);
                    g.setColor(Color.YELLOW);
                    g.drawRect(ans.posX, ans.posY, ans.size, 20);
                    g.drawString(ans.popis, ans.posX, ans.posY + 18);
                    
                    g.setColor(ans.output.color);
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
        tempAnswerOutput = selectAnswerOutputOnMouseOver(e.getPoint());
        DecisionAddNewAnswerSign plusSign = selectPlusSign(e.getPoint());
        Answer an = selectAnswerOnMouseOver(e.getPoint());
        tempQuestInput = selectQuestInputOnMouseOver(e.getPoint());
        
        if (tempDecision == null && tempAnswerOutput == null && plusSign == null
                && an == null && tempQuestInput == null)
                return;
        
        if (e.getButton() == MouseEvent.BUTTON1) {          
            if (tempDecision != null) {
                drawDeleteZone = true;
                mouseHoldDecisionSelected = true;
                tempDecision.color = Color.RED;
                this.repaint();
            }
            else if (tempAnswerOutput != null && tempAnswerOutput.goingToDecision == null
                    && tempAnswerOutput.goingToQuestOutput == null) {
                mouseHoldAnswerOutputSelected = true;
                tempAnswerOutput.color = Color.WHITE;
                this.repaint();
            }
            else if (plusSign != null) {
                plusSign.belongsTo.addAnswer("nova odpoved");
                plusSign.belongsTo.updateAfterNewAnswer();
                this.repaint();   
            }
            else if (tempQuestInput != null) {
                mouseHoldOnQuestInputSelected = true;
                tempQuestInput.color = Color.WHITE;
                this.repaint();              
            }
        }
        else if (e.getButton() == MouseEvent.BUTTON3) {
            if (tempDecision != null) {
                DecisionEditDialog ded = new DecisionEditDialog(null, tempDecision);
                this.repaint();
            }
            else if (an != null) {
                RequestTextDialog rtg = new RequestTextDialog(null, 300, 200);
                an.popis = rtg.textArea.getText();   
                this.repaint();
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
                LineAnswerDecision tempLineToAdd = new LineAnswerDecision(tempAnswerOutput, di);
                quest.lines.add(tempLineToAdd);
                tempAnswerOutput.goingToDecision = di.inputToDecision;               
            }
            else if (qo != null) {
                LineAnswerDecision tempLineToAdd = new LineAnswerDecision(tempAnswerOutput, qo);
                quest.lines.add(tempLineToAdd);
                tempAnswerOutput.goingToQuestOutput = qo;
            }
        }
        else if (tempQuestInput != null) {
            tempQuestInput.color = Color.RED;
            DecisionInput di = selectDecisionInputOnMouseOver(e.getPoint());
            LineAnswerDecision lad = new LineAnswerDecision(di, tempQuestInput);
            quest.lines.add(lad);  
        }
        else if (tempDecision != null) {
            if (e.getX() > this.getWidth() - 160 && e.getX() < this.getWidth() - 40 &&
                    e.getY() > this.getHeight() - 40 && e.getY() < this.getHeight() - 10) {
                
                Set<LineAnswerDecision> toDelete = new HashSet<>();
                LinkedList<AnswerOutput> toSend = new LinkedList<>();
                for (Answer a : tempDecision.answers)
                    toSend.add(a.output);
                toDelete.addAll(selectLinesContainingAnswerOutput(toSend, quest.lines));
                toDelete.addAll(selectLinsContainingDecisionInput(tempDecision.decisionInput, quest.lines));
                
                for (LineAnswerDecision lad : toDelete)
                    lad.delete();
                
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
        }
        else if (mouseHoldAnswerOutputSelected || mouseHoldOnQuestInputSelected) {
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
        g.drawString("Delete",this.getWidth() - 140, this.getHeight() - 18);
    }
    
    private Decision selectDecisionOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty())
            return null;
        
        Decision de = null;
        for (int i = 0; i < quest.decisions.size(); i++) {
            de = quest.decisions.get(i);
            if (de.MouseOverlaps(mouseCursor))
                return de;
        }
        
        return null;
    }
    
    private AnswerOutput selectAnswerOutputOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty())
            return null;
        
        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            for (int j = 0; j < de.answers.size() ;j++) {
                Answer an = de.answers.get(j);
                if (an.output.MouseOverlaps(mouseCursor))
                    return an.output;       
            }  
        }
        return null;
    }  
    
    private DecisionInput selectDecisionInputOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty())
            return null;
        
        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            if (de.decisionInput.MouseOverlaps(mouseCursor))
                return de.decisionInput;
        }
        return null;
    }  
    
    private DecisionAddNewAnswerSign selectPlusSign(Point mouseCursor) {
        if (quest.decisions.isEmpty())
            return null;
        
        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            if (de.plusSign.MouseOverlaps(mouseCursor))
                return de.plusSign;
        }
        return null;
        
    }
    
   private Answer selectAnswerOnMouseOver(Point mouseCursor) {
        if (quest.decisions.isEmpty())
            return null;
        
        for (int i = 0; i < quest.decisions.size(); i++) {
            Decision de = quest.decisions.get(i);
            for (int j = 0; j < de.answers.size() ;j++) {
                Answer an = de.answers.get(j);
                if (an.MouseOverlaps(mouseCursor))
                    return an;       
            }  
        }
        return null;
    }
    
   private QuestOutput selectQuestOutputOnMouseOver(Point mouseCursor) {
       if (quest.outputs.isEmpty())
           return null;
       
       for (int i = 0; i < quest.outputs.size(); i++) {
           int posX = this.getWidth() - 30;
           int posY = i*33;
           int size = 30;
           
           if (mouseCursor.x > posX && mouseCursor.x < posX + size &&
                   mouseCursor.y > posY && mouseCursor.y < posY + size)
               return quest.outputs.get(i); 
       }
       return null;
   }
   
   private QuestInput selectQuestInputOnMouseOver(Point mouseCursor) {
       if (quest.inputs.isEmpty())
           return null;
       
       for (int i = 0; i < quest.inputs.size(); i++) {
           int posX = 0;
           int posY = i*33;
           int size = 30;
           
           if (mouseCursor.x > posX && mouseCursor.x < posX + size &&
                   mouseCursor.y > posY && mouseCursor.y < posY + size)
               return quest.inputs.get(i); 
       }
       return null;
   }
   
   private Set<LineAnswerDecision> selectLinesContainingAnswerOutput(LinkedList<AnswerOutput> aos, 
                                                                        LinkedList<LineAnswerDecision> lines) {
       Set<LineAnswerDecision> toSelect = new HashSet<LineAnswerDecision>();
       
       for (LineAnswerDecision lad : lines) {
           for (AnswerOutput ao : aos) {
               if (lad.ao == ao)
                   toSelect.add(lad);
           }
       }
       return toSelect;
   }
   
   private Set<LineAnswerDecision> selectLinsContainingDecisionInput(DecisionInput di,
                                                                        LinkedList<LineAnswerDecision> lines) {
       Set<LineAnswerDecision> toSelect = new HashSet<LineAnswerDecision>();
       
       for (LineAnswerDecision lad : lines) {
               if (lad.di == di)
                   toSelect.add(lad);
       }
       return toSelect;
       
       
   }
   
}
