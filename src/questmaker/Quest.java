package questmaker;

import java.awt.Color;
import java.util.LinkedList;

public class Quest {

    static int inputId = 0;
    static int outputId = 0;
    LinkedList<QuestInputNew> inputs;
    LinkedList<QuestOutputNew> outputs;
    LinkedList<Decision> decisions;
    QuestBubble questBubble;

    public Quest(QuestBubble questBubble) {
        this.questBubble = questBubble;
        inputs = new LinkedList<>();
        outputs = new LinkedList<>();
        decisions = new LinkedList<>();
    }

    public void addInput() {
        inputs.add(new QuestInputNew(this, 10, Color.RED));
        inputs.getLast().upadatePosition();     
    }

    public void addOutput() {
       outputs.add(new QuestOutputNew(this, 10, Color.BLUE));
       outputs.getLast().upadatePosition();
    }

    public void delete() {
        inputs.clear();
        outputs.clear();
        decisions.clear();
    }

}
