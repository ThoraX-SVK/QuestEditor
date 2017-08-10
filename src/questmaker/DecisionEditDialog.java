package questmaker;

import java.awt.Button;
import java.awt.Dialog;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import javax.swing.BoxLayout;

/**
 *
 * @author Tom
 */
public class DecisionEditDialog extends Dialog implements ActionListener {

    Decision decision;
    Button ok;

    TextField popis;
    TextArea question;
    LinkedList<TextField> answers;

    public DecisionEditDialog(QuestMainFrame owner, Decision decision) {
        super(owner);
        this.decision = decision;
        this.answers = new LinkedList<>();
        this.setSize(400, decision.answers.size() * 50 + 300);
        this.setModal(true);
        this.setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                dispose();
            }
        }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        popis = new TextField(decision.popis);
        popis.setSize(400, 50);

        question = new TextArea(decision.question);
        question.setSize(400, 100);

        Label popisLabel = new Label("Popis rozhodnutia:");
        Label questionLabel = new Label("Otázka:");             // ine stringy? tieto su take divne
        this.add(popisLabel); 
        this.add(popis);
        this.add(questionLabel);
        this.add(question);

        for (int i = 0; i < decision.answers.size(); i++) {
            Label tempLabel = new Label("Možnosť " + (i + 1) + ".:");
            TextField tf = new TextField(decision.answers.get(i).answer);
            answers.add(tf);
            tf.setSize(400, 50);
            this.add(tempLabel);
            this.add(tf);
        }

        ok = new Button("Potvrdiť zmeny");
        ok.addActionListener(this);
        ok.setSize(400, 50);
        this.add(ok);

        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == ok) {
            decision.popis = popis.getText();
            decision.question = question.getText();
            for (int i = 0; i < answers.size(); i++) {
                decision.answers.get(i).answer = answers.get(i).getText();
            }
            decision.update();
            dispose();
        }
    }

}
