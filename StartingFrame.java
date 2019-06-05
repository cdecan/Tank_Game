/** 
 * this template can be used for a start menu
 * for your final project
 **/


//Imports
import java.io.*;
import java.util.Scanner;
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
  static String name = "";
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
    System.out.println("Name: "+ name);
    mainPanel.setLayout(new GridLayout(6,0));
    
    //Create a JButton for the centerPanel
    JButton startButton = new JButton("START");
    startButton.addActionListener(new ButtonListener());
    startButton.setBackground(Color.WHITE);
    
    //Create a tutorial button
    JButton helpButton = new JButton("CONTROLS");
    helpButton.addActionListener(new ButtonListener());
    helpButton.setBackground(Color.PINK);
    
    //shop buttones
    JButton shopButton = new JButton("SHOP");
    shopButton.addActionListener(new ButtonListener());
    shopButton.setBackground(Color.GREEN);
    
    //Create a JButton for the centerPanel
    JLabel startLabel = new JLabel("Welome to Tanks!");
    startLabel.setHorizontalAlignment(JLabel.CENTER);
    
    JButton saveButton = new JButton("SAVE");
    saveButton.addActionListener(new ButtonListener());
    //
    JButton loadButton = new JButton("LOAD");
    loadButton.addActionListener(new ButtonListener());
    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(startLabel);
    mainPanel.add(shopButton);
    mainPanel.add(startButton);
    mainPanel.add(helpButton);
    mainPanel.add(saveButton);
    mainPanel.add(loadButton);
    
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
        new LevelSelectFrame(); //create a new FunkyFrame (another file that extends JFrame)
      }else if(command.equals("CONTROLS")){
        System.out.println("tutorial");
        thisFrame.dispose();
        new TutorialFrame(); //create a new FunkyFrame (another file that extends JFrame)
      }else if(command.equals("SHOP")){
        System.out.println("shop");
        thisFrame.dispose();
        new ShopFrame(); //create a new FunkyFrame (another file that extends JFrame)
      }else if(command.equals("SAVE")){
        System.out.println("SAVING");
        try{
        PrintWriter fileOut = new PrintWriter(new FileWriter("save.txt"));
        //fileOut.println(name);
        fileOut.println("POINTS:");
        fileOut.println(Integer.toString(points));
        System.out.println(Integer.toString(points));
        fileOut.println("Colors:");
        fileOut.println("RED:OWNED");
        if(colorOwned(1)){
          fileOut.println("BLUE:OWNED");
        }else{
          fileOut.println("BLUE:NOT-OWNED");  
        }
        if(colorOwned(2)){
          fileOut.println("GREEN:OWNED");
        }else{
          fileOut.println("GREEN:NOT-OWNED");  
        }
        if(colorOwned(3)){
          fileOut.println("YELLOW:OWNED");
        }else{
          fileOut.println("YELLOW:NOT-OWNED");
        }
        fileOut.close();
        }catch(java.io.IOException e){System.out.println("oops");}
      }else if(command.equals("LOAD")){
       //read in save data----------------------------------------------------------------
    try{
      String txt = "";
    Scanner fileIn = new Scanner (new File ("save.txt"));
        fileIn.nextLine();
        points = fileIn.nextInt();
        do{
          txt += fileIn.nextLine();
          
        }while(fileIn.hasNextLine());//lines >= 0);
        fileIn.close();
        
        
          if(txt.toUpperCase().indexOf("RED:OWNED") >0){
            colorOwned[0] = true;
             
          }else{
            colorOwned[0] = false;
       
          }
          if(txt.toUpperCase().indexOf("BLUE:OWNED") >0){
            colorOwned[1] = true;
           
          }else{
            colorOwned[1] = false;
           
          }
          if(txt.toUpperCase().indexOf("GREEN:OWNED") >0){
            colorOwned[2] = true;
           
          }else{
            colorOwned[2] = false;
           
          }
          if(txt.toUpperCase().indexOf("YELLOW:OWNED") >0){
            colorOwned[3] = true;
            
          }else{
            colorOwned[3] = false;
            
          }
    }catch(java.io.FileNotFoundException e){}
    //------------------------------------------------------------------------ 
      }
      
    }
    
  }
  
  public static boolean colorOwned(int color){
    if(colorOwned[color]){
      return true;
    }else{
      return false;
    }
  }
  public static void buyBlue(){
    if(points - 20 >= 0){
      colorOwned[1] = true;
      points -= 20;
    }else{
      System.out.println("YOU CAN'T AFFORD THAT!");//CHANGE THIS TO ADD TO FRAME INSTEAD
    }
    //System.out.println("test");
  }
  public static void buyGreen(){
    if(points - 30 >= 0){
      colorOwned[2] = true;
      points -=30;
    }else{
      System.out.println("YOU CAN'T AFFORD THAT!");//CHANGE THIS TO ADD TO FRAME INSTEAD
    }
    //System.out.println("test");
  }
  public static void buyYellow(){
    if(points - 40 >= 0){
      colorOwned[3] = true;
      points -= 40;
    }else{
      System.out.println("YOU CAN'T AFFORD THAT!");//CHANGE THIS TO ADD TO FRAME INSTEAD
    }
    //System.out.println("test");
  }
  public static void equipRed(){
    colorEquipped[0] = true;
    colorEquipped[1] = false;
    colorEquipped[2] = false;
    colorEquipped[3] = false;
  }
  public static void equipBlue(){
    if(colorOwned[1]){
      colorEquipped[0] = false;
      colorEquipped[1] = true;
      colorEquipped[2] = false;
      colorEquipped[3] = false;
    }else{
      System.out.println("YOU DON'T OWN THAT!");
    }
  }
  public static void equipGreen(){
    if(colorOwned[2]){
      colorEquipped[0] = false;
      colorEquipped[1] = false;
      colorEquipped[2] = true;
      colorEquipped[3] = false;
    }else{
      System.out.println("YOU DON'T OWN THAT!");
    }
  }
  public static void equipYellow(){
    if(colorOwned[3]){
      colorEquipped[0] = false;
      colorEquipped[1] = false;
      colorEquipped[2] = false;
      colorEquipped[3] = true;
    }else{
      System.out.println("YOU DON'T OWN THAT!");
    }
  }
  /**
   * A method to check if red is equipped
   * @return whether or not the color is equipped
   * @Auth Connor
   * */
  public static boolean redEquipped(){
    if(colorEquipped[0]){
      return true;
    }else{
      return false;
    }
  }
  /**
   * A method to check if blue is equipped
   * @return whether or not the color is equipped
   * @Auth Connor
   * */
  public static boolean blueEquipped(){
    if(colorEquipped[1]){
      return true;
    }else{
      return false;
    }
  }
  /**
   * A method to check if green is equipped
   * @return whether or not the color is equipped
   * @Auth Connor
   * */
  public static boolean greenEquipped(){
    if(colorEquipped[2]){
      return true;
    }else{
      return false;
    }
  }
  /**
   * A method to check if yellow is equipped
   * @return whether or not the color is equipped
   * @Auth Connor
   * */
  public static boolean yellowEquipped(){
    if(colorEquipped[3]){
      return true;
    }else{
      return false;
    }
  }
  /**
   * a method to add points to the player's save
   * @param time the time took to complete the level
   * @param health the amount of health that the player finished off with
   * @return the amount of points that the player earned
   * @auth Connor
   * */
  public static int addPoints(int time, int health){
    points += ((health*(10000-time))/1000);
    return ((health*(10000-time))/1000);
  }
  public static int showPoints(){
    return points;
  }
  
  public static void storeName(String text){
    name = text;
  }
  public static String showName(){
    return name;
  }
  
  //Main method starts this application
  public static void main(String[] args) { 
    new StartingFrame();
    
  }
  
}
