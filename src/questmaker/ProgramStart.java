package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class ProgramStart extends InputOutputSquare implements Serializable {
    
    QuestInput target;

    public ProgramStart(QuestInput target, int size, Color color) {
        super(size, color);
        this.target = target;
    }
    
}
