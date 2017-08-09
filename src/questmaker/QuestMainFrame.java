package questmaker;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.swing.Box;

public class QuestMainFrame extends Frame implements ActionListener {

    QuestMainFrameCanvas qmfc;
    LinkedList<QuestBubble> questBubbles;
    QuestBubble tempQuestBubble;
    Button addQuestButton;
    Button Save;
    Button Load;

    public QuestMainFrame() {
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        }
        );

        questBubbles = new LinkedList<>();
        qmfc = new QuestMainFrameCanvas(questBubbles, this);
        this.add(qmfc);
        
        Panel P = new Panel();
        P.setBackground(Color.BLACK);

        addQuestButton = new Button("Pridaj Quest");
        addQuestButton.addActionListener(this);
        P.add(addQuestButton);
        P.add(Box.createRigidArea(new Dimension(350, 0)));
        Save = new Button("Uloz");
        Save.addActionListener(this);
        P.add(Save);
        
        Load = new Button("Nacitaj");
        Load.addActionListener(this);
        P.add(Load);
        
        this.add("South", P);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean test = true;
        if (e.getSource() == addQuestButton) {
            RequestTextDialog rtd = new RequestTextDialog(this, 300, 300);
            tempQuestBubble = new QuestBubble(rtd.textArea.getText(), 300, 300, Color.GREEN, null);
            questBubbles.add(tempQuestBubble);
            tempQuestBubble = null;
            qmfc.repaint();
        }
        else if (e.getSource() == Save) {
            FileDialog FD = new FileDialog(this, "Save as",FileDialog.SAVE);
            FD.setVisible(true);
          
            if(FD.getFile() != null) {
                try {
                    FileOutputStream fos=new FileOutputStream(FD.getDirectory() + FD.getFile());
                    try (ObjectOutputStream os = new ObjectOutputStream(fos)) {
                        SaveFile toSave = new SaveFile(qmfc.programStart, questBubbles);
                        os.writeObject(toSave);   
                    }
                }
                catch (IOException ex) {
                    
                    System.out.println(ex);
                }
            }
        } else if (e.getSource() == Load) {
            FileDialog FD = new FileDialog(this, "Load",FileDialog.LOAD);
            FD.setVisible(true);

            if (FD.getFile() != null) {
                
                try {
                FileInputStream fis=new FileInputStream(FD.getDirectory() + FD.getFile());
                ObjectInputStream is = new ObjectInputStream(fis);
                    try {
                        
                        SaveFile file= (SaveFile)is.readObject();
                        cleanupBeforeLoad();
                        questBubbles = file.questbubbles;
                        qmfc.questBubbles = file.questbubbles;
                        qmfc.programStart = file.programStart;
                        qmfc.repaint();
                        
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex);
                    }
                
                }
                catch (IOException ex) {
                    System.out.println(ex);
                } 
            }
        }
    }
    
    private void cleanupBeforeLoad() {
        questBubbles.clear();
        questBubbles = null;
        qmfc.programStart = null;
        qmfc.lastMouseOver = null;
        qmfc.questBubbles = null;
        qmfc.tempQuesInput = null;
        qmfc.mouseButtonHoldOnQuestBuble = false;
        qmfc.mouseButtonHoldOnQuestOutput = false;
        qmfc.showDeleteZone = false;
        qmfc.deleteInputLines = false;
        qmfc.deleteOutputLines = false;
        qmfc.mouseButtonHoldOnProgramStart = false;
    }
}
