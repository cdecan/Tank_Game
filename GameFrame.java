/**
 * 
 * @author C&J
 **/

//Graphics & GUI imports
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Toolkit;
import java.awt.Graphics;
import java.awt.Color;

//Keyboard imports
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Mouse imports
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

class GameFrame extends JFrame { 
  
  //class variable (non-static)
  static double x, y;
  static double dx, dy;
  
  boolean moveLeft;
  boolean moveRight;
  boolean moveUp;
  boolean moveDown;
  boolean faceUp = true, faceDown = false, faceLeft = false, faceRight = false;
  boolean moveUp2, moveDown2, moveLeft2, moveRight2;
  int timeLimit = 0;
  Square square = new Square();
  Tank tank = new Tank();
  boolean ballExists = false;
  
  
  //Constructor - this runs first
  GameFrame() { 
    
    super("My Game");  
    this.setSize(1366, 768);
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(Toolkit.getDefaultToolkit().getScreenSize());
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);
    
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
    
    
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
    this.dx = (x+15);
    this.dy = (y);
    ballExists = false;
    faceUp = true;
    // int timeLimit = 0;
    while(true){
      //    timeLimit++;
      //  JLabel label = new JLabel(Integer.toString(timeLimit));
      //  square.add(label);
      while(ballExists = false){
        dx = x+15;
        dy = y;
        
      }
      try{ Thread.sleep(1);} catch (Exception exc){}  //delay
      //  square.remove(label);
      this.repaint();
      //square.repaint();
      
      
      //    if(timeLimit == 10000){
      //    dispose();
      // }
      
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
      if(faceUp){
        g.setColor(Color.BLACK);
        g.fillOval((int)x,(int)y,50,100);
        g.setColor(Color.RED);
        g.fillOval((int)x+5,(int)y+5,40,90);
        g.setColor(Color.BLACK);
        g.fillRect((int)x+22,(int)y-25,5,29);
      }else if(faceDown){
        g.setColor(Color.BLACK);
        g.fillOval((int)x,(int)y,50,100);
        g.setColor(Color.RED);
        g.fillOval((int)x+5,(int)y+5,40,90);
        g.setColor(Color.BLACK);
        g.fillRect((int)x+22,(int)y+100,5,29);        
        
      }else if(faceLeft){
        g.setColor(Color.BLACK);
        g.fillOval((int)x,(int)y,100,50);
        g.setColor(Color.RED);
        g.fillOval((int)x+5,(int)y+5,90,40);
        g.setColor(Color.BLACK);
        g.fillRect((int)x-24,(int)y+20,29,5);
        
      }else if(faceRight){
        g.setColor(Color.BLACK);
        g.fillOval((int)x,(int)y,100,50);
        g.setColor(Color.RED);
        g.fillOval((int)x+5,(int)y+5,90,40);
        g.setColor(Color.BLACK);
        g.fillRect((int)x+100,(int)y+22,29,5);
      }
      
      
      if (moveLeft == true){
        x--;
      } else if (moveRight == true){
        x++;
      }else if (moveUp){
        y--;
      }else if(moveDown){
        y++;
      }
      
      if ((moveLeft2) ){
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dx--;
        
      } else if ((moveRight2)){ //&& (ballExists = false)
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dx++;
        
      }else if ((moveUp2)){
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dy--;
        
      }else if((moveDown2)){
        g.setColor(Color.BLACK);
        g.fillOval((int)dx,(int)dy,20,20);
        dy++;
        
      }
      
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
        faceRight = true;
        faceLeft= false;
        faceUp = false;
        faceDown = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
        moveDown = true;
        faceDown = true;
        faceLeft= false;
        faceUp = false;
        faceRight = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
        moveLeft = true;
        faceLeft = true;
        faceRight= false;
        faceUp = false;
        faceDown = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
        //for(int i = 100; i>1; i--){
        moveUp = true;
        faceRight = false;
        faceLeft= false;
        faceUp = true;
        faceDown = false;
        //}
      }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        //System.out.println("ESCAPE KEY!"); //close frame & quit
        System.exit(0);
      }else if(KeyEvent.getKeyText(e.getKeyCode()).equals("F")){
        // this.remove();
      }else if (e.getKeyCode() == KeyEvent.VK_UP) {
        //    if(ballExists = false){
        moveUp2 = true;
        ballExists = true;
        //  }
        
      }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        //   if(ballExists = false){
        moveDown2 = true;
        ballExists = true;
        //  }
      }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        //if(ballExists = false){
        moveLeft2 = true;
        ballExists = true;
        //}
        
      }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        //  if(ballExists = false){
        moveRight2 = true;
        ballExists = true;
        //}
        
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
        // moveUp2 = false;
        // dy = (y);
        // dx = x+15;
      }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        // moveDown2 = false;
        // dy = y;
        // dx = x+15;
      }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        //moveLeft2 = false;
        //dx = x+15;
        //dy = y;
      }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        //moveRight2 = false;
        //dx = x+15;
        //dy = y;
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
  } //end of mouselistener
}
