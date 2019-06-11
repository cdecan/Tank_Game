/** 
 * GameFrameLevel2.java
 * A program to launch a VS mode screen
 * Jayden and Connor
 * May 27, 2019
 **/
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.awt.image.*;
//Graphics & GUI imports
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//sound imports
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.*;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GameFrameLevel2 extends JFrame { 
  
  //class variable (non-static)
  static double x, y;
  static double ballX, ballY;
  static double targX, targY;
  static double trapX, trapY, trapX2, trapY2, trapX3, trapY3;
  boolean dead = false, targDestroyed = false;
  boolean moveLeft;
  boolean moveRight;
  boolean moveUp;
  boolean moveDown;
  boolean faceUp = true, faceDown = false, faceLeft = false, faceRight = false;
  boolean moveUp2, moveDown2, moveLeft2, moveRight2;
  int timeLimit = 0;
  int countDown = 1;
  Square square = new Square();
  Tank tank = new Tank();
  BufferedImage image;
  BufferedImage ballDead;
  BufferedImage timerImage;
  Clip clip = StartingFrame.music(2);
  
  //Constructor - this runs first
  GameFrameLevel2() { 
    
    super("My Game");
    
    this.setSize(1366, 768);
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
    
    try {                
      image = ImageIO.read(new File("Images/explode.png"));
      ballDead = ImageIO.read(new File("Images/pop.png"));
      timerImage = ImageIO.read(new File("Images/time"+Integer.toString(countDown)+ ".png"));
    } catch (IOException ex) {
      System.out.println("Error: Image(s) not found");
    }
    //Tank tank = new Tank();
    //tank.setPreferredSize(new Dimension(100, 100));
    //Set up the game panel (where we put our graphics)
    Square square = new Square();
    square.setLayout(new BorderLayout());
    square.add(tank);
    
    this.add(square);
    
    
    MyKeyListener keyListener = new MyKeyListener(); 
    this.addKeyListener(keyListener);
    
    this.requestFocusInWindow(); //make sure the frame has focus   
    
    this.setVisible(true);
    
    //Start the game loop in a separate thread
    Thread t = new Thread(new Runnable() { public void run() { animate(); }}); //start the gameLoop 
    t.start();
    
  } //End of Constructor
  
  //the main gameloop - this is where the game state is updated
  public void animate() { 
    this.x = (100);  //update coords
    this.y = (100);
    this.ballX = x;
    this.ballY = y;
    this.targX = (int)(1100);
    this.targY = (int)(200);
    trapX = 250;
    trapY = 300;
    trapX2 = 450;
    trapY2 = 400;
    trapX3 = 650;
    trapY3 = 200;
      
    faceUp = true;
    boolean run = true;
    
    int timeLimit = 0;
    while(run){
      timeLimit++;
      if (timeLimit%1000 == 0) {
        countDown++;
        try {                
          
          timerImage = ImageIO.read(new File("Images/time"+Integer.toString(countDown)+ ".png"));
        } catch (IOException ex) {
          System.out.println("Error: Image(s) not found");
        }
      }
      
      if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceUp)){
        ballX = x+12;
        ballY = y-30;
      }else if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceDown)){
        ballX = x+12;
        ballY = y+120;
      }else if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceLeft)){
        ballX = x-40;
        ballY = y+10;
      }else if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceRight)){
        ballX = x+120;
        ballY = y+10;
      }
      try{ Thread.sleep(1);} catch (Exception exc){}  //delay
      //  square.remove(label);
      this.repaint();
      
      //square.repaint();
      
      if((collision(getBoundsBall(),getBoundsTarget()))&&((moveUp2)||(moveDown2)||(moveLeft2)||(moveRight2))){
        targDestroyed = true;
        try{
          Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        clip.close();
        new WinFrame(true, timeLimit, 3);
      }
      
      if((collision(getBoundsPlayer(),getBoundsRoof()))){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsFloor()))){
        y--;
      }else if((collision(getBoundsPlayer(),getBoundsRWall()))){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsLWall()))){
        x++;
      }
      
      if((collision(getBoundsPlayer(),getBoundsInWall1())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall1())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall1())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall1())) && (faceDown)){
        y--;
      }
      
      if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceDown)){
        y--;
      }
      
      if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceDown)){
        y--;
      }
      
      if((collision(getBoundsPlayer(),getBoundsInWall4())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall4())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall4())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall4())) && (faceDown)){
        y--;
      }
      
      if((collision(getBoundsPlayer(),getBoundsInWall5())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall5())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall5())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall5())) && (faceDown)){
        y--;
      }
      if((collision(getBoundsPlayer(),getBoundsInWall6())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall6())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall6())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall6())) && (faceDown)){
        y--;
      }
      if((collision(getBoundsBall(),getBoundsInWall1()))||(collision(getBoundsBall(),getBoundsInWall2()))||(collision(getBoundsBall(),getBoundsInWall3()))||(collision(getBoundsBall(),getBoundsInWall4()))||(collision(getBoundsBall(),getBoundsInWall5()))||(collision(getBoundsBall(),getBoundsInWall6()))||(collision(getBoundsBall(),getBoundsRoof()))||(collision(getBoundsBall(),getBoundsFloor()))||(collision(getBoundsBall(),getBoundsLWall()))||(collision(getBoundsBall(),getBoundsRWall()))){
        moveUp2 = false;
        moveDown2 = false;
        moveLeft2 = false;
        moveRight2 = false;
      }
      
      
      
      if((collision(getBoundsPlayer(),getBoundsTrap()))||(collision(getBoundsPlayer(),getBoundsTrap2()))||(collision(getBoundsPlayer(),getBoundsTrap3()))||(collision(getBoundsPlayer(),getBoundsTrap4()))||(collision(getBoundsPlayer(),getBoundsTrap5()))||(collision(getBoundsPlayer(),getBoundsTrap6()))){
        dead = true;
        try{
          Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        clip.close();
        new WinFrame(false, timeLimit, 0);
      }
      
      if(timeLimit == 10000){
        dead = true;
        try{
          Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        clip.close();
        new WinFrame(false, timeLimit, 0);
      }
      
    }    
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  
  private class Square extends JPanel{
    Tank tank = new Tank();
    
    Square(){
      add(tank);
    }
  }
  
  
  
  
  
  private class Tank extends JComponent{
    //double x,y;
    // boolean moveLeft, moveRight,moveUp,moveDown;
    Tank(){
      //   x = 100;
      //   y = 100;
    }
    public void paintComponent(Graphics g){
      super.paintComponent(g);
      setDoubleBuffered(true);
      //timer
      g.drawImage(timerImage, 851, 100, this);
      
      //WALLS//////////////////////////////////////////////////
      //border
      g.setColor(Color.BLACK);
      g.fillRect(0,0, 1366, 50);
      g.fillRect(0,718,1368,50);
      g.fillRect(0,0,50,768);
      g.fillRect(1316,0,50,768);
      
      //inner
      g.setColor(Color.BLACK);
      g.fillRect(300,200,200,150);
      g.fillRect(0,500, 500, 50);
      g.fillRect(700,350,80,80);
      g.fillRect(700,600,50,130);
      g.fillRect(1000,350,300,50);
      g.fillRect(700,0, 50, 300);
      //g.fillRect(700,250,20,518);
      //g.fillRect(700,250,20,518);
      
      //TRAP////////////////////////////////////////////////////
      g.setColor(Color.RED);
      g.fillOval((int)trapX,(int)trapY,30,30);
      g.setColor(Color.GREEN);
      g.fillOval((int)trapX+5,(int)trapY+5,20,20);
      //2
      g.setColor(Color.RED);
      g.fillOval((int)trapX2,(int)trapY2,30,30);
      g.setColor(Color.GREEN);
      g.fillOval((int)trapX2+5,(int)trapY2+5,20,20);
      //3
      g.setColor(Color.RED);
      g.fillOval((int)trapX3,(int)trapY3,30,30);
      g.setColor(Color.GREEN);
      g.fillOval((int)trapX3+5,(int)trapY3+5,20,20);
      //4
      g.setColor(Color.RED);
      g.fillOval(1000,500,30,30);
      g.setColor(Color.GREEN);
      g.fillOval(1005,505,20,20);
      //5
      g.setColor(Color.RED);
      g.fillOval(800,500,30,30);
      g.setColor(Color.GREEN);
      g.fillOval(805,505,20,20);
      //6
      g.setColor(Color.RED);
      g.fillOval(800,400,30,30);
      g.setColor(Color.GREEN);
      g.fillOval(805,405,20,20);
      //TARGET//////////////////////////////////////////////////
      g.setColor(Color.BLACK);
      g.fillOval((int) targX, (int) targY, 60, 60);
      g.setColor(Color.WHITE);
      g.fillOval((int) targX+5, (int) targY+5, 50, 50);
      g.setColor(Color.PINK);
      g.fillOval((int) targX+10, (int) targY+10, 40, 40);
      g.setColor(Color.YELLOW);
      g.fillOval((int) targX+15, (int) targY+15, 30, 30);
      g.setColor(Color.BLUE);
      g.fillOval((int) targX+20, (int) targY+20, 20, 20);
      g.setColor(Color.WHITE);
      g.fillOval((int) targX+25, (int) targY+25, 10, 10);
      ////////////////////////////////////////////////////////////
      ///dead
      if(dead){
        g.drawImage(image, (int)x-300, (int)y-300, this);
      }
      if(targDestroyed){
        g.drawImage(ballDead, (int)ballX-300, (int)ballY-100, this);
      }
      //MOVEMENT////////////////////////////////////////////
      if (moveLeft){
        x--;
      } else if (moveRight){
        x++;
      }else if (moveUp){
        y--;
      }else if(moveDown){
        y++;
      }//player
      
      if (moveLeft2){
        moveRight2 = false;
        moveUp2 = false;
        moveDown2 = false;
        g.setColor(Color.CYAN);
        g.fillOval((int)ballX,(int)ballY,20,20);
        ballX--;
        
      } else if (moveRight2){
        moveLeft2 = false;
        moveUp2 = false;
        moveDown2 = false;
        g.setColor(Color.CYAN);
        g.fillOval((int)ballX,(int)ballY,20,20);
        ballX++;
        
      }else if (moveUp2){
        moveRight2 = false;
        moveLeft2 = false;
        moveDown2 = false;
        g.setColor(Color.CYAN);
        g.fillOval((int)ballX,(int)ballY,20,20);
        ballY--;
        
      }else if(moveDown2){
        moveRight2 = false;
        moveUp2 = false;
        moveLeft2 = false;
        g.setColor(Color.CYAN);
        g.fillOval((int)ballX,(int)ballY,20,20);
        ballY++;
      }
      /////////////////////////////////////////////////
      
      //PLAYER//////////////////////////////////////
      //RED
      if(StartingFrame.redEquipped()){
        if(faceUp){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.RED);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y-25,5,29);
        
        }else if(faceDown){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.RED);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y+100,5,29);        
          
        }else if(faceLeft){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.RED);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x-24,(int)y+20,29,5);
          
        }else if(faceRight){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.RED);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+100,(int)y+22,29,5);
        }
        //BLUE
      }else if(StartingFrame.blueEquipped()){
        if(faceUp){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.BLUE);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y-25,5,29);
        
        }else if(faceDown){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.BLUE);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y+100,5,29);        
          
        }else if(faceLeft){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.BLUE);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x-24,(int)y+20,29,5);
          
        }else if(faceRight){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.BLUE);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+100,(int)y+22,29,5);
        }
        //GREEN
      }else if(StartingFrame.greenEquipped()){
        if(faceUp){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.GREEN);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y-25,5,29);
        
        }else if(faceDown){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.GREEN);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y+100,5,29);        
          
        }else if(faceLeft){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.GREEN);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x-24,(int)y+20,29,5);
          
        }else if(faceRight){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.GREEN);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+100,(int)y+22,29,5);
          //YELLOW
        }
      }else if(StartingFrame.yellowEquipped()){
        if(faceUp){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.YELLOW);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y-25,5,29);
        
        }else if(faceDown){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,50,100);
          g.fillRoundRect((int)x-15,(int)y,15,100,10,10);
          g.fillRoundRect((int)x+50,(int)y,15,100,10,10);
          g.setColor(Color.YELLOW);
          g.fillOval((int)x+5,(int)y+5,40,90);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+22,(int)y+100,5,29);        
          
        }else if(faceLeft){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.YELLOW);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x-24,(int)y+20,29,5);
          
        }else if(faceRight){
          g.setColor(Color.BLACK);
          g.fillOval((int)x,(int)y,100,50);
          g.fillRoundRect((int)x,(int)y-15,100,15,10,10);
          g.fillRoundRect((int)x,(int)y+50,100,15,10,10);
          g.setColor(Color.YELLOW);
          g.fillOval((int)x+5,(int)y+5,90,40);
          g.setColor(Color.BLACK);
          g.fillRect((int)x+100,(int)y+22,29,5);
        }
      }//colors: all copy-pasted except with a change in color
      
      
      
    }
    
    
  }
  
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    
    public void keyTyped(KeyEvent e) {  
    }
    
    public void keyPressed(KeyEvent e) {
      //System.out.println("keyPressed="+KeyEvent.getKeyText(e.getKeyCode()));
      
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {  //If 'D' is pressed
        moveRight = true;
        moveDown = false;
        moveLeft = false;
        moveUp = false;
        faceRight = true;
        faceLeft= false;
        faceUp = false;
        faceDown = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
        moveDown = true;
        moveUp = false;
        moveLeft = false;
        moveRight = false;
        faceDown = true;
        faceLeft= false;
        faceUp = false;
        faceRight = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
        moveLeft = true;
        moveDown = false;
        moveUp = false;
        moveRight = false;
        faceLeft = true;
        faceRight= false;
        faceUp = false;
        faceDown = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
        //for(int i = 100; i>1; i--){
        moveUp = true;
        moveDown = false;
        moveLeft = false;
        moveRight = false;
        faceRight = false;
        faceLeft= false;
        faceUp = true;
        faceDown = false;
        //}
      }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        //System.out.println("ESCAPE KEY!"); //close frame & quit
        System.exit(0);
      }else if((KeyEvent.getKeyText(e.getKeyCode()).equals("Q"))&&(moveUp2 == false) && (moveDown2 == false)&&(moveLeft2 == false)&&(moveRight2 ==false)){
        if(faceUp){
          moveUp2 = true;
          moveDown2 = false;
          moveRight2 = false;
          moveLeft2 = false;
          
        }else if(faceDown){
          moveDown2 = true;
          moveUp2 = false;
          moveRight2 = false;
          moveLeft2 = false;
          
        }else if(faceLeft){
          moveLeft2 = true;
          moveDown2 = false;
          moveRight2 = false;
          moveUp2 = false;
          
        }else if(faceRight){
          moveRight2 = true;
          moveDown2 = false;
          moveUp2 = false;
          moveLeft2 = false;
          
        }
      }else if((KeyEvent.getKeyText(e.getKeyCode()).equals("E"))&&((moveUp2)||(moveDown2)||(moveLeft2)||(moveRight2))){
        if(moveUp2 == true){
          x = ballX-15;
          y = ballY-55;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }else if(moveDown2 == true){
          x = ballX-15;
          y = ballY-55;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }else if(moveLeft2 == true){
          x = ballX-55;
          y = ballY-15;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }else if(moveRight2 == true){
          x = ballX-55;
          y = ballY-15;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }
      }
    }   
    
    public void keyReleased(KeyEvent e) {
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
        moveLeft = false;
        // faceLeft = false;
      } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
        moveRight = false;
        // faceRight = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
        moveDown = false;
        //  faceDown = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
        moveUp = false;
        // faceUp = false;
      }
    }
  } //end of keyboard listener
  
  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
  
  public Rectangle getBoundsPlayer() {
    if((faceUp)||(faceDown)){
      return new Rectangle((int)x-15,(int) y, 80, 100);
    }else{
      return new Rectangle((int)x, (int)y-15, 100, 80);        
    }
  }
  public Rectangle getBoundsBall() {
    return new Rectangle((int)ballX,(int) ballY, 20, 20);
  }
  public Rectangle getBoundsTarget() {
    return new Rectangle((int)targX,(int) targY, 60, 60);
  }
  
  public Rectangle getBoundsTrap() {
    return new Rectangle((int)trapX, (int) trapY, 30, 30);
  }
  public Rectangle getBoundsTrap2() {
    return new Rectangle((int)trapX2, (int) trapY2, 30, 30);
  }
  public Rectangle getBoundsTrap3() {
    return new Rectangle((int)trapX3, (int) trapY3, 30, 30);
  }
  public Rectangle getBoundsTrap4(){
    return new Rectangle(1000,500,30,30);
  }
  public Rectangle getBoundsTrap5(){
    return new Rectangle(800,500,30,30);
  }
  public Rectangle getBoundsTrap6(){
    return new Rectangle(800,400,30,30);
  }
  public Rectangle getBoundsRoof(){
    return new Rectangle(0, 0, 1366, 50);
  }
  public Rectangle getBoundsFloor(){
    return new Rectangle(0,718, 1366, 50);
  }
  public Rectangle getBoundsRWall(){
    return new Rectangle(1316, 0, 50, 768);
  }
  public Rectangle getBoundsLWall(){
    return new Rectangle(0, 0, 50,768);
  }
  public Rectangle getBoundsInWall1(){
    return new Rectangle(300,200,200,150);
  }
  public Rectangle getBoundsInWall2(){
    return new Rectangle(0, 500, 500,50);
  }
  public Rectangle getBoundsInWall3(){
    return new Rectangle(700,350,80,80);
  }
  public Rectangle getBoundsInWall4(){
    return new Rectangle(700,600,50,130);
  }
  public Rectangle getBoundsInWall5(){
    return new Rectangle(1000,350,300,50);
  }
  public Rectangle getBoundsInWall6(){
    return new Rectangle(700,0, 50, 300);
  }
  
  //collision booleans
  /**
   * a method to check for collision between 2 rectangles
   * @param obj1 the first object to be checked
   * @param obj2 the second object to be checked
   * @return whether or not the objects intersect
   * */
  public boolean collision(Rectangle obj1, Rectangle obj2){
    return obj1.intersects(obj2);
  }
}

