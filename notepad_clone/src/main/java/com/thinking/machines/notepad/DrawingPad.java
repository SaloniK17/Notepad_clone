package com.thinking.machines.notepad;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.event.*;
public class DrawingPad extends JDialog
{
private JMenuItem foregroundColorJMenuItem,backgroundColorJMenuItem;
private JMenu colorJMenuItem;
private CanvasPad canvasPad;
private JMenuBar menuBar;
private JButton clear;
public DrawingPad(JFrame frame)
{
super(frame,"Drawing Pad",true);
foregroundColorJMenuItem=new JMenuItem("ForeGround-Color");
backgroundColorJMenuItem=new JMenuItem("BackGround-Color");
colorJMenuItem=new JMenu("Color Changer");
menuBar=new JMenuBar();
canvasPad=new CanvasPad();
clear=new JButton("Clear");

colorJMenuItem.add(foregroundColorJMenuItem);
colorJMenuItem.add(backgroundColorJMenuItem);

menuBar.add(colorJMenuItem);
menuBar.add( Box.createHorizontalStrut(1100) ); 
menuBar.add(clear);

foregroundColorJMenuItem.addActionListener((ae)->{
JColorChooser jcc=new JColorChooser();
Color fgColor=canvasPad.getForegroundColor();
Color color=jcc.showDialog(canvasPad,"ForeGround-Color",fgColor);
if(color!=null)
{
canvasPad.setForegroundColor(color);
}
});

backgroundColorJMenuItem.addActionListener((ae)->{
JColorChooser jcc=new JColorChooser();
Color bgColor=canvasPad.getBackgroundColor();
Color color=jcc.showDialog(canvasPad,"BackGround-Color",bgColor);
if(color!=null)
{
canvasPad.setBackgroundColor(color);
}
});
clear.addActionListener((ae)->{canvasPad.clear();});

setLayout(new BorderLayout());
add(canvasPad,BorderLayout.CENTER);
setJMenuBar(menuBar);
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
int x=1300;
int y=600;
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
setSize(x,y);
setLocation(((d.width-x)/2),((d.height-y)/2));
setResizable(false);
setVisible(true);
}

//inner class start here
class CanvasPad extends Canvas
{
private int lastx;
private int lasty;
CanvasPad()
{
setForeground(Color.black);
setBackground(Color.white);
addMouseListener(new MouseAdapter(){
public void mousePressed(MouseEvent me)
{
lastx=me.getX();
lasty=me.getY();
}
});
addMouseMotionListener(new MouseMotionAdapter()
{
public void mouseDragged(MouseEvent me)
{
int currentx,currenty;
currentx=me.getX();
currenty=me.getY();
Graphics g=getGraphics();
g.drawLine(lastx,lasty,currentx,currenty);
lastx=currentx;
lasty=currenty;
}
});
}
public void setForegroundColor(Color color)
{
setForeground(color);
}
public void setBackgroundColor(Color color)
{
setBackground(color);
}
public Color getForegroundColor()
{
return getForeground();
}
public Color getBackgroundColor()
{
return getBackground();
}
public void clear()
{
Graphics g=getGraphics();
g.clearRect(0,0,getSize().width,getSize().height);
}
}//inner class ends here
}