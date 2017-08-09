package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class ProgramStart extends InputOutputSquare {
    
    QuestInput target;

    public ProgramStart(QuestInput target, int size, Color color) {
        super(size, color);
        this.target = target;
    }
    
}
