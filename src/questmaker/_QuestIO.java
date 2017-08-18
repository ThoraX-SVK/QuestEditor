package questmaker;

import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public abstract class _QuestIO extends _OuterLayerIO implements Serializable {

    Quest owner;

    public _QuestIO(Quest owner, int posX, int posY, int width, int height, Color c) {
        super(posX, posY, width, height, c);
        this.owner = owner;
    }

    public Quest getOwner() {
        return owner;
    }
    
    @Override
    abstract public void updatePosition();
    abstract public void buildInnerSquare(int positionInLinkedList, int windowWidth);
    
}
