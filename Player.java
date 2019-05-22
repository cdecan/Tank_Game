public class Player extends Object{
  boolean movingUp, movingDown,movingLeft,movingRight, up,down,left,right;
  int health;
  boolean dead;
  public int bullets;
  
  public Player(int x, int y, int width, int height){
    super(x,y,width,height);
    this.health = 3;
    this.dead = false;
    this.up = true;
  }
  private void move(){
    if(this.movingUp){
      this.up = true;
      this.down = false;
      this.left = false;
      this.right = false;
      this.y-=2;
    }else if(this.movingDown){
            this.up = false;
      this.down = true;
      this.left = false;
      this.right = false;
      this.y+=2;
      
    }else if(this.movingLeft){
            this.up = false;
      this.down = false;
      this.left = true;
      this.right = false;
      this.x-=2;
      
    }else if(this.movingRight){
            this.up = false;
      this.down = false;
      this.left = false;
      this.right = true;
      this.x+=2;
    }
      
    
    
}
}