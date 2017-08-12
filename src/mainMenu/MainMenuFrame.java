package mainMenu;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Tom
 */
public class MainMenuFrame extends Frame {
    
    MainMenuCanvas mmc;
    
    public MainMenuFrame() {
        this.setSize(900,600);
        this.setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {

                System.exit(0);
            }
        }
        );
        
        mmc = new MainMenuCanvas(this);
        this.add(mmc);
        
        
        this.setVisible(true);
    }
    
}
