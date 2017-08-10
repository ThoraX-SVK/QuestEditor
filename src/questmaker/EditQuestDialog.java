package questmaker;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;

/**
 *
 * @author Tom
 */
public class EditQuestDialog extends Dialog implements ActionListener {

    EditQuestDialogCanvas eqdc;
    Quest quest;
    Button addInput;
    Button addOutput;
    Button setName;
    Button addNewDecision;

    public EditQuestDialog(QuestMainFrame owner, Quest quest) {
        super(owner);
        this.setModal(true);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.quest = quest;

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                dispose();
            }
        }
        );

        Panel P = new Panel();

        addInput = new Button("Pridaj Input");
        addInput.addActionListener(this);
        addOutput = new Button("Pridaj Output");
        addOutput.addActionListener(this);
        setName = new Button("Zmeniť názov");
        setName.addActionListener(this);
        addNewDecision = new Button("Pridaj nové rozhodnutie");
        addNewDecision.addActionListener(this);

        P.add(addInput);
        P.add(Box.createRigidArea(new Dimension(50, 0)));
        P.add(setName);
        P.add(addNewDecision);
        P.add(Box.createRigidArea(new Dimension(300, 0)));
        P.add(addOutput);
        P.setBackground(Color.BLACK);
        this.add("South", P);

        eqdc = new EditQuestDialogCanvas(quest);
        this.add(eqdc);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addInput) {          
            quest.addInput();
            eqdc.repaint();
        } else if (e.getSource() == addOutput) {
            quest.addOutput();
            eqdc.repaint();
        } else if (e.getSource() == setName) {
            RequestTextDialog rtd = new RequestTextDialog(null, 300, 200);
            quest.questBubble.questName = rtd.textArea.getText();
            quest.questBubble.update();
        } else if (e.getSource() == addNewDecision) {
            RequestTextDialog rtd = new RequestTextDialog(null, 300, 200);
            Decision decision = new Decision("", rtd.textArea.getText(), 300, 300, Color.CYAN);
            quest.decisions.add(decision);
            eqdc.repaint();
        }
    }

}
