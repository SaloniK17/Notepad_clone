package com.thinking.machines.notepad;
import javax.swing.*;
import java.awt.*;
public class AboutNotepad extends JDialog
{
public AboutNotepad(JFrame frame)
{
super(frame,"About Notepad",true);
setIconImages(null);
setLayout(null);
ImageIcon i1=new ImageIcon(this.getClass().getResource("/AboutNotepad/windows_icon.png"));
Image i2=i1.getImage().getScaledInstance(320,60,Image.SCALE_DEFAULT);
i1=new ImageIcon(i2);
JLabel l1=new JLabel(i1);
int lm=10;
int tm=10;
l1.setBounds(lm+65,tm,320,60);
JSeparator s=new JSeparator();
s.setBounds(lm+5,tm+60+10,430,20);
ImageIcon i3=new ImageIcon(this.getClass().getResource("/AboutNotepad/logo_icon.jpg"));
JLabel l2=new JLabel(i3);
l2.setBounds(lm,tm+60+10+10+10+5,40,40);
JLabel l3=new JLabel("<html><p>Notepad is a word processing program. <br> Which allows changing of text in coumputer files.<br><br>Notepad is a simple text editor for basic text editing program.<br>Which enables computer user to create documents. </p></html>");
l3.setBounds(lm+40+10,tm+60+10+10,400,200);
l3.setFont(new Font("SAN_SERIF",Font.PLAIN,18));
JButton b=new JButton("OK");
b.setBounds(lm+320,tm+330,70,30);
b.addActionListener((ae)->{AboutNotepad.this.dispose();
AboutNotepad.this.setFocusableWindowState(false);
});
add(l1);
add(s);
add(l2);
add(l3);
add(b);
setDefaultCloseOperation(DISPOSE_ON_CLOSE);
setSize(475,430);
setLocation(100,100);
setResizable(false);
setVisible(true);
}
}