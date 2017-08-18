package questmaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;

public class QuestMainFrameCanvas extends DoubleBuffer implements MouseListener, MouseMotionListener {

    ProgramStart programStart;
    QuestMainFrame owner;

    LinkedList<_Rectangle> blockToDraw;

    _Rectangle lastMouseOver;
    Point tempLineEnd;
    
    _Rectangle _chosenOutputRectangle;
    _Rectangle _chosenInputRectangle;
    _Rectangle _chosenBlock;
    
    _QuestOutput _chosenQuestOutput;
    _FunctionOutput _chosenFunctionOutput;
    
    _QuestInput _chosenQuestInput;
    _FunctionInput _chosenFunctionInput;
    
    QuestBubble _chosenQuestBubble;
    FunctionBlock _chosenFunctionBlock;
    
    boolean mouseButtonHoldOnQuestBuble;
    boolean mouseButtonHoldOnQuestOutput;
    boolean mouseButtonHoldOnProgramStart;
    boolean mouseButtonHoldOnFunction;
    boolean mouseHoldOnFunctionOutput;
    boolean showDeleteZone;
    boolean deleteOutputLines;
    boolean deleteInputLines;

    public QuestMainFrameCanvas(QuestMainFrame qmf) {
        this.setBackground(Color.BLACK);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.blockToDraw = new LinkedList<>();
        this.mouseButtonHoldOnQuestBuble = false;
        this.mouseButtonHoldOnQuestOutput = false;
        this.showDeleteZone = false;
        this.deleteInputLines = false;
        this.deleteOutputLines = false;
        this.mouseButtonHoldOnProgramStart = false;
        this.mouseButtonHoldOnFunction = false;
        this.mouseHoldOnFunctionOutput = false;
        this.programStart = new ProgramStart(0, 0, 30, 30, Color.GREEN);
    }

