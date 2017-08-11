package questmaker;

import game.GameFrame;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;


public class QuestMaker {
    
    final static boolean setUpEditor = false;
    
    public static void main(String[] args) throws IOException {

        if (setUpEditor) {
            QuestMainFrame QMF = new QuestMainFrame();
        }
        else {
            
            try {
                FileInputStream fis=new FileInputStream("C:\\Users\\Tom\\Desktop\\test");
                ObjectInputStream is = new ObjectInputStream(fis);
                    try {
                        SaveFile file = (SaveFile)is.readObject();
                        GameFrame gf = new GameFrame(file);
                        gf.gd.run();
  
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex);
                    }
                
                }
            catch (IOException e) {
                e.printStackTrace();
            }

                
            
            
        }
        
    }

}
