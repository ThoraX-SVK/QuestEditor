package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _FunctionInput extends _FunctionIO implements Serializable {
    
    public _FunctionInput(FunctionBlock owner, int posX, int posY, int width, int height, Color c) {
        super(owner, posX, posY, width, height, c);
    }

    public FunctionBlock getOwner() {
        return owner;
    }

    @Override
    public void updatePosition() {
        this.posX = owner.posX - 15;
        this.posY = owner.posY;
    }
    
    
}
