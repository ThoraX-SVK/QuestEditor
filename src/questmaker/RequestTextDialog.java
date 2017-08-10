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
 * @author Tom
 */
public class RequestTextDialog extends Dialog implements ActionListener {

   
    Button confirm;
    TextArea textArea; 

    public RequestTextDialog(QuestMainFrame owner, int width, int height) {
  
        super(owner);
        this.setModal(true);
        this.setSize(300, 200);
        this.setSize(width, height);

        this.setLocationRelativeTo(null);

        Panel P = new Panel();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if ("".equals(textArea.getText())) {
                    textArea.setText("null"); 
                }
                
                
                dispose();
            }
        }
        );

      
        textArea = new TextArea("Insert text here" );
  
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                textArea.setText("");
            }
        });
        textArea.setFocusable(true);
        this.add(textArea);

        confirm = new Button("Okay");
        confirm.addActionListener(this);
        P.add(confirm);
        this.add("South", P);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirm) {
            if ("".equals(textArea.getText())) {
                textArea.setText("null");
            }
            this.dispose();
        }
    }

}
