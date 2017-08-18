package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public abstract class _FunctionIO extends _OuterLayerIO  implements Serializable {

    
    FunctionBlockRandom owner;

    public _FunctionIO(FunctionBlockRandom owner, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.owner = owner;
    }

    

    @Override
    abstract public void updatePosition();
    
}