    @Override
    public void paintBuffer(Graphics g) {

        g.setFont(new Font("OCR A Extended", 1, 20));

        programStart.draw(g);
        if (programStart.target != null) {
            g.setColor(Color.CYAN);
            g.drawLine(programStart.posX + programStart.width / 2, programStart.posY + programStart.height / 2,
                    programStart.target.posX + 5, programStart.target.posY + 5);
        }

        for (_Rectangle r : blockToDraw) {
            if (r instanceof FunctionBlock) {
                FunctionBlock fb = (FunctionBlock) r;
                fb.draw(g);
            } else if (r instanceof QuestBubble) {
                QuestBubble qb = (QuestBubble) r;
                qb.draw(g);
            }
        }

        if (showDeleteZone) {
            drawDeleteZone(g);
        }

        if (tempLineEnd != null) {
            if (mouseButtonHoldOnQuestOutput) {
                g.setColor(Color.ORANGE);
                g.drawLine(_chosenQuestOutput.posX + 5, _chosenQuestOutput.posY + 5,
                        tempLineEnd.x, tempLineEnd.y);
            } else if (mouseHoldOnFunctionOutput) {
                g.setColor(Color.ORANGE);
                g.drawLine(_chosenFunctionOutput.posX + 5, _chosenFunctionOutput.posY + 5,
                        tempLineEnd.x, tempLineEnd.y);
            } else if (mouseButtonHoldOnProgramStart) {
                g.setColor(Color.CYAN);
                g.drawLine(programStart.posX + programStart.width / 2, programStart.posY + programStart.height / 2,
                        tempLineEnd.x, tempLineEnd.y);
            }
        }

        for (_Rectangle r : blockToDraw) {
            if (r instanceof QuestBubble) {
                QuestBubble tmp = (QuestBubble) r;
                for (_QuestOutput qo : tmp.quest.outputs) {
                    if (qo.target != null) {
                        drawLine(qo.getTarget(), qo, g);
                    }
                }
            } else if (r instanceof FunctionBlock) {
                FunctionBlock fb = (FunctionBlock) r;
                for (_FunctionOutput fo : fb.outputs) {
                    if (fo.target != null)
                        drawLine(fo, fo.target, g);
                }
            }

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            
            _chosenBlock = selectBlockOnMouseOver(e.getPoint());
            
            if (_chosenBlock instanceof QuestBubble) {
                _chosenQuestBubble = (QuestBubble) _chosenBlock;
                _chosenQuestBubble.color = Color.RED;
                mouseButtonHoldOnQuestBuble = true;
                showDeleteZone = true;
                this.repaint();
                return;
            } else if (_chosenBlock instanceof FunctionBlock) {
                _chosenFunctionBlock = (FunctionBlock) _chosenBlock;
                _chosenFunctionBlock.color = Color.RED;
                mouseButtonHoldOnFunction = true;
                showDeleteZone = true;
                this.repaint(); 
                return;
            }
            
            _chosenOutputRectangle = selectOutput(e.getPoint());
            
            if (_chosenOutputRectangle instanceof _QuestOutput) {
                _chosenQuestOutput = (_QuestOutput) _chosenOutputRectangle;
                if (_chosenQuestOutput.target == null) { /* ak true, tak z neho nejde čiara */
                    _chosenQuestOutput.color = Color.WHITE;
                    mouseButtonHoldOnQuestOutput = true;
                    this.repaint();
                    return;
                }
            } else if (_chosenOutputRectangle instanceof _FunctionOutput) {
                _chosenFunctionOutput = (_FunctionOutput) _chosenOutputRectangle;
                if (_chosenFunctionOutput.target == null) {  /* ak true, tak z neho nejde čiara */
                    _chosenFunctionOutput.color = Color.WHITE;
                    mouseHoldOnFunctionOutput = true;
                    this.repaint();
                    return;
                }
            }
            
            if (programStart.isMouseOver(e.getPoint()))
                mouseButtonHoldOnProgramStart = true;
  
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            
            _chosenBlock = selectBlockOnMouseOver(e.getPoint());
            
            if (_chosenBlock instanceof QuestBubble) {
                _chosenQuestBubble = (QuestBubble) _chosenBlock;
                EditQuestDialog eqd = new EditQuestDialog(owner, _chosenQuestBubble.quest);
                this.repaint();
                return;
            } else if (_chosenBlock instanceof FunctionBlockRandom) {
                _chosenFunctionBlock = (FunctionBlockRandom) _chosenBlock;
                EditFunctionDialog efd = new EditFunctionDialog(owner, _chosenFunctionBlock);
                this.repaint();
                return;
            }
            
            _chosenOutputRectangle = selectOutput(e.getPoint());
            
            if (_chosenOutputRectangle instanceof _QuestOutput) {
                _chosenQuestOutput = (_QuestOutput) _chosenOutputRectangle;
                
                if (_chosenQuestOutput.target != null) {
                    _chosenQuestOutput.target = null;
                    this.repaint();
                    return;
                }
                
            } else if (_chosenOutputRectangle instanceof _FunctionOutput) {
                _chosenFunctionOutput = (_FunctionOutput) _chosenOutputRectangle;
                
                if (_chosenFunctionOutput.target != null) {
                    _chosenFunctionOutput.target = null;
                    this.repaint();
                    return;
                }
            }
            
            _chosenInputRectangle = selectInput(e.getPoint());
            
            if (_chosenInputRectangle instanceof _QuestInput) {
                _chosenQuestInput = (_QuestInput) _chosenInputRectangle;
                
                for (_Rectangle r : blockToDraw) {
                    if (r instanceof QuestBubble) {
                        QuestBubble tmp = (QuestBubble) r;
                        for (_QuestOutput qo : tmp.quest.outputs) {
                            if (qo.target == _chosenQuestInput) {
                                qo.target = null;
                            }
                        }
                    } else if (r instanceof FunctionBlockRandom) {
                        FunctionBlockRandom fbr = (FunctionBlockRandom) r;
                        for (_FunctionOutput fo : fbr.outputs) {
                            if (fo.target == _chosenQuestInput) {
                                fo.target = null;
                            }
                        }
                    }
                }
                this.repaint();
                return; 
            } else if (_chosenInputRectangle instanceof _FunctionInput) {
                _chosenFunctionInput = (_FunctionInput) _chosenInputRectangle;
                
                //TODO
                
                
            }     
        }
        
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseButtonHoldOnQuestBuble) {
            _chosenQuestBubble.posX = e.getX() - _chosenQuestBubble.width / 2;
            _chosenQuestBubble.posY = e.getY() - _chosenQuestBubble.height / 2;
            
            _chosenQuestBubble.update();
            this.repaint();
            
        } else if (mouseButtonHoldOnFunction) {
            _chosenFunctionBlock.posX = e.getX() - _chosenFunctionBlock.width / 2;
            _chosenFunctionBlock.posY = e.getY() - _chosenFunctionBlock.height / 2;

            _chosenFunctionBlock.update();
            this.repaint();

        } else if (mouseButtonHoldOnQuestOutput || mouseButtonHoldOnProgramStart
                || mouseHoldOnFunctionOutput) {
            tempLineEnd = new Point(e.getPoint());
            this.repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (mouseButtonHoldOnQuestBuble) {
            //ak sa nachadza v cervenom delete obdlzniku
            if (e.getX() > this.getWidth() - 160 && e.getX() < this.getWidth() - 40
                    && e.getY() > this.getHeight() - 40 && e.getY() < this.getHeight() - 10) {

                for (_QuestInput qi : _chosenQuestBubble.quest.inputs) {
                    for (_Rectangle r : blockToDraw) {
                        if (r instanceof QuestBubble) {
                            QuestBubble tmp = (QuestBubble) r;
                            for (_QuestOutput qo : tmp.quest.outputs) {
                                if (qo.target == qi) {
                                    qo.target = null;
                                }
                            }
                        } else if (r instanceof FunctionBlockRandom) {
                            FunctionBlockRandom fbr = (FunctionBlockRandom) r;
                            for (_FunctionOutput fo : fbr.outputs) {
                                if (fo.target == qi) {
                                    fo.target = null;
                                }
                            }
                        }
                    }
                }

                for (_QuestOutput qo : _chosenQuestBubble.quest.outputs) {
                    qo.target = null;
                }

                _chosenQuestBubble.delete();
                blockToDraw.remove(_chosenQuestBubble);
            } else { /* Nie je to v delete bloku */
                _chosenQuestBubble.color = Color.BLUE;
            }
            this.repaint();
            
        } else if (mouseButtonHoldOnFunction) {
            _chosenFunctionBlock.color = Color.GRAY;

        } else if (mouseButtonHoldOnQuestOutput) {
            _chosenQuestOutput.color = Color.BLUE;   
            _chosenQuestOutput.target = selectInput(e.getPoint());
            this.repaint();
            
        } else if (mouseHoldOnFunctionOutput) {
            _chosenFunctionOutput.color = Color.BLUE;
            _chosenFunctionOutput.target = selectInput(e.getPoint());  
            this.repaint();
            
        } else if (mouseButtonHoldOnProgramStart) {
            programStart.target = selectInput(e.getPoint());  
            
        }

        tempLineEnd = null;
        showDeleteZone = false;
        mouseButtonHoldOnProgramStart = false;
        mouseButtonHoldOnQuestOutput = false;
        mouseButtonHoldOnQuestBuble = false;
        mouseButtonHoldOnFunction = false;
        mouseHoldOnFunctionOutput = false;
        _chosenFunctionOutput = null;
        _chosenBlock = null;
        _chosenFunctionBlock = null;
        _chosenFunctionOutput = null;
        _chosenInputRectangle = null;
        _chosenOutputRectangle = null;
        _chosenQuestBubble = null;
        _chosenQuestInput = null;
        _chosenQuestOutput = null;
        this.repaint();
    }

    

