/** 
 * NameFrame.java
 * A program to launch a name select screen
 * Jayden and Connor
 * June 3, 2019
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
import java.io.*;
import java.util.Scanner;



class NameFrame extends JFrame { 
  
  JFrame thisFrame;
  String name;
      JTextField nameField = new JTextField("Name", 20);
  //Constructor - this runs first
  NameFrame() { 
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
    
    //Create a JLabel for the centerPanel
    JLabel hello = new JLabel("Before you play, we're gonna need your name :)))");
    
    //Create a JButton for the centerPanel
    JButton startButton = new JButton("START");
    startButton.addActionListener(new StartButtonListener());
    startButton.setBackground(Color.WHITE);
    
    //Create a JLabel for the centerPanel
    JLabel nameLabel = new JLabel("Enter your name here:");
    nameLabel.setHorizontalAlignment(JLabel.CENTER);
    
    //Create a JTextField for the centerPanel

    
    //Add all panels to the mainPanel according to border layout
    mainPanel.add(hello);
    mainPanel.add(startButton);
    mainPanel.add(nameLabel);
    mainPanel.add(nameField);
    
    //add the main panel to the frame
    this.add(mainPanel);
    
    //Start the app
    this.setVisible(true);
  }
  
  //This is an inner class that is used to detect a button press
 class StartButtonListener implements ActionListener {  //this is the required class definition
    public void actionPerformed(ActionEvent event)  {  
      String command = event.getActionCommand();
      if(command.equals("START")){
        System.out.println("Starting new Game");
        name = nameField.getText();
        StartingFrame.storeName(name);
        thisFrame.dispose();
        new StartingFrame();
      }
    }

  }
  
  
  //Main method starts this application
  public static void main(String[] args) { 
    new NameFrame();

  }
  
}
