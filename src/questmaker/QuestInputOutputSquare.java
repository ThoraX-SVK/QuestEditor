package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
abstract public class QuestInputOutputSquare extends InputOutputSquare implements Serializable {
    
    Quest owner;
    int innerPosX;
    int innerPosY;
    int innerSize;

    public QuestInputOutputSquare(Quest owner, int size, Color color) {
        super(size, color);
        this.owner = owner;
        this.innerPosX = 0;
        this.innerPosY = 0;
        this.innerSize = 0;
    }
    
    abstract public void upadatePosition();
    abstract public void buildInnerSquare(int positionInLinkedList, int windowWidth);
    
    public void drawInner (Graphics g) {
        g.drawRect(innerPosX, innerPosY, innerSize, innerSize);
    }
    
    public boolean overlapsInner(Point mousePosition) {
        return mousePosition.x > innerPosX && mousePosition.x < innerPosX + innerSize
                && mousePosition.y > innerPosY && mousePosition.y < innerPosY + innerSize;
    }

}
