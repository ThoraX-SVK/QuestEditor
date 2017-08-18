package questmaker;

import java.awt.Color;

/**
 *
 * @author Tom
 */
public class FunctionBlockJoiner extends FunctionBlock {

    
    
    public FunctionBlockJoiner(int posX, int posY, int width, int height, Color c) {
        super("JOIN", posX, posY, width, height, c);
        this.addInput();
        this.addOutput();
    }

    @Override
    public _Rectangle getTarget() {
        return outputs.get(0);
    }
    
}
