/**
 * 
 * @author C&J
 **/
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.File;
//Graphics & GUI imports
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;
import javax.imageio.ImageIO;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//sound imports
import javax.sound.sampled.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.*;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameFrameLevelVS extends JFrame { 
  
  //class variable (non-static)
  static double x, y;
  static double dx, dy;
  static double targX, targY;
  static double trapX, trapY, trapX2, trapY2, trapX3, trapY3;
  
  boolean dead = false;
  
  boolean moveLeft;
  boolean moveRight;
  boolean moveUp;
  boolean moveDown;
  boolean faceUp = true, faceDown = false, faceLeft = false, faceRight = false;
  boolean moveUp2, moveDown2, moveLeft2, moveRight2;
  boolean targMoveUp, targMoveDown,targMoveLeft,targMoveRight;
  int timeLimit = 0;
  int countDown = 1;
  Square square = new Square();
  Tank tank = new Tank();
  
  
  BufferedImage image;
  BufferedImage timerImage;

  Clip clip = StartingFrame.music(3);
  
  //Constructor - this runs first
  GameFrameLevelVS() { 
    
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
    targMoveLeft = false;
    targMoveRight = false;
    targMoveUp = false;
    targMoveDown = false;
    
    try {                
          image = ImageIO.read(new File("Images/explode.png"));
          timerImage = ImageIO.read(new File("Images/time"+Integer.toString(countDown)+ ".png"));
       } catch (IOException ex) {
            // handle exception...
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
    
    MyMouseListener mouseListener = new MyMouseListener();
    this.addMouseListener(mouseListener);
    
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
    this.dx = x;
    this.dy = y;
    this.targX = (int)(1000);
    this.targY = (int)(300);
    trapX = 300;
    trapY = 300;
    trapX2 = 500;
    trapY2 = 400;
    trapX3 = 700;
    trapY3 = 200;
    
    faceUp = true;
    

    
    int timeLimit = 0;
    boolean run = true;
    while(run){
      
      timeLimit++;
      if (timeLimit%1000 == 0) {
        countDown++;
              try {                
          
          timerImage = ImageIO.read(new File("Images/time"+Integer.toString(countDown)+ ".png"));
       } catch (IOException ex) {
            // handle exception...
       }
        System.out.println("TIME LEFT: " + (10-(timeLimit/1000)));
      }
      
      if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceUp)){
        dx = x+12;
        dy = y-30;
      }else if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceDown)){
        dx = x+12;
        dy = y+120;
      }else if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceLeft)){
        dx = x-40;
        dy = y+10;
      }else if((moveUp2 == false) && (moveDown2 == false) &&( moveLeft2 == false )&& (moveRight2 == false)&&(faceRight)){
        dx = x+120;
        dy = y+10;
      }
      try{ Thread.sleep(1);} catch (Exception exc){}  //delay
      //  square.remove(label);
      this.repaint();
      
      //square.repaint();
      
      
      if(targCollision()){
        dead = true;
        try{
        Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        clip.close();
        new WinFrame(false, timeLimit, 0);
      }
      

      if ((int)(Math.random()*2) == 0){
        if(x > targX){
          targMoveRight = true;
          targMoveLeft= false;
          targMoveDown = false;
          targMoveUp = false;
        }else if(x < targX){
          targMoveRight = false;
          targMoveLeft= true;
          targMoveDown = false;
          targMoveUp = false;
        }
      } else {
        if(y > targY){
          targMoveRight = false;
          targMoveLeft= false;
          targMoveDown = true;
          targMoveUp = false;
        }else if(y < targY){
          targMoveRight = false;
          targMoveLeft= false;
          targMoveDown = false;
          targMoveUp = true;
        }
      }      
      if(roofCollision()){
        y++;
      }else if(floorCollision()){
        y--;
      }else if(rWallCollision()){
        x--;
      }else if(lWallCollision()){
        x++;
      }
      
      if((inWallCollision1()) && (faceUp)){
        y++;
      }else if((inWallCollision1()) && (faceLeft)){
        x++;
      }else if((inWallCollision1()) && (faceRight)){
        x--;
      }else if((inWallCollision1()) && (faceDown)){
        y--;
      }
      
      if((ballInWallCollision1())||(ballInWallCollision2())||(ballInWallCollision3())||(ballRoofCollision())||(ballFloorCollision())||(ballLWallCollision())||(ballRWallCollision())){
        moveUp2 = false;
        moveDown2 = false;
        moveLeft2 = false;
        moveRight2 = false;
      }
      
      if((inWallCollision2()) && (faceUp)){
        y++;
      }else if((inWallCollision2()) && (faceLeft)){
        x++;
      }else if((inWallCollision2()) && (faceRight)){
        x--;
      }else if((inWallCollision2()) && (faceDown)){
        y--;
      }
      
      if((inWallCollision3()) && (faceUp)){
        y++;
      }else if((inWallCollision3()) && (faceLeft)){
        x++;
      }else if((inWallCollision3()) && (faceRight)){
        x--;
      }else if((inWallCollision3()) && (faceDown)){
        y--;
      }
      
      if((trapCollision())||(trapCollision2())||(trapCollision3())){
        dead = true;
        try{
        Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        new WinFrame(false, timeLimit, 0);
      }
      
      if(timeLimit == 10000){
        dead = true;
        try{
        Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        new WinFrame(true, (timeLimit-10000), 3);
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
      g.drawImage(timerImage, 651, 100, this);
      //WALLS//////////////////////////////////////////////////
      //border
      g.setColor(Color.BLACK);
      g.fillRect(0,0, 1366, 50);
      g.fillRect(0,718,1368,50);
      g.fillRect(0,0,50,768);
      g.fillRect(1316,0,50,768);
      
      //inner
      g.setColor(Color.BLACK);
      g.fillRect(400,50,20,300);
      g.fillRect(0,300, 300,20);
      g.fillRect(700,250,20,518);
      
      //TRAP////////////////////////////////////////////////////
      g.setColor(Color.RED);
      g.fillOval((int)trapX,(int)trapY,30,30);
      g.setColor(Color.GREEN);
      g.fillOval((int)trapX+5,(int)trapY+5,20,20);
      //1.5
      g.setColor(Color.RED);
      g.fillOval((int)trapX+30,(int)trapY,30,30);
      g.setColor(Color.GREEN);
      g.fillOval((int)trapX+35,(int)trapY+5,20,20);
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
      if(targMoveUp){
        targY--;
      }else if(targMoveDown){
        targY++;
      }else if(targMoveLeft){
        targX--;
      }else if(targMoveRight){
        targX++;
      }
      ////////////////////////////////////////////////////////////
      
      ///dead
      if(dead){
        g.drawImage(image, (int)x-300, (int)y-300, this);
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
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dx--;
        
      } else if (moveRight2){
        moveLeft2 = false;
        moveUp2 = false;
        moveDown2 = false;
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dx++;
        
      }else if (moveUp2){
        moveRight2 = false;
        moveLeft2 = false;
        moveDown2 = false;
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dy--;
        
      }else if(moveDown2){
        moveRight2 = false;
        moveUp2 = false;
        moveLeft2 = false;
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dy++;
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
          x = dx-15;
          y = dy-55;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }else if(moveDown2 == true){
          x = dx-15;
          y = dy-55;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }else if(moveLeft2 == true){
          x = dx-55;
          y = dy-15;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }else if(moveRight2 == true){
          x = dx-55;
          y = dy-15;
          moveUp2 = false;
          moveDown2 = false;
          moveLeft2 = false;
          moveRight2 = false;
        }
      }else if (e.getKeyCode() == KeyEvent.VK_UP) {
        
        
        
      }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        
      }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        
        
      }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        
        
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
      }else if (e.getKeyCode() == KeyEvent.VK_UP) {
        
      }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        
      }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        
      }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        
      }
    }
  } //end of keyboard listener
  
  // -----------  Inner class for the keyboard listener - This detects mouse movement & clicks and runs the corresponding methods 
  private class MyMouseListener implements MouseListener {
    
    public void mouseClicked(MouseEvent e) {
      System.out.println("Mouse Clicked");
      System.out.println("X:"+e.getX() + " y:"+e.getY());
    }
    
    public void mousePressed(MouseEvent e) {
    }
    
    public void mouseReleased(MouseEvent e) {
    }
    
    public void mouseEntered(MouseEvent e) {
    }
    
    public void mouseExited(MouseEvent e) {
    }
  }
  public Rectangle getBoundsPlayer() {
    if((faceUp)||(faceDown)){
      return new Rectangle((int)x-15,(int) y, 80, 100);
    }else{
      return new Rectangle((int)x, (int)y-15, 100, 80);        
    }
  }
  public Rectangle getBoundsBall() {
    return new Rectangle((int)dx,(int) dy, 20, 20);
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
    return new Rectangle(400,50,20,300);
  }
  public Rectangle getBoundsInWall2(){
    return new Rectangle(0, 300, 300,20);
  }
  public Rectangle getBoundsInWall3(){
    return new Rectangle(700,250,20,518);
  }
  
  //collision booleans
  
  public boolean ballCollision(){
    return getBoundsBall().intersects(getBoundsTarget());
  }
  public boolean targCollision(){
    return getBoundsTarget().intersects(getBoundsPlayer());
  }
  public boolean trapCollision(){
    return getBoundsTrap().intersects(getBoundsPlayer());
  }
  public boolean trapCollision2(){
    return getBoundsTrap2().intersects(getBoundsPlayer());
  }
  public boolean trapCollision3(){
    return getBoundsTrap3().intersects(getBoundsPlayer());
  }
  public boolean roofCollision(){
    return getBoundsRoof().intersects(getBoundsPlayer());
  }
  public boolean floorCollision(){
    return getBoundsFloor().intersects(getBoundsPlayer());
  }
  public boolean lWallCollision(){
    return getBoundsLWall().intersects(getBoundsPlayer());
  }
  public boolean rWallCollision(){
    return getBoundsRWall().intersects(getBoundsPlayer());
  }
  public boolean inWallCollision1(){
    return getBoundsInWall1().intersects(getBoundsPlayer());
  }
  public boolean inWallCollision2(){
    return getBoundsInWall2().intersects(getBoundsPlayer());
  }
  public boolean inWallCollision3(){
    return getBoundsInWall3().intersects(getBoundsPlayer());
  }
  public boolean ballInWallCollision1(){
    return getBoundsInWall1().intersects(getBoundsBall());
  }
  public boolean ballInWallCollision2(){
    return getBoundsInWall2().intersects(getBoundsBall());
  }
  public boolean ballInWallCollision3(){
    return getBoundsInWall3().intersects(getBoundsBall());
  }
  public boolean ballRoofCollision(){
    return getBoundsRoof().intersects(getBoundsBall());
  }
  public boolean ballFloorCollision(){
    return getBoundsFloor().intersects(getBoundsBall());
  }
  public boolean ballLWallCollision(){
    return getBoundsLWall().intersects(getBoundsBall());
  }
  public boolean ballRWallCollision(){
    return getBoundsRWall().intersects(getBoundsBall());
  }
  
}


//end of mouselistener
