package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public abstract class _FunctionIO extends _OuterLayerIO  implements Serializable {

    
    FunctionBlock owner;

    public _FunctionIO(FunctionBlock owner, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.owner = owner;
    }

    

    @Override
    abstract public void updatePosition();
    
}
