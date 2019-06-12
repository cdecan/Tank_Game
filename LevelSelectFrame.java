/** 
 * LevelSelectFrame.java
 * A program to launch a level select screen
 * Jayden and Connor
 * May 30, 2019
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

class LevelSelectFrame extends JFrame { 
  
  JFrame thisFrame;
  Font font = new Font("Comic Sans MS", Font.PLAIN, 16);
  
  //Constructor - this runs first
  LevelSelectFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    //this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GREEN);
    
    mainPanel.setLayout(new GridLayout(0,3));
    
    //Create a JButton for the centerPanel
    JButton easyButton = new JButton("EASY");
    easyButton.setFont(font);
    easyButton.addActionListener(new ButtonListener());
    easyButton.setBackground(Color.GREEN);
    
    //hard
    JButton hardButton = new JButton("HARD");
    hardButton.setFont(font);
    hardButton.addActionListener(new ButtonListener());
    hardButton.setBackground(Color.RED);
    
    //vs
    JButton vsButton = new JButton("SURVIVAL");
    vsButton.setFont(font);
    vsButton.addActionListener(new ButtonListener());
    vsButton.setBackground(Color.PINK);
    
    //Create a tutorial label
    
    mainPanel.add(easyButton);
    mainPanel.add(hardButton);
    mainPanel.add(vsButton);

    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class ButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      String command = event.getActionCommand();
      if(command.equals("EASY")){
      System.out.println("level 1");
        thisFrame.dispose();
        new GameFrameLevel1(); //create a new FunkyFrame (another file that extends JFrame)
      }else if(command.equals("HARD")){
        System.out.println("level 2");
        thisFrame.dispose();
        new GameFrameLevel2();
      }else if(command.equals("SURVIVAL")){
        thisFrame.dispose();
        new GameFrameLevelVS();
      }
    }
    
  }
  
  
  
}
