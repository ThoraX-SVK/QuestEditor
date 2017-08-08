package questmaker;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Tom
 */
public class LineOutputInput {

    QuestInput qi;
    QuestOutput qo;

    public LineOutputInput(QuestOutput qo, QuestInput qi) {
        this.qi = qi;
        this.qo = qo;
    }

    public void delete() {
        if (qo != null) {
            qo.goingToInput = null;
        }

        qi = null;
        qo = null;
    }

    public boolean deleteIfContainsAtLeastOne(QuestInput qi, QuestOutput qo) {
        if (this.qi == qi || this.qo == qo) {
            this.delete();
            return true;
        }
        return false;
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(this.qo.posX + 5, this.qo.posY + 5, this.qi.posX + 5, this.qi.posY + 5);
    }

}
