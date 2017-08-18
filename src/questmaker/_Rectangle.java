package questmaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.Serializable;

/**
 *
 * @author Tom
 */
public class _Rectangle implements Serializable{
    static final long serialVersionUID = 42L;
    int posX;
    int posY;
    int width;
    int height;
    Color color;

    public _Rectangle(int posX, int posY, int width, int height, Color c) {
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.color = c;
    }
    
    public boolean isMouseOver(Point mousePosition) {
        return mousePosition.x > posX && mousePosition.x < posX + width
                && mousePosition.y > posY && mousePosition.y < posY + height;
    }
    
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawRect(posX, posY, width, height);
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
    
}
