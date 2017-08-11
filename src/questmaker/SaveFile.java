package questmaker;

import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class SaveFile implements Serializable {
    
    static final long serialVersionUID = 42L;
    ProgramStart programStart;
    LinkedList<QuestBubble> questbubbles;

    public SaveFile(ProgramStart programStart, LinkedList<QuestBubble> questbubbles) {
        this.programStart = programStart;
        this.questbubbles = questbubbles;
    }

    public ProgramStart getProgramStart() {
        return programStart;
    }

    public LinkedList<QuestBubble> getQuestbubbles() {
        return questbubbles;
    }
    
    
}
