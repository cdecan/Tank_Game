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

class TutorialFrame extends JFrame { 
  
  JFrame thisFrame;
  
  //Constructor - this runs first
  TutorialFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(400,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    //this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GRAY);
    
    mainPanel.setLayout(new GridLayout(10,0));
    
    //Create a JButton for the centerPanel
    JButton returnButton = new JButton("RETURN");
    returnButton.addActionListener(new ButtonListener());
    returnButton.setBackground(Color.WHITE);
    
    //Create a tutorial label
    JLabel[] help = {new JLabel("CONTROLS: "),new JLabel("W,A,S,D ARE TO MOVE YOUR TANK"),new JLabel("Q IS TO SHOOT A BALL"),new JLabel("E IS TO TELEPORT"),new JLabel("THE OBJECTIVE OF THE GAME IS TO SHOOT THE BEACH BALL BEFORE TIME RUNS OUT"),new JLabel("GETTING HIT BY A TRAP WILL CAUSE YOU TO LOSE A LIFE"),new JLabel("YOUR SCORE IS DETERMINED BY THE TIME YOU TOOK AND THE HEALTH YOU HAVE REMAINING"),new JLabel("THE MORE HEALTH YOU HAVE AND THE LESS TIME YOU TAKE, THE HIGHER SCORE YOU WILL RECIEVE"),new JLabel("THERE IS ALSO A SECRET VS MODE, WHICH WE WILL LET YOU FIGURE OUT ON YOUR OWN ;)")};
    mainPanel.add(returnButton);
    for(int i = 0; i < help.length; i++){
      mainPanel.add(help[i]);
    }
    
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
