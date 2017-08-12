package game;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import questmaker.SaveFile;


public class GameFrame extends Frame {

    public GameDraw gd;
    
    
    public GameFrame(SaveFile save) {
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        }
        );
        
        gd = new GameDraw(save,this);
        this.add(gd);
        
        
        this.setVisible(true);
    }
    
}
