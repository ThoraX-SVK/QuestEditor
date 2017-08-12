package mainMenu;

import game.GameFrame;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import jdk.nashorn.internal.parser.TokenType;
import questmaker.DoubleBuffer;
import questmaker.MyRectangle;
import questmaker.QuestMainFrame;
import questmaker.SaveFile;

/**
 *
 * @author Tom
 */
public class MainMenuCanvas extends DoubleBuffer implements MouseListener {

    MainMenuFrame owner;
    MyRectangle loadGameFile;
    MyRectangle launchEditor;
    MyRectangle exitProgram;

    String loadString = "Load game file";
    String launchEditorString = "Launch Editor";
    String exitString = "Exit";

    public MainMenuCanvas(MainMenuFrame owner) {
        this.setBackground(Color.BLACK);
        this.owner = owner;
        this.addMouseListener(this);

        this.loadGameFile = new MyRectangle(loadString.length() * 20, Color.GREEN) {
            @Override
            public void draw(Graphics g) {
                g.setColor(this.getColor());
                g.drawRect(this.getPosX(), this.getPosY(), this.getSize(), 60);
            }

            @Override
            public boolean MouseOverlaps(Point mousePosition) {
                return mousePosition.x > this.getPosX() && mousePosition.x < this.getPosX() + this.getSize()
                        && mousePosition.y > this.getPosY() && mousePosition.y < this.getPosY() + 60;
            }

        };

        this.launchEditor = new MyRectangle(loadString.length() * 20, Color.GREEN) {
            @Override
            public void draw(Graphics g) {
                g.setColor(this.getColor());
                g.drawRect(this.getPosX(), this.getPosY(), this.getSize(), 60);
            }

            @Override
            public boolean MouseOverlaps(Point mousePosition) {
                return mousePosition.x > this.getPosX() && mousePosition.x < this.getPosX() + this.getSize()
                        && mousePosition.y > this.getPosY() && mousePosition.y < this.getPosY() + 60;
            }

        };

        this.exitProgram = new MyRectangle(loadString.length() * 20, Color.GREEN) {
            @Override
            public void draw(Graphics g) {
                g.setColor(this.getColor());
                g.drawRect(this.getPosX(), this.getPosY(), this.getSize(), 60);
            }

            @Override
            public boolean MouseOverlaps(Point mousePosition) {
                return mousePosition.x > this.getPosX() && mousePosition.x < this.getPosX() + this.getSize()
                        && mousePosition.y > this.getPosY() && mousePosition.y < this.getPosY() + 60;
            }

        };

    }

    @Override
    public void paintBuffer(Graphics g) {
        //g.setFont(new Font("OCR A Extended", 1, 28));
        g.setFont(new Font("Tw Cen MT Condensed", 1, 50));

        //Čísla sú presne aby bolo všetko vycentrované
        loadGameFile.setPosX(this.getWidth() - this.getWidth() / 2 - 140);
        loadGameFile.setPosY(300);
        loadGameFile.draw(g);

        launchEditor.setPosX(loadGameFile.getPosX());
        launchEditor.setPosY(loadGameFile.getPosY() + 80);
        launchEditor.draw(g);

        exitProgram.setPosX(launchEditor.getPosX());
        exitProgram.setPosY(launchEditor.getPosY() + 80);
        exitProgram.draw(g);

        g.setColor(Color.WHITE);
        g.drawString(loadString, loadGameFile.getPosX() + 15, loadGameFile.getPosY() + 45);
        g.drawString(launchEditorString, launchEditor.getPosX() + 24, launchEditor.getPosY() + 45);
        g.drawString(exitString, exitProgram.getPosX() + 105, exitProgram.getPosY() + 45);

        g.setFont(new Font("Tw Cen MT Condensed", 1, 150));
        g.drawString("Quester!", this.getWidth() - 225 - this.getWidth() / 2, 200);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (loadGameFile.MouseOverlaps(e.getPoint())) {
            FileDialog FD = new FileDialog(owner, "Load",FileDialog.LOAD);
            FD.setVisible(true);

            if (FD.getFile() != null) {
                
                try {
                FileInputStream fis=new FileInputStream(FD.getDirectory() + FD.getFile());
                //System.out.println(FD.getDirectory() + FD.getFile());
                ObjectInputStream is = new ObjectInputStream(fis);
                    try {
                        
                        SaveFile file= (SaveFile)is.readObject();
                        GameFrame gf = new GameFrame(file);
                        gf.gd.run();
                        owner.dispose();
                        
                    } catch (ClassNotFoundException ex) {
                        System.out.println(ex);
                    }
                
                }
                catch (IOException ex) {
                    System.out.println(ex);
                } 
            } 
        } else if (launchEditor.MouseOverlaps(e.getPoint())) {
            QuestMainFrame QMF = new QuestMainFrame();
            owner.dispose();
        } else if (exitProgram.MouseOverlaps(e.getPoint())) {
            System.exit(0);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
