package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public class FunctionBlockRandom extends _Rectangle implements Serializable {
    
    static final long serialVersionUID = 42L;
    String functionName;
    
    _FunctionInput input;
    LinkedList<_FunctionOutput> outputs;

    public FunctionBlockRandom(int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.functionName = "RANDOM";
        this.outputs = new LinkedList<>();
        this.input = new _FunctionInput(this, 0, 0, 10, 10, Color.RED);
        this.input.updatePosition();
    }
    
    public void addOuput() {
        _FunctionOutput output = new _FunctionOutput(this,0,0,10,10,Color.BLUE);
        output.updatePosition();
        outputs.add(output);   
    }
    
    private void addInput() {
        _FunctionInput in = new _FunctionInput(this,0,0,10,10,Color.RED);
        in.updatePosition();
        input = in;
    }
    
    
    
    
    
    
    public void update() {
        input.updatePosition();
        
        for (_FunctionOutput ou : outputs) {
            ou.updatePosition();
        } 
    }
    
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(functionName, posX + 3, posY + 18);
        
        input.draw(g);
        for (_FunctionOutput ou : outputs) {
            ou.draw(g);
        }
    }
    
    public _Rectangle getTarget() {
        
        double totalSum = 0;
        
        for (_FunctionOutput fo :outputs) {
            totalSum += fo.probability;
        }
        
        double chance = Math.random()*totalSum;
        double summing = 0;
        for (_FunctionOutput fo : outputs) {
            if (chance >= summing && chance < summing + fo.probability)
                return fo;
            else
                summing += fo.probability;
        }
    
        return null;
    }
    
}
