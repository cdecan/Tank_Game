/**
 * GameFrameLevelVS.java
 * A program to launch a VS mode screen
 * Jayden and Connor
 * May 30, 2019
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
import javax.sound.sampled.Clip;

class GameFrameLevelVS extends JFrame { 
  
  //class variable
  static double x, y;
  static double ballX, ballY;
  static double targX, targY;
  static double trapX, trapY, trapX2, trapY2, trapX3, trapY3;
  
  boolean dead = false;
  boolean targDead = false;
  
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
  BufferedImage ballDead;

  Clip clip = StartingFrame.music(3);
  /**
  * Constructor - this runs first
  */
  GameFrameLevelVS() { 
    
    super("My Game");
    
    this.setSize(1366, 768);
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    this.setUndecorated(true);  //Set to true to remove title bar
    this.setResizable(false);
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
      ballDead = ImageIO.read(new File("Images/pop.png"));
    } catch (IOException ex) {
      System.out.println("Error: Image(s) not found");
    }//catch
    
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
  /**
  * the main gameloop - this is where the game state is updated
  */
  public void animate() { 
    this.x = (100);  //update coords
    this.y = (100);
    this.ballX = x;
    this.ballY = y;
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
          System.out.println("Error: Image(s) not found");
        }
      }//if a second passes
      
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
      }//ball movement
      try{ Thread.sleep(1);} catch (Exception exc){}  //delay
      //  square.remove(label);
      this.repaint();
      
      //square.repaint();
      
      
      if(collision(getBoundsPlayer(),getBoundsTarget())){
        dead = true;
        try{
          Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        clip.close();
        new WinFrame(false, timeLimit, 0);
      }//if target hits player
      

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
      }//target movement      
      if(collision(getBoundsPlayer(),getBoundsRoof())){
        y++;
      }else if(collision(getBoundsPlayer(),getBoundsFloor())){
        y--;
      }else if(collision(getBoundsPlayer(),getBoundsRWall())){
        x--;
      }else if(collision(getBoundsPlayer(),getBoundsLWall())){
        x++;
      }//collision player and outer walls
      
      if(collision(getBoundsPlayer(),getBoundsInWall1()) && (faceUp)){
        y++;
      }else if(collision(getBoundsPlayer(),getBoundsInWall1()) && (faceLeft)){
        x++;
      }else if(collision(getBoundsPlayer(),getBoundsInWall1()) && (faceRight)){
        x--;
      }else if(collision(getBoundsPlayer(),getBoundsInWall1()) && (faceDown)){
        y--;
      }//collision player and inner wall
      
      if((collision(getBoundsBall(),getBoundsInWall1()))||(collision(getBoundsBall(),getBoundsInWall2()))||(collision(getBoundsBall(),getBoundsInWall3()))||(collision(getBoundsBall(),getBoundsRoof()))||(collision(getBoundsBall(),getBoundsFloor()))||(collision(getBoundsBall(),getBoundsLWall()))||(collision(getBoundsBall(),getBoundsRWall()))){
        moveUp2 = false;
        moveDown2 = false;
        moveLeft2 = false;
        moveRight2 = false;
      }//collision ball and inner wall
      
      if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall2())) && (faceDown)){
        y--;
      }//collision player and inner wall
      
      if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceUp)){
        y++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceLeft)){
        x++;
      }else if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceRight)){
        x--;
      }else if((collision(getBoundsPlayer(),getBoundsInWall3())) && (faceDown)){
        y--;
      }//collision player and inner wall
      
      if((collision(getBoundsPlayer(),getBoundsTrap()))||(collision(getBoundsPlayer(),getBoundsTrap2()))||(collision(getBoundsPlayer(),getBoundsTrap3()))){
        dead = true;
        try{
          Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        new WinFrame(false, timeLimit, 0);
      }//collision player and trap
      
      if(timeLimit == 10000){
        targDead = true;
        try{
          Thread.sleep(1000);
        }catch(java.lang.InterruptedException e){}
        dispose();
        run = false;
        new WinFrame(true, (timeLimit-10000), 3);
      }//time up
      
    }//while the game runs    
  }//animate
  
  /** --------- INNER CLASSES ------------- **/
  
  
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  
  private class Square extends JPanel{
    Tank tank = new Tank();
    /**
     * the constructor for the custom jpanel that has the game on it
     * */
    Square(){
      
      add(tank);
    }//constructor
  }//square
  
  
  
  
  
  private class Tank extends JComponent{
    //double x,y;
    // boolean moveLeft, moveRight,moveUp,moveDown;
    /**
     * the constructor for the main jcomponent of the game
     * */
    Tank(){
      //   x = 100;
      //   y = 100;
    }//constructor
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
      }else if(targDead){
        g.drawImage(ballDead,(int)targX-300,(int)targY-300,this);
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
      
      
      
    }//paintcomponent
    
    
  }//class
  
  
  // -----------  Inner class for the keyboard listener - this detects key presses and runs the corresponding code
  private class MyKeyListener implements KeyListener {
    
    /**
     * a method to check what to do when a key is typed
     * */
    public void keyTyped(KeyEvent e) {  
    }
    /**
     * a method to check what to do when a key is pressed
     * */
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
      }else if((KeyEvent.getKeyText(e.getKeyCode()).equals("J"))&&(moveUp2 == false) && (moveDown2 == false)&&(moveLeft2 == false)&&(moveRight2 ==false)){
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
      }else if((KeyEvent.getKeyText(e.getKeyCode()).equals("K"))&&((moveUp2)||(moveDown2)||(moveLeft2)||(moveRight2))){
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
    /**
     * a method to check what to do when a key is released
     * */
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
/**
   * a method to create a hitbox for the player
   * @return the rectangle enclosing the player
   * */
  public Rectangle getBoundsPlayer() {
    if((faceUp)||(faceDown)){
      return new Rectangle((int)x-15,(int) y, 80, 100);
    }else{
      return new Rectangle((int)x, (int)y-15, 100, 80);        
    }
  }
  /**
   * a method to create a hitbox for the ball
   * @return the rectangle enclosing the ball
   * */
  public Rectangle getBoundsBall() {
    return new Rectangle((int)ballX,(int) ballY, 20, 20);
  }
  /**
   * a method to create a hitbox for the target
   * @return the rectangle enclosing the target
   * */
  public Rectangle getBoundsTarget() {
    return new Rectangle((int)targX,(int) targY, 60, 60);
  }
  /**
   * a method to create a hitbox for the first trap
   * @return the rectangle enclosing the first trap
   * */
  public Rectangle getBoundsTrap() {
    return new Rectangle((int)trapX, (int) trapY, 30, 30);
  }
  /**
   * a method to create a hitbox for the second trap
   * @return the rectangle enclosing the second trap
   * */
  public Rectangle getBoundsTrap2() {
    return new Rectangle((int)trapX2, (int) trapY2, 30, 30);
  }
  /**
   * a method to create a hitbox for the third trap
   * @return the rectangle enclosing the third trap
   * */
  public Rectangle getBoundsTrap3() {
    return new Rectangle((int)trapX3, (int) trapY3, 30, 30);
  }
  /**
   * a method to create a hitbox for the top border wall
   * @return the rectangle enclosing the top border wall
   * */
  public Rectangle getBoundsRoof(){
    return new Rectangle(0, 0, 1366, 50);
  }
  /**
   * a method to create a hitbox for the bottom border wall
   * @return the rectangle enclosing the bottom border wall
   * */
  public Rectangle getBoundsFloor(){
    return new Rectangle(0,718, 1366, 50);
  }
  /**
   * a method to create a hitbox for the right border wall
   * @return the rectangle enclosing the right border wall
   * */
  public Rectangle getBoundsRWall(){
    return new Rectangle(1316, 0, 50, 768);
  }
  /**
   * a method to create a hitbox for the left border wall
   * @return the rectangle enclosing the left border wall
   * */
  public Rectangle getBoundsLWall(){
    return new Rectangle(0, 0, 50,768);
  }
  /**
   * a method to create a hitbox for the first inner wall
   * @return the rectangle enclosing the first inner wall
   * */
  public Rectangle getBoundsInWall1(){
    return new Rectangle(400,50,20,300);
  }
  /**
   * a method to create a hitbox for the second inner wall
   * @return the rectangle enclosing the second inner wall
   * */
  public Rectangle getBoundsInWall2(){
    return new Rectangle(0, 300, 300,20);
  }
  /**
   * a method to create a hitbox for the third inner wall
   * @return the rectangle enclosing the third inner wall
   * */
  public Rectangle getBoundsInWall3(){
    return new Rectangle(700,250,20,518);
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
  
}//class
