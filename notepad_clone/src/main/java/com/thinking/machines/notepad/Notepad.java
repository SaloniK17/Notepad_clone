package com.thinking.machines.notepad;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import javax.swing.event.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.UndoManager;
import com.thinking.machines.tmcomponents.swing.*;
class Notepad extends JFrame
{
private static String fileName="Untitled";
private JMenu fileJMenu,editJMenu,formatJMenu,viewJMenu,helpJMenu;
private JMenuItem newJMenuItem,newWindowJMenuItem,openJMenuItem,saveJMenuItem,saveAsJMenuItem,printJMenuItem,exitJMenuItem;
private JMenuItem undoJMenuItem,cutJMenuItem,copyJMenuItem,pasteJMenuItem,deleteJMenuItem,findJMenuItem;
private JMenuItem fontJMenuItem;
private JMenuItem aboutNotepadJMenuItem;
private JMenuItem zoomInJMenuItem,zoomOutJMenuItem;
private JCheckBoxMenuItem statusBarJMenuItem,wordWrapJMenuItem;
private JMenu zoomJMenu;
private JMenuBar menuBar;
private Container container;
private JTextArea textArea;
private JScrollPane jsp;
private ImageIcon logoIcon;
private String selectedText;
private UndoManager undoManager;
private int lineNum;
private int columnNum; 
private JLabel statusBar;
private String filePath;
Notepad()
{
super(fileName+" - Notepad");
initComponents();
setAppearance();   
addListener();
}
/*Notepad(String fileName)
{
super(fileName+" - Notepad");
initComponents();
setAppearance();   
addListener();
}*/

private void initComponents()
{
// Menu Item 
//file
newJMenuItem=new JMenuItem("New");
newJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
newWindowJMenuItem=new JMenuItem("New Window");
newWindowJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
openJMenuItem=new JMenuItem("Open...");
openJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
saveJMenuItem=new JMenuItem("Save");
saveJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
saveAsJMenuItem=new JMenuItem("Save As...");
saveAsJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK|ActionEvent.SHIFT_MASK));
printJMenuItem=new JMenuItem("Print");
printJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
exitJMenuItem=new JMenuItem("Exit");
exitJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0));
//edit
undoJMenuItem=new JMenuItem("Undo");
undoJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
cutJMenuItem=new JMenuItem("Cut");
cutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
copyJMenuItem=new JMenuItem("Copy");
copyJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
pasteJMenuItem=new JMenuItem("Paste");
pasteJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
deleteJMenuItem=new JMenuItem("Delete");
deleteJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
findJMenuItem=new JMenuItem("Find...");
findJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,ActionEvent.CTRL_MASK));
//format
wordWrapJMenuItem=new JCheckBoxMenuItem("Word Wrap");
fontJMenuItem=new JMenuItem("Font...");
//view
statusBarJMenuItem=new JCheckBoxMenuItem("Status Bar");
zoomInJMenuItem=new JMenuItem("Zoom In");
zoomInJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PLUS,ActionEvent.CTRL_MASK));
zoomOutJMenuItem=new JMenuItem("Zoom out");
zoomOutJMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS,ActionEvent.CTRL_MASK));
//help
aboutNotepadJMenuItem=new JMenuItem("About Notepad");
// menu
fileJMenu=new JMenu("<html><u>F</u>ile</html>");
editJMenu=new JMenu("<html><u>E</u>dit</html>");
formatJMenu=new JMenu("<html>F<u>o</u>rmat</html>");
viewJMenu=new JMenu("<html><u>V</u>iew</html>");
zoomJMenu=new JMenu("Zoom");
helpJMenu=new JMenu("<html><u>H</u>elp</html>");

//menu bar 
menuBar=new JMenuBar();
textArea=new JTextArea();
logoIcon=new ImageIcon(this.getClass().getResource("/icons/logo_icon.jpg"));
undoManager=new UndoManager();
lineNum=1;
columnNum=1;
statusBar=new JLabel("Ln "+lineNum+" Col "+columnNum);
}
private void  setAppearance()
{
setIconImage(logoIcon.getImage());
fileJMenu.add(newJMenuItem);
fileJMenu.add(newWindowJMenuItem);
fileJMenu.add(openJMenuItem);
fileJMenu.add(saveJMenuItem);
fileJMenu.add(saveAsJMenuItem);
fileJMenu.addSeparator();
fileJMenu.add(printJMenuItem);
fileJMenu.addSeparator();
fileJMenu.add(exitJMenuItem);

editJMenu.add(undoJMenuItem);
editJMenu.addSeparator();
editJMenu.add(cutJMenuItem);
editJMenu.add(copyJMenuItem);
editJMenu.add(pasteJMenuItem);
editJMenu.add(deleteJMenuItem);
editJMenu.addSeparator();
editJMenu.add(findJMenuItem);

formatJMenu.add(wordWrapJMenuItem);
formatJMenu.add(fontJMenuItem);

zoomJMenu.add(zoomInJMenuItem);
zoomJMenu.add(zoomOutJMenuItem);
viewJMenu.add(zoomJMenu);
viewJMenu.add(statusBarJMenuItem);

helpJMenu.add(aboutNotepadJMenuItem);

menuBar.add(fileJMenu);
menuBar.add(editJMenu);
menuBar.add(formatJMenu);
menuBar.add(viewJMenu);
menuBar.add(helpJMenu);

setJMenuBar(menuBar);

container=getContentPane();
container.setLayout(new BorderLayout());
textArea.setFont(new Font("Consolas",Font.PLAIN,24));
jsp=new JScrollPane(textArea,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
jsp.setBorder(BorderFactory.createEmptyBorder());
statusBar.setVisible(false);
container.add(jsp,BorderLayout.CENTER);
container.add(statusBar,BorderLayout.SOUTH);
Dimension d=Toolkit.getDefaultToolkit().getScreenSize();
this.setSize(d.width,d.height);
this.setDefaultCloseOperation(EXIT_ON_CLOSE);
}
private void addListener()
{
Notepad.this.textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
public void undoableEditHappened(UndoableEditEvent e)
{
undoManager.addEdit(e.getEdit());
}
});

Notepad.this.textArea.addCaretListener((ce)->{
try
{
int caretPosition=Notepad.this.textArea.getCaretPosition(); 
lineNum=Notepad.this.textArea.getLineOfOffset(caretPosition);
columnNum=caretPosition-Notepad.this.textArea.getLineStartOffset(lineNum);
lineNum++;
statusBar.setText("Ln "+lineNum+" Col "+columnNum);
}catch(Exception exception)
{
//do nothing
}
});


this.newJMenuItem.addActionListener((ae)->{
if(Notepad.this.textArea.getText().length()>0)
{
Object[] options={"Save","Don't Save","Cancel"};
int selectedOption=JOptionPane.showOptionDialog(Notepad.this,"Do you want to save changes to "+fileName+"?","Notepad",JOptionPane.YES_NO_CANCEL_OPTION,-1,null,options,options[0]);
if(selectedOption==JOptionPane.CANCEL_OPTION)return;
else if(selectedOption==JOptionPane.YES_OPTION)
{
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));

jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.isDirectory())return true;
if(file.getName().endsWith(".txt"))return true;
return false;
}
public String getDescription()
{
return "Text Documents (*.txt)";
}
});//addChoosableFileFilter function ends here

selectedOption=jfc.showSaveDialog(Notepad.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
String selectedFilePath=selectedFile.getAbsolutePath();
if(selectedFilePath.contains(".")==false)selectedFilePath+=".txt";
File saveFile=new File(selectedFilePath);
File parent=new File(saveFile.getParent());
if(parent.exists()==false||parent.isDirectory()==false)
{
options=new String[1];
options[0]="OK";
JOptionPane.showOptionDialog(Notepad.this,selectedFile.getPath()+"\nPath does not exists.\nCheck the path and try again.","Save As",JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
return;
}
if(saveFile.exists())
{
options=new String[2];
options[0]="Yes";
options[1]="No";
selectedOption=JOptionPane.showOptionDialog(Notepad.this,selectedFile.getName()+" already exists.\n Do you want to replace it?","Confirm Save As",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);  
if(selectedOption==JOptionPane.NO_OPTION)return;
}
BufferedWriter outFile=null;
try
{
outFile=new BufferedWriter(new FileWriter(saveFile));
Notepad.this.textArea.write(outFile);
}catch(Exception exception)
{
//do nothing
}
}
else
{
return;
}
}//yes_option ends here
}// textarea length>0 ends here
this.textArea.setText("");
});


this.newWindowJMenuItem.addActionListener((ae)->{Notepad note=new Notepad();
note.setVisible(true);
note.setDefaultCloseOperation(DISPOSE_ON_CLOSE);});


this.openJMenuItem.addActionListener((ae)->{
Object [] options=null;
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
jfc.setAcceptAllFileFilterUsed(false);
jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter(){
public boolean accept(File file)
{
if(file.isDirectory())return true;
if(file.getName().endsWith(".txt"))return true;
return false;
}
public String getDescription()
{
return "Text Documents (*.txt)";
} 
});
int selectedOption=jfc.showOpenDialog(Notepad.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
String selectedFilePath=selectedFile.getAbsolutePath();
if(selectedFilePath.contains(".")==false)selectedFilePath+=".txt";
if(selectedFilePath.endsWith(".txt")==false)selectedFilePath+=".txt";
File openFile=new File(selectedFilePath);
File parentFile=new File(openFile.getParent());
if(parentFile.exists()==false||parentFile.isDirectory()==false)
{
options=new String[1];
options[0]="OK";
JOptionPane.showOptionDialog(Notepad.this,selectedFile.getAbsolutePath()+"\nPath does not exists.\nCheck the path and try again.","Open",JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
return;
}
if(openFile.exists()==false)
{
options=new String[1];
options[0]="OK";
JOptionPane.showOptionDialog(Notepad.this,selectedFile.getName()+"\nFile not Found.\nCheck the file name  and try again.","Open",JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
return;
}
Notepad.this.fileName=openFile.getName();
Notepad.this.filePath=openFile.getAbsolutePath();
Notepad.this.setTitle(Notepad.this.fileName.substring(0,Notepad.this.fileName.indexOf("."))+" - Notepad");
BufferedReader inFile=null;
try
{
inFile=new BufferedReader(new FileReader(openFile));
Notepad.this.textArea.read(inFile,null);
}catch(Exception exception)
{
//do nothing
}
}//approve_option ends here
});


this.saveJMenuItem.addActionListener((ae)->{
if(Notepad.this.fileName.equalsIgnoreCase("Untitled"))
{
Notepad.this.someCommonAction();
}//untilted file name if  ends here
else
{
try
{
BufferedWriter outFile=new BufferedWriter(new FileWriter(new File(Notepad.this.filePath)));
Notepad.this.textArea.write(outFile);
}catch(Exception exception)
{
//do nothing
}
}
});

this.saveAsJMenuItem.addActionListener((ae)->{Notepad.this.someCommonAction();});


this.printJMenuItem.addActionListener((ae)->{
Object [] options=null;
try
{
Notepad.this.textArea.print();
}catch(Exception exception)
{
options=new String[1];
options[0]="OK";
JOptionPane.showOptionDialog(Notepad.this,exception.getMessage(),"Print",JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
return;
}
});



this.exitJMenuItem.addActionListener((ae)->{System.exit(0);});


this.undoJMenuItem.addActionListener((ae)->{
try 
{
if(Notepad.this.textArea.getText().length()>0)undoManager.undoOrRedo();
} catch (CannotRedoException cre) 
{
//do nothing
}
});


this.cutJMenuItem.addActionListener((ae)->{
Notepad.this.selectedText=Notepad.this.textArea.getSelectedText();
Notepad.this.textArea.replaceRange("",Notepad.this.textArea.getSelectionStart(),Notepad.this.textArea.getSelectionEnd());
});

this.copyJMenuItem.addActionListener((ae)->{
Notepad.this.selectedText=Notepad.this.textArea.getSelectedText();
});

this.pasteJMenuItem.addActionListener((ae)->{
Notepad.this.textArea.insert(Notepad.this.selectedText,Notepad.this.textArea.getCaretPosition());
});

this.deleteJMenuItem.addActionListener((ae)->{
Notepad.this.textArea.replaceRange("",Notepad.this.textArea.getSelectionStart(),Notepad.this.textArea.getSelectionEnd());
});

this.findJMenuItem.addActionListener((ae)->{
TMFindDialog findDialog=new TMFindDialog(Notepad.this.textArea);
findDialog.setAlwaysOnTop(true);
});


this.wordWrapJMenuItem.addActionListener((ae)->{
if(Notepad.this.wordWrapJMenuItem.isSelected())
{
Notepad.this.textArea.setLineWrap(true);
Notepad.this.textArea.setWrapStyleWord(true);
}
else
{
Notepad.this.textArea.setLineWrap(false);
Notepad.this.textArea.setWrapStyleWord(false);
}
});

this.fontJMenuItem.addActionListener((ae)->{

TMFontChooser fontChooser = new TMFontChooser();
    int result = fontChooser.showDialog(Notepad.this);
    if (result == TMFontChooser.OK_OPTION)
    {
       Font font = fontChooser.getSelectedFont(); 
       Notepad.this. textArea.setFont(font);
    }
});
this.zoomInJMenuItem.addActionListener((ae)->{
Font font=Notepad.this.textArea.getFont();
Notepad.this.textArea.setFont(new Font(font.getName(),font.getStyle(),font.getSize()+2));
});

this.zoomOutJMenuItem.addActionListener((ae)->{
Font font=Notepad.this.textArea.getFont();
Notepad.this.textArea.setFont(new Font(font.getName(),font.getStyle(),font.getSize()-2));
});

this.statusBarJMenuItem.addActionListener((ae)->{
Notepad.this.statusBar.setVisible(Notepad.this.statusBarJMenuItem.isSelected());
});


this.aboutNotepadJMenuItem.addActionListener((ae)->{
AboutNotepad an=new AboutNotepad();
an.setVisible(true);
an.toFront();
});


}// addListener function ends here

private void fileLoad(String fileName)
{
String fn=fileName.substring(0,fileName.indexOf('.'));
File openFile=new File(fileName);
if(openFile.exists())
{
this.fileName=fn;
this.setTitle(this.fileName+" - Notepad");
BufferedReader inFile=null;
try
{
inFile=new BufferedReader(new FileReader(openFile));
Notepad.this.textArea.read(inFile,null);
}catch(Exception exception)
{
//do nothing
}
}
else
{
int selectedOption=JOptionPane.showConfirmDialog(this,"Cannot find the "+fileName+" file.\n Do you want to create a new file?","Notepad",JOptionPane.YES_NO_CANCEL_OPTION,JOptionPane.WARNING_MESSAGE);
if(selectedOption==JOptionPane.YES_OPTION)
{
try
{
openFile.createNewFile();
}catch(Exception e)
{
//do nothing
}
this.setTitle(fn);
}
if(selectedOption==JOptionPane.CANCEL_OPTION)
{
System.exit(0);
}
}
}

private void someCommonAction()
{
Object [] options=null;
JFileChooser jfc=new JFileChooser();
jfc.setCurrentDirectory(new File("."));
jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter()
{
public boolean accept(File file)
{
if(file.isDirectory())return true;
if(file.getName().endsWith(".txt"))return true;
return false;
}
public String getDescription()
{
return "Text Documents (*.txt)";
}
});
int selectedOption=jfc.showSaveDialog(Notepad.this);
if(selectedOption==JFileChooser.APPROVE_OPTION)
{
File selectedFile=jfc.getSelectedFile();
String selectedFilePath=selectedFile.getAbsolutePath();
if(selectedFilePath.contains(".")==false)selectedFilePath+=".txt";
File saveFile=new File(selectedFilePath);
File parentFile=new File(saveFile.getParent());
if(parentFile.exists()==false||parentFile.isDirectory()==false)
{
options=new String[1];
options[0]="OK";
JOptionPane.showOptionDialog(Notepad.this,selectedFile.getAbsolutePath()+"\nPath does not exists.\nCheck the path and try again.","Save As",JOptionPane.YES_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);
return;
}
if(saveFile.exists())
{
options=new String[2];
options[0]="Yes";
options[1]="No";
selectedOption=JOptionPane.showOptionDialog(Notepad.this,selectedFile.getName()+" already exists.\n Do you want to replace it?","Confirm Save As",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE,null,options,options[0]);  
if(selectedOption==JOptionPane.NO_OPTION)return;
}
BufferedWriter outFile=null;
try
{
outFile=new BufferedWriter(new FileWriter(saveFile));
Notepad.this.textArea.write(outFile);
}catch(Exception exception)
{
//do nothing
}
Notepad.this.fileName=saveFile.getName();
Notepad.this.filePath=saveFile.getAbsolutePath();
Notepad.this.setTitle(Notepad.this.fileName.substring(0,Notepad.this.fileName.indexOf("."))+"- Notepad");
}//approve option if ends here
}

public static void main(String gg[])
{
Notepad n=new Notepad();
n.setVisible(true);
if(gg.length>0)n.fileLoad(gg[0]);
}
}