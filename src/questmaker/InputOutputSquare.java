package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Tom
 */
public class InputOutputSquare {
    
    int posX;
    int posY;
    int size;
    Color color;

    public InputOutputSquare(int size, Color color) {
        this.size = size;
        this.color = color;
    }

    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX + size
                && mousePosition.y > posY && mousePosition.y < posY + size;
    }
    
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.drawRect(this.posX, this.posY, this.size, this.size);
    }
    
}
