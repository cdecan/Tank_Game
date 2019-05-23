/**
 * This template can be used as reference or a starting point
 * for your final summative project
 * @author Mangat
 **/

//Graphics &GUI imports
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
  
  Square square;
  boolean moveLeft;
  boolean moveRight;
  boolean moveUp;
  boolean moveDown;
  boolean moveUp2, moveDown2, moveLeft2, moveRight2;
  
  
  
  //Constructor - this runs first
  GameFrame() { 
    
    super("My Game");  
    // Set the frame to full screen 
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(1366, 768);
    // this.setUndecorated(true);  //Set to true to remove title bar
    //frame.setResizable(false);
    
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
    
    //Set up the game panel (where we put our graphics)
    square = new Square();
    
    //JPanel panel = new JPanel();
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
    this.dx = (200);
    this.dy = (300);
    while(true){
      
      
      try{ Thread.sleep(1);} catch (Exception exc){}  //delay
      this.repaint();
      
    }    
  }
  
  /** --------- INNER CLASSES ------------- **/
  
  // Inner class for the the game area - This is where all the drawing of the screen occurs
  
  private class Square extends JPanel{
    //, Graphics g2\
   //      g.fillRect((int)dx, (int)dy, 50, 50);
    public void paintComponent(Graphics g) {   
      super.paintComponent(g); //required
      //super.paintComponent(g2);
      setDoubleBuffered(true); 
      g.setColor(Color.RED); //There are many graphics commands that Java can use
      g.fillRect((int)x, (int)y, 60, 60); //notice the x,y variables that we control from our animate method
      g.fillRect((int)x,(int)y+23, 100, 12);
      if (moveLeft == true){
        x--;
      } else if (moveRight == true){
        x++;
      }else if (moveUp){
        y--;
      }else if(moveDown){
        y++;
      }
   //   g2.setColor(Color.BLUE); //There are many graphics commands that Java can use
     // g2.fillRect((int)dx, (int)dy, 50, 50); //notice the x,y variables that we control from our animate method      
      

    }
    public void paint(Graphics g) {
      super.paint(g);
    g.setColor(Color.BLUE);
    g.fillRect((int)dx,(int)dy,50,50);
          if (moveLeft2 == true){
        dx-=2;
      } else if (moveRight2 == true){
        dx+=2;
      }else if (moveUp2){
        dy-=2;
      }else if(moveDown2){
        dy+=2;
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
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
        moveDown = true;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
        moveLeft = true;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
        for(int i = 100; i>1; i--){
          moveUp = true;
          
        }
      }else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {  //If ESC is pressed
        //System.out.println("ESCAPE KEY!"); //close frame & quit
        System.exit(0);
      }else if(KeyEvent.getKeyText(e.getKeyCode()).equals("F")){
        // this.remove();
      }else if (e.getKeyCode() == KeyEvent.VK_UP) {
        moveUp2 = true;
      }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        moveDown2 = true;
      }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        moveLeft2 = true;
      }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        moveRight2 = true;
      }
    }   
    
    public void keyReleased(KeyEvent e) {
      if (KeyEvent.getKeyText(e.getKeyCode()).equals("A")) {
        moveLeft = false;
      } else if (KeyEvent.getKeyText(e.getKeyCode()).equals("D")) {
        moveRight = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("S")) {
        moveDown = false;
      }else if (KeyEvent.getKeyText(e.getKeyCode()).equals("W")) {
        moveUp = false;
      }else if (e.getKeyCode() == KeyEvent.VK_UP) {
        moveUp2 = false;
      }else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
        moveDown2 = false;
      }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
        moveLeft2 = false;
      }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        moveRight2 = false;
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
