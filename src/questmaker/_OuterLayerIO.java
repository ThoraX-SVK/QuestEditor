package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public abstract class _OuterLayerIO extends _Rectangle  implements Serializable{

    int innerPosX;
    int innerPosY;
    int innerWidth;
    int innerHeigth;

    public _OuterLayerIO(int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
    }
 
    abstract public void updatePosition();
    
    public void drawInner(Graphics g) {
        g.setColor(this.color);
        g.drawRect(innerPosX, innerPosY, innerWidth, innerHeigth);
    }
    
    public boolean isMouseOverInner(Point mousePosition) {
        return mousePosition.x > innerPosX && mousePosition.x < innerPosX + innerWidth
                && mousePosition.y > innerPosY && mousePosition.y < innerPosY + innerHeigth;
    }
    
    
}
