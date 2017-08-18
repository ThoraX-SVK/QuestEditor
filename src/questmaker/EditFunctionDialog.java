package questmaker;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Tom
 */
public class EditFunctionDialog extends Dialog implements ActionListener {
    
    FunctionBlock fbl;
    EditFunctionDialogCanvas canvas;
    Button addOutput;

    public EditFunctionDialog(QuestMainFrame owner, FunctionBlock fbl) {
        super(owner);
        this.setModal(true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.fbl = fbl;
        this.canvas = new EditFunctionDialogCanvas(this);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                dispose();
            }
        }
        );
        
        Panel P = new Panel();
        P.setBackground(Color.BLACK);
        
        addOutput = new Button("Pridaj Output");
        addOutput.addActionListener(this);
        P.add(addOutput);
        
        this.add("South",P);
        this.add(canvas);
        
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == addOutput) {
            fbl.addOutput();
            fbl.outputs.getLast().updatePosition();
            canvas.repaint();
        }
        
    }
    
    
    
    
}
