package questmaker;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class SaveFile implements Serializable {
    
    ProgramStart programStart;
    LinkedList<QuestBubble> questbubbles;

    public SaveFile(ProgramStart programStart, LinkedList<QuestBubble> questbubbles) {
        this.programStart = programStart;
        this.questbubbles = questbubbles;
    }
}
