package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Tom
 */
public abstract class FunctionBlock extends _Rectangle implements Serializable {
    
    static final long serialVersionUID = 42L;
    String name;
    LinkedList<_FunctionInput> inputs;
    LinkedList<_FunctionOutput> outputs;

    public FunctionBlock(String name, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.outputs = new LinkedList<>();
        this.inputs = new LinkedList<>();
        this.name = name;
        this.width = this.name.length() * 14;
    }
    
    public void addOutput() {
        _FunctionOutput output = new _FunctionOutput(this,0,0,10,10,Color.BLUE);
        output.updatePosition();
        outputs.add(output); 
    }
    
    public void addInput() {
        _FunctionInput in = new _FunctionInput(this,0,0,10,10,Color.RED);
        in.updatePosition();
        inputs.add(in);
    }
    
    abstract public _Rectangle getTarget();
    
    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.drawString(name, posX + 3, posY + 18);
        
        
        for (_FunctionInput fi : inputs) {
            fi.draw(g);
        }
        
        for (_FunctionOutput ou : outputs) {
            ou.draw(g);
        }
    }
    
    public void update() {
        
        for (_FunctionInput fi : inputs) {
            fi.updatePosition();
        }
        
        for (_FunctionOutput ou : outputs) {
            ou.updatePosition();
        } 
    }  

    
    
}
