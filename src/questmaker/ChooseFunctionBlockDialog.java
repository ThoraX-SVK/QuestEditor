package questmaker;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Tom
 */
public class ChooseFunctionBlockDialog extends Dialog {
    
    QuestMainFrame owner;
    ChooseFunctionBlockDialogCanvas canvas;
    
    public ChooseFunctionBlockDialog(QuestMainFrame owner) {
        super(owner);
        this.owner = owner;
        this.setModal(true);
        this.setSize(300, 300);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setBackground(Color.BLACK);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                dispose();
            }
        }
        );
        
        canvas = new ChooseFunctionBlockDialogCanvas(this);
        this.add(canvas);
        
        this.setVisible(true);
    }
    
    
    
    
}
