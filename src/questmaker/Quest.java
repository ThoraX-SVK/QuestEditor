package questmaker;

import java.awt.Color;
import java.util.LinkedList;

public class Quest {

    static int inputId = 0;
    static int outputId = 0;
    LinkedList<QuestInput> inputs;
    LinkedList<QuestOutput> outputs;
    LinkedList<Decision> decisions;
    LinkedList<LineAnswerDecision> lines;
    QuestBubble questBubble;

    public Quest(QuestBubble questBubble) {
        this.questBubble = questBubble;
        inputs = new LinkedList<>();
        outputs = new LinkedList<>();
        decisions = new LinkedList<>();
        lines = new LinkedList<>();
    }

    public void addInput() {
        inputs.add(new QuestInput(this, inputId, questBubble.posX - 15,
                questBubble.posY + 13 * inputs.size(), 10, Color.RED));
        inputId++;
    }

    public void addOutput() {
        outputs.add(new QuestOutput(this, outputId, questBubble.posX + questBubble.bubbleSize + 5,
                questBubble.posY + 13 * outputs.size(), 10, Color.BLUE));
        outputId++;
    }

    public void delete() {

        inputs.clear();
        outputs.clear();
        decisions.clear();
        lines.clear();
    }

}
