package questmaker;

import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;

public class Quest implements Serializable {

    static final long serialVersionUID = 42L;
    static int inputId = 0;
    static int outputId = 0;
    LinkedList<_QuestInput> inputs;
    LinkedList<_QuestOutput> outputs;
    LinkedList<Decision> decisions;
    QuestBubble questBubble;

    public Quest(QuestBubble questBubble) {
        this.questBubble = questBubble;
        inputs = new LinkedList<>();
        outputs = new LinkedList<>();
        decisions = new LinkedList<>();
    }

    public void addInput() {
        inputs.add(new _QuestInput(this, 0, 0, 10, 10, Color.RED));
        inputs.getLast().updatePosition();
    }

    public void addOutput() {
       outputs.add(new _QuestOutput(this,0,0,10, 10, Color.BLUE));
       outputs.getLast().updatePosition();
    }

    public void delete() {
        inputs.clear();
        outputs.clear();
        decisions.clear();
    }

}
