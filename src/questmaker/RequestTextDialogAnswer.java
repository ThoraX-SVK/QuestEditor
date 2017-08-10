package questmaker;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Timotej
 */

public class RequestTextDialogAnswer extends Dialog implements ActionListener {
    
    TextArea textArea;
    String lastText;
    Button bConfirm;
    
    public RequestTextDialogAnswer(QuestMainFrame owner, String lastText, int width, int height) {
 
        super(owner);
        this.setSize(width, height);
        this.lastText = lastText;
        this.setModal(true);
        
        
        bConfirm = new Button("OK");
        bConfirm.addActionListener(this);
        Panel panel = new Panel();
        panel.add(bConfirm);
        this.add("South", panel);
        

        textArea = new TextArea(lastText);
        textArea.setFocusable(true);
        this.add(textArea);
  
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textArea.setText("");
            }
        });
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if ("".equals(textArea.getText())) {
                    textArea.setText("null"); 
                }
                textArea.setText(lastText);
                dispose();
            }
        }
        );
        
        this.setVisible(true);
         
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bConfirm) {
            if ("".equals(textArea.getText())) {
                textArea.setText("null");
            }
            this.dispose();
        }
    }
    
 
}