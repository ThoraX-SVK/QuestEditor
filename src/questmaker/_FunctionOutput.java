package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _FunctionOutput extends _FunctionIO implements Serializable {

    _Rectangle target;
    double probability;

    public _FunctionOutput(FunctionBlock owner, int posX, int posY, int width, int height, Color c) {
        super(owner, posX, posY, width, height, c);
        this.probability = 0;
    }
 
    @Override
    public void updatePosition() {
        this.posX = owner.posX + owner.width + 5;
        this.posY = owner.posY + 13*owner.outputs.indexOf(this);
    }
    
    public _Rectangle getTarget() {
        return target;
    }

    public double getProbability() {
        return probability;
    }
}
