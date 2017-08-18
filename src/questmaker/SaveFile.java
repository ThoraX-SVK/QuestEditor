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
    LinkedList<_Rectangle> blocksToDraw;

    public SaveFile(ProgramStart programStart, LinkedList<_Rectangle> blocksToDraw) {
        this.programStart = programStart;
        this.blocksToDraw = blocksToDraw;
    }

    public ProgramStart getProgramStart() {
        return programStart;
    }

    public LinkedList<_Rectangle> getBlocksToDraw() {
        return blocksToDraw;
    }
    
}
