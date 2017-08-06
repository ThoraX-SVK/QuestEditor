package questmaker;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;

public class QuestMainFrame extends Frame implements ActionListener{
    
    QuestMainFrameCanvas qmfc;
    LinkedList<QuestBubble> questBubbles;
    QuestBubble tempQuestBubble;
    Button addQuestButton;
    
    public QuestMainFrame() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter ()
                                {   public void windowClosing(WindowEvent e) {
                                    
                                    System.exit(0);
                                    }
                                }
        );
        
        questBubbles = new LinkedList<>();
        qmfc = new QuestMainFrameCanvas(questBubbles,this);
        this.add(qmfc);
        
        Panel P = new Panel();
        P.setBackground(Color.BLACK);
        
        addQuestButton = new Button("Add Quest Bubble");
        addQuestButton.addActionListener(this);
        P.add(addQuestButton);
        this.add("South",P);
        
        
        this.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean test = true;
        if (e.getSource() == addQuestButton) { 
            RequestTextDialog rtd = new RequestTextDialog(this,300,300);
            tempQuestBubble = new QuestBubble(rtd.textArea.getText(), 300, 300, Color.GREEN, null);
            questBubbles.add(tempQuestBubble);
            tempQuestBubble = null;
            qmfc.repaint();
        }        
    }   
}

    
