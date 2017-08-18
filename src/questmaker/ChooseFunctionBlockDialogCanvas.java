package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import sun.java2d.d3d.D3DRenderQueue;

/**
 *
 * @author Tom
 */
public class ChooseFunctionBlockDialogCanvas extends DoubleBuffer implements MouseListener {
    
    ChooseFunctionBlockDialog owner;
    FunctionBlockRandom blockRandom;
    FunctionBlockJoiner blockJoin;

    public ChooseFunctionBlockDialogCanvas(ChooseFunctionBlockDialog owner) {
        this.owner = owner;
        blockRandom = new FunctionBlockRandom(20, 10, 0, 20, Color.GRAY);
        blockRandom.addOutput();
        blockRandom.addOutput();
        blockRandom.update();
        blockJoin = new FunctionBlockJoiner(20, 60, 0, 20, Color.GRAY);
        blockJoin.update();
        
        this.addMouseListener(this);
    }
     
    @Override
    public void paintBuffer(Graphics g) {
        g.setFont(new Font("OCR A Extended", 1, 20));
        blockJoin.draw(g);
        blockRandom.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
        if (blockJoin.isMouseOver(e.getPoint())) {
            FunctionBlockJoiner tmp = new FunctionBlockJoiner(300, 300, 0, 20, Color.GRAY);
            tmp.update();
            owner.owner.qmfc.blockToDraw.add(tmp);
            owner.owner.qmfc.repaint();
        } else if (blockRandom.isMouseOver(e.getPoint())) {
            FunctionBlockRandom tmp = new FunctionBlockRandom(300, 300, 0, 20, Color.GRAY);
            owner.owner.qmfc.blockToDraw.add(tmp);
            owner.owner.qmfc.repaint();
        }
        
        owner.dispose();   
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
