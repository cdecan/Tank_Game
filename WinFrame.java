/** 
 * WinFrame.java
 * A program to launch a win screen
 * Jayden and Connor
 * May 26, 2019
 **/


//Imports
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class WinFrame extends JFrame { 
  
  JFrame thisFrame;
  
  //Constructor - this runs first
  WinFrame(boolean won, int time, int health) { 
    super("Win Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    //this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GREEN);
    
    mainPanel.setLayout(new GridLayout(3,0));
    
    //Create a JButton for the centerPanel
    JButton returnButton = new JButton("RETURN");
    returnButton.addActionListener(new ButtonListener());
    returnButton.setBackground(Color.WHITE);
    
    //Create a tutorial label
    JLabel winLabel = new JLabel(StartingFrame.showName()+", YOU WIN!");
    int pointsGained = StartingFrame.addPoints(time, health);
    String pointString = Integer.toString(pointsGained);
    JLabel points = new JLabel ("YOU EARNED " + pointString + " POINTS!");
    JLabel lossLabel = new JLabel(StartingFrame.showName()+", YOU LOSE!");
    if(won){
    mainPanel.add(winLabel);
    }else{
    mainPanel.add(lossLabel);
    }
    mainPanel.add(returnButton);
    mainPanel.add(points);

    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }//constructor
  
  //This is an inner class that is used to detect a button press
  class ButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("returning to thing");
        thisFrame.dispose();
        new StartingFrame(); //create a new FunkyFrame (another file that extends JFrame)
      
    }//action event
    
  }//actionlistener
  
  
  
}//winframe
