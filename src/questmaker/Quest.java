package questmaker;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

public class Quest implements Serializable {

    static int inputId = 0;
    static int outputId = 0;
    LinkedList<QuestInput> inputs;
    LinkedList<QuestOutput> outputs;
    LinkedList<Decision> decisions;
    QuestBubble questBubble;

    public Quest(QuestBubble questBubble) {
        this.questBubble = questBubble;
        inputs = new LinkedList<>();
        outputs = new LinkedList<>();
        decisions = new LinkedList<>();
    }

    public void addInput() {
        inputs.add(new QuestInput(this, 10, Color.RED));
        inputs.getLast().upadatePosition();     
    }

    public void addOutput() {
       outputs.add(new QuestOutput(this, 10, Color.BLUE));
       outputs.getLast().upadatePosition();
    }

    public void delete() {
        inputs.clear();
        outputs.clear();
        decisions.clear();
    }

}
