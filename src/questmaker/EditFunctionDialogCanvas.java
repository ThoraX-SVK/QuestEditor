package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelListener;

/**
 *
 * @author Tom
 */
public class EditFunctionDialogCanvas extends DoubleBuffer implements MouseListener {
    
    EditFunctionDialog owner;
    
    public EditFunctionDialogCanvas(EditFunctionDialog owner) {
        this.owner = owner;
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
    }
    
    public void paintBuffer(Graphics g) {
        
        g.setFont(new Font("OCR A Extended", 1, 20));
        
        for (_FunctionOutput qo : owner.fbl.outputs) {
            g.setColor(Color.WHITE);
         
            g.drawRect(400, 100 + owner.fbl.outputs.indexOf(qo)*25, 
                    Double.toString(qo.probability).length()*13, 20);
            g.drawString(Double.toString(qo.probability), 400, 100 + owner.fbl.outputs.indexOf(qo)*25 + 18);
            
            g.drawLine(15, 15, 400, 100 + owner.fbl.outputs.indexOf(qo)*25);
            g.drawLine(400 + Double.toString(qo.probability).length()*13,
                    100 + owner.fbl.outputs.indexOf(qo)*25, this.getWidth() - 15,
                    15 + 33*owner.fbl.outputs.indexOf(qo));
            
            g.drawRect(this.getWidth() - 30, 0 + 33*owner.fbl.outputs.indexOf(qo), 30, 30);
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
        for (_FunctionOutput qo : owner.fbl.outputs) {
             if (isMouseOver(e.getPoint(), 400, 100 + owner.fbl.outputs.indexOf(qo)*25,
                     Double.toString(qo.probability).length()*13, 20)) {
                 
                 RequestTextDialog rtg = new RequestTextDialog(null, "", 300, 300);
                 try {
                 qo.probability = Double.parseDouble(rtg.textArea.getText());
                 this.repaint();
                 }
                 catch (NumberFormatException ex) {
                     
                 }
                 
             }
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
    
    public boolean isMouseOver(Point mousePosition, int posX, int posY, int width, int height) {
        return mousePosition.x > posX && mousePosition.x < posX + width
                && mousePosition.y > posY && mousePosition.y < posY + height;
    }
    
    
}
