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

class WinFrame extends JFrame { 
  
  JFrame thisFrame;
  
  //Constructor - this runs first
  WinFrame(boolean won) { 
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
    JLabel winLabel = new JLabel("YOU WIN!");
    JLabel lossLabel = new JLabel("YOU LOSE!");
    if(won){
    mainPanel.add(winLabel);
    }else{
    mainPanel.add(lossLabel);
    }
    mainPanel.add(returnButton);

    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
  class ButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      System.out.println("returning to thing");
        thisFrame.dispose();
        new StartingFrame(); //create a new FunkyFrame (another file that extends JFrame)
      
    }
    
  }
  
  
  
}