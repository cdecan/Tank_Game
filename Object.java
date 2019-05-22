import java.awt.Rectangle;

class Object{
  int x, y, width, height;
  public Object(int x, int y, int width, int height)
  {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
  public int getX(){
    return this.x;
  }
  public int getY(){
    return this.y;
  }
  public int getHeight(){
    return this.height;
  }
  public int getWidth(){
    return this.width;
  }
  public Rectangle getHitBox() {
    return new Rectangle(this.x, this.y, this.width, this.height);
  }
}//general object