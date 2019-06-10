/** 
 * ShopFrame.java
 * A program to launch a shop screen
 * Jayden and Connor
 * May 28, 2019
 **/


//Imports
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

///////////////////////////////////////////////////////////////////

class ShopFrame extends JFrame { 
  
  JFrame thisFrame;
  int pointsOwned = StartingFrame.showPoints();
  JLabel points = new JLabel(Integer.toString(pointsOwned));
  JButton[] buyButton = {new JButton("OWNED"),new JButton("BUY (20)"),new JButton("BUY (30)"),new JButton("BUY (40)")};
  JButton[] ownedButton = {new JButton("OWNED"),new JButton("OWNED"),new JButton("OWNED"),new JButton("OWNED")};
  //Constructor - this runs first
  ShopFrame() { 
    
    super("Shop");
    this.thisFrame = this; //lol
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    
    JLabel pointsLabel = new JLabel("POINTS: ");
    
    mainPanel.setBackground(Color.GREEN);
    
    mainPanel.setLayout(new GridLayout(4,1));
    
    ButtonListener listener = new ButtonListener();
    
    //Create a JButton for the centerPanel
    JButton returnButton = new JButton("RETURN");
    returnButton.addActionListener(listener);
    returnButton.setBackground(Color.WHITE);
    JButton returnButton2 = new JButton("RETURN");
    returnButton2.addActionListener(listener);
    returnButton2.setBackground(Color.WHITE);
    
    //Create a tutorial label
    //JLabel helpLabel = new JLabel("THIS IS THE SHOP");
    
    //create buy buttons and equip buttons
    
    JButton[] equipButton = {new JButton("EQUIP (R)"),new JButton("EQUIP (B)"),new JButton("EQUIP (G)"),new JButton("EQUIP (Y)")};
    
    for(int addlisteners = 0; addlisteners < 4; addlisteners++){
      buyButton[addlisteners].addActionListener(listener);
      buyButton[addlisteners].setBackground(Color.PINK);
      equipButton[addlisteners].addActionListener(listener);
      equipButton[addlisteners].setBackground(Color.GRAY);
    }
    
    String[] color = new String[4];
    color[0] = "RED";
    color[1] = "BLUE";
    color[2] = "GREEN";
    color[3] = "YELLOW";
    
    RedTank red = new RedTank();
    GreenTank green = new GreenTank();
    BlueTank blue = new BlueTank();
    YellowTank yellow = new YellowTank();
    
    
    //Create Images
    
    
    //mainPanel.add(helpLabel);
    // mainPanel.add(returnButton);
    mainPanel.add(returnButton);
    mainPanel.add(pointsLabel);
    mainPanel.add(points);
    mainPanel.add(returnButton2);
    for(int i = 0;i<4;i++){
      mainPanel.add(buyButton[i]);
      if(StartingFrame.colorOwned(i)){
        buyButton[i].setText("OWNED");
        repaint();
      }
    }
    
    mainPanel.add(red);
    mainPanel.add(blue);
    mainPanel.add(green);
    mainPanel.add(yellow);
    for(int o = 0;o<4;o++){
      mainPanel.add(equipButton[o]);
    }    
    
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
    
    
  }
  private class MyKeyListener implements KeyListener {
    
    public void keyTyped(KeyEvent e) {  
    }
    
    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("B")) {  //If 'B' is pressed
         StartingFrame.equipBlue();
        if(StartingFrame.blueEquipped()){
        System.out.println("EQUIPPED!");
        }
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("G")) {  //If 'G' is pressed
         StartingFrame.equipGreen();
        if(StartingFrame.greenEquipped()){
        System.out.println("EQUIPPED!");
        }
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("Y")) {  //If 'Y' is pressed
         StartingFrame.equipYellow();
        if(StartingFrame.yellowEquipped()){
        System.out.println("EQUIPPED!");
        }
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("R")) {  //If 'R' is pressed
         StartingFrame.equipRed();
        if(StartingFrame.redEquipped()){
        System.out.println("EQUIPPED!");
        }
      }
      }
    public void keyReleased(KeyEvent e) {
    }
  }
  //This is an inner class that is used to detect a button press
  class ButtonListener implements ActionListener{//this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      String command = event.getActionCommand();
      if(command.equals("RETURN")){
        System.out.println("returning to thing");
        thisFrame.dispose();
        new StartingFrame(); //create a new StartingFrame (another file that extends JFrame)
      }else if(command.equals("BUY (20)")){
        //System.out.println("test");
        StartingFrame.buyBlue();
        pointsOwned = StartingFrame.showPoints();
        points.setText(Integer.toString(pointsOwned));
        if(StartingFrame.colorOwned(1)){
        buyButton[1].setText("OWNED");
        }
        repaint();
      }else if(command.equals("BUY (30)")){
        StartingFrame.buyGreen();
        pointsOwned = StartingFrame.showPoints();
        points.setText(Integer.toString(pointsOwned));
        if(StartingFrame.colorOwned(2)){
        buyButton[2].setText("OWNED");
        }
        repaint();
      }else if(command.equals("BUY (40)")){
        StartingFrame.buyYellow();
        pointsOwned = StartingFrame.showPoints();
        points.setText(Integer.toString(pointsOwned));
        if(StartingFrame.colorOwned(3)){
        buyButton[3].setText("OWNED");
        }
        repaint();
      }else if(command.equals("EQUIP (R)")){
        StartingFrame.equipRed();
        System.out.println("EQUIPPED!");
      }else if(command.equals("EQUIP (B)")){
        StartingFrame.equipBlue();
        if(StartingFrame.blueEquipped()){
        System.out.println("EQUIPPED!");
        }
      }else if(command.equals("EQUIP (G)")){
        StartingFrame.equipGreen();
        if(StartingFrame.greenEquipped()){
        System.out.println("EQUIPPED!");
        }
      }else if(command.equals("EQUIP (Y)")){
        StartingFrame.equipYellow();
        if(StartingFrame.yellowEquipped()){
        System.out.println("EQUIPPED!");
        }
      }
    }
    
  }
  class RedTank extends JComponent{
    RedTank(){
    }
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.RED);
      g.fillOval(15,15,40,90);
      
    }
  }
  class GreenTank extends JComponent{
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.GREEN);
      g.fillOval(15,15,40,90);      
    }
  }
  class BlueTank extends JComponent{
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.BLUE);
      g.fillOval(15,15,40,90);      
    }
  }
  class YellowTank extends JComponent{
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      g.setColor(Color.BLACK);
      g.fillOval(10,10,50,100);
      g.setColor(Color.YELLOW);
      g.fillOval(15,15,40,90);      
    }
  }
  
  
}
