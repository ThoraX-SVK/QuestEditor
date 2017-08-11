package game;

import java.awt.Frame;
import questmaker.SaveFile;


public class GameFrame extends Frame {

    public GameDraw gd;
    
    
    public GameFrame(SaveFile save) {
        this.setSize(600,600);
        
        gd = new GameDraw(save);
        this.add(gd);
        
        
        this.setVisible(true);
    }
    
}
