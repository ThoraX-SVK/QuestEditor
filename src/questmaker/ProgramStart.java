package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class ProgramStart extends _Rectangle implements Serializable {
    
    static final long serialVersionUID = 42L;
    _Rectangle target;

    public ProgramStart(int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
    }


    public _Rectangle getTarget() {
        return target;
    }
    
    
    
}
