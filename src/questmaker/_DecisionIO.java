package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public abstract class _DecisionIO extends _Rectangle implements Serializable {

    public _DecisionIO(int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
    }

    abstract public void updatePosition();
    
}
