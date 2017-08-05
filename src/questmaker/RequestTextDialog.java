package questmaker;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Tom
 */
public class RequestTextDialog extends Dialog implements ActionListener{
    
    Button confirm;
    TextArea textArea;
    
    public RequestTextDialog(QuestMainFrame owner,int width, int height) {
        super(owner);
        this.setModal(true);
        this.setSize(300, 200);
        
        this.setSize(width, height);
        this.setLocationRelativeTo(null);
        
        Panel P = new Panel();
        
        textArea = new TextArea("Insert text here");
        this.add(textArea);
       
        confirm = new Button("Okay");
        confirm.addActionListener(this);
        P.add(confirm);
        this.add("South",P);
        
        this.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {  
            this.dispose();   
        }
    }
    
}
