/** 
 * this template can be used for a start menu
 * for your final project
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
import java.awt.event.ActionEvent;
import javax.swing.SwingUtilities;

class StartingFrame extends JFrame { 
  
  static boolean[] colorOwned = {true, false, false, false};
  static boolean[] colorEquipped = {true, false, false, false};
  static int points = 0;
  JFrame thisFrame;
  
  //Constructor - this runs first
  StartingFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GREEN);
    
    mainPanel.setLayout(new GridLayout(4,0));
    
    //Create a JButton for the centerPanel
    JButton startButton = new JButton("START");
    startButton.addActionListener(new ButtonListener());
    startButton.setBackground(Color.WHITE);
    
    //Create a tutorial button
    JButton helpButton = new JButton("CONTROLS");
    helpButton.addActionListener(new ButtonListener());
    helpButton.setBackground(Color.PINK);
    
    //shop button
    JButton shopButton = new JButton("SHOP");
    shopButton.addActionListener(new ButtonListener());
    shopButton.setBackground(Color.GREEN);
    
    //Create a JButton for the centerPanel
    JLabel startLabel = new JLabel("Welome to Tanks!");
    startLabel.setHorizontalAlignment(JLabel.CENTER);
    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(startLabel);
    mainPanel.add(shopButton);
    mainPanel.add(startButton);
    mainPanel.add(helpButton);
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class ButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      String command = event.getActionCommand();
      if(command.equals("START")){
        System.out.println("Starting new Game");
        thisFrame.dispose();
        new GameFrame(); //create a new FunkyFrame (another file that extends JFrame)
      }else if(command.equals("CONTROLS")){
        System.out.println("tutorial");
        thisFrame.dispose();
        new TutorialFrame(); //create a new FunkyFrame (another file that extends JFrame)
      }else if(command.equals("SHOP")){
        System.out.println("shop");
        thisFrame.dispose();
        new ShopFrame(); //create a new FunkyFrame (another file that extends JFrame)
      }
      
    }
    
  }
  
  public static void buyBlue(){
      colorOwned[1] = true;
      //System.out.println("test");
    }
  public static void buyGreen(){
    colorOwned[2] = true;
    //System.out.println("test");
  }
  public static void buyYellow(){
    colorOwned[3] = true;
    //System.out.println("test");
  }
  public static void equipRed(){
    colorEquipped[0] = true;
    colorEquipped[1] = false;
    colorEquipped[2] = false;
    colorEquipped[3] = false;
  }
  public static void equipBlue(){
    colorEquipped[0] = false;
    colorEquipped[1] = true;
    colorEquipped[2] = false;
    colorEquipped[3] = false;
  }
  public static void equipGreen(){
    colorEquipped[0] = false;
    colorEquipped[1] = false;
    colorEquipped[2] = true;
    colorEquipped[3] = false;
  }
  public static void equipYellow(){
    colorEquipped[0] = false;
    colorEquipped[1] = false;
    colorEquipped[2] = false;
    colorEquipped[3] = true;
  }
  public static boolean redEquipped(){
    if(colorEquipped[0]){
      return true;
    }else{
      return false;
    }
  }
  public static boolean blueEquipped(){
    if(colorEquipped[1]){
      return true;
    }else{
      return false;
    }
  }
  public static boolean greenEquipped(){
    if(colorEquipped[2]){
      return true;
    }else{
      return false;
    }
  }
  public static boolean yellowEquipped(){
    if(colorEquipped[3]){
      return true;
    }else{
      return false;
    }
  }
  public static int addPoints(int time, int health){
    points += health;
    points += 10000 - time;
    points = points/1000;
    return points;
  }
  
  //Main method starts this application
  public static void main(String[] args) { 
    new StartingFrame();
    
  }
  
}
