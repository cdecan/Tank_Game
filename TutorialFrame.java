/** 
 * TutorialFrame.java
 * A program to launch a tutorial screen
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

class TutorialFrame extends JFrame { 
  
  JFrame thisFrame;
  Font font = new Font("Comic Sans MS", Font.PLAIN, 13);
  
  //Constructor - this runs first
  TutorialFrame() { 
    super("Start Screen");
    this.thisFrame = this; //lol  
    
    //configure the window
    this.setSize(800,700);    
    this.setLocationRelativeTo(null); //start the frame in the center of the screen
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
    //this.setResizable (false);
    
    //Create a Panel for stuff
    JPanel mainPanel = new JPanel();
    mainPanel.setBackground(Color.GRAY);
    
    mainPanel.setLayout(new GridLayout(10,0));
    
    //Create a JButton for the centerPanel
    JButton returnButton = new JButton("RETURN");
    returnButton.setFont(font);
    returnButton.addActionListener(new ButtonListener());
    returnButton.setBackground(Color.WHITE);
    
    //Create a tutorial label
    JLabel[] help = {new JLabel("CONTROLS: "),
      new JLabel("W,A,S,D ARE TO MOVE YOUR TANK"),
      new JLabel("J IS TO SHOOT A BALL"),
      new JLabel("K IS TO TELEPORT"),
      new JLabel("THE OBJECTIVE OF THE GAME IS TO SHOOT THE 'BEACH BALL' (TARGET) BEFORE TIME RUNS OUT"),
      new JLabel("GETTING HIT BY A TRAP WILL CAUSE YOU TO DIE INSTANTLY"),
      new JLabel("YOUR SCORE IS DETERMINED BY THE TIME YOU TOOK"),
      new JLabel("THE LESS TIME YOU TAKE, THE HIGHER SCORE YOU WILL RECIEVE"),
      new JLabel("THERE IS ALSO A VS MODE, IN WHICH THE BALL IS TRYING TO HIT YOU! RUN UNTIL TIME RUNS OUT, IF YOU CAN!")};
    mainPanel.add(returnButton);
    for(int i = 0; i < help.length; i++){
      help[i].setFont(font);
      help[i].setHorizontalAlignment(JLabel.CENTER);
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
      
    }//action event
    
  }//actionlistener
  
  
  
}//tutorialframe
