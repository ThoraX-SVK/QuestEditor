package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class ProgramStart extends MyRectangle implements Serializable {
    
    static final long serialVersionUID = 42L;
    QuestInput target;

    public ProgramStart(QuestInput target, int size, Color color) {
        super(size, color);
        this.target = target;
    }

    public QuestInput getTarget() {
        return target;
    }
    
    
    
}
