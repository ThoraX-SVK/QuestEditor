package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class DecisionAddNewAnswerSign implements Serializable {

    static final long serialVersionUID = 42L;
    int posX;
    int posY;
    Decision belongsTo;

    public DecisionAddNewAnswerSign(Decision belongsTo) {
        this.belongsTo = belongsTo;
        updatePositon();
    }

    public void updatePositon() {
        this.posX = belongsTo.posX + belongsTo.size / 2;
        this.posY = belongsTo.posY + belongsTo.answers.size() * 20 + 25;
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(posX + 3, posY, 4, 10);
        g.fillRect(posX, posY + 3, 10, 4);
    }

    public boolean MouseOverlaps(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX + 10
                && mousePosition.y > posY && mousePosition.y < posY + 10;
    }

    public void delete() {
        belongsTo = null;
    }

}