    @Override
    public void mouseMoved(MouseEvent e) {

        if (!mouseButtonHoldOnQuestBuble && !blockToDraw.isEmpty()) {
            _Rectangle r = selectBlockOnMouseOver(e.getPoint());
            QuestBubble qb = null;
            FunctionBlockRandom fbl = null;

            if (r instanceof QuestBubble) {
                qb = (QuestBubble) r;
            } else if (r instanceof FunctionBlockRandom) {
                fbl = (FunctionBlockRandom) r;
            }

            if (r == null && lastMouseOver != null) {
                if (lastMouseOver instanceof QuestBubble) {
                    lastMouseOver.color = Color.GREEN;
                } else if (lastMouseOver instanceof FunctionBlockRandom) {
                    lastMouseOver.color = Color.GRAY;
                }

                this.repaint();
            }
            if (qb != null) {
                lastMouseOver = qb;
                qb.color = Color.BLUE;
                //chosenQuestBubble = null;
                this.repaint();
            }
            if (fbl != null) {
                lastMouseOver = fbl;
                fbl.color = Color.RED;
                this.repaint();
            }
        }

    }

    private _Rectangle selectOutput(Point mouseCursor) {

        if (programStart.isMouseOver(mouseCursor)) {
            return programStart;
        }

        if (blockToDraw.isEmpty()) {
            return null;
        }

        for (_Rectangle r : blockToDraw) {
            if (r instanceof QuestBubble) {
                QuestBubble qb = (QuestBubble) r;
                for (_QuestOutput qo : qb.quest.outputs) {
                    if (qo.isMouseOver(mouseCursor)) {
                        return qo;
                    }
                }
            } else if (r instanceof FunctionBlock) {
                FunctionBlock fbr = (FunctionBlock) r;
                for (_FunctionOutput fo : fbr.outputs) {
                    if (fo.isMouseOver(mouseCursor)) {
                        return fo;
                    }
                }
            }
        }

        return null;
    }

    private _Rectangle selectInput(Point mouseCursor) {

        if (blockToDraw.isEmpty()) {
            return null;
        }

        for (_Rectangle r : blockToDraw) {
            if (r instanceof QuestBubble) {
                QuestBubble qb = (QuestBubble) r;
                for (_QuestInput qi : qb.quest.inputs) {
                    if (qi.isMouseOver(mouseCursor)) {
                        return qi;
                    }
                }
            } else if (r instanceof FunctionBlock) {
                FunctionBlock fbr = (FunctionBlock) r;
                for (_FunctionInput fi : fbr.inputs) {
                    if (fi.isMouseOver(mouseCursor)) {
                        return fi;
                    }
                }
            }
        }
        
        return null;
    }

    private _Rectangle selectBlockOnMouseOver(Point mouseCursor) {
        if (blockToDraw.isEmpty()) {
            return null;
        }

        for (_Rectangle r : blockToDraw) {
            if (r.isMouseOver(mouseCursor)) {
                return r;
            }
        }
        return null;
    }

    private void drawLine(_Rectangle rec, _Rectangle target, Graphics g) {
        g.setColor(Color.WHITE);
        g.drawLine(rec.posX + 5, rec.posY + 5, target.posX + 5, target.posY + 5);
    }

    private void drawDeleteZone(Graphics g) {
        g.setColor(Color.RED);
        g.drawRect(this.getWidth() - 160, this.getHeight() - 40, 120, 30);
        g.drawString("Delete", this.getWidth() - 140, this.getHeight() - 18);
    }

    @Override
        public void mouseClicked(MouseEvent e) {
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
