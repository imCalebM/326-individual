/* Rectangle.java
 * Lab 20, COMP160,  2014
 * 
 * graphical representation of a rectangle
 * with a fill, colour, x, y, width, height
 */

import java.awt.*;

public class Rectangle{
  private int x;  // x location;
  private int y;  // y location;
  private Color shade;  // colour of Rectangle
  private int width; //width of Rectangle
  private int height;//height of Rectangle
  private boolean fill; //drawRect false or fillRect true
  
  private static int totalCount;
  private int thisCount;
  
  /*constructor for Rectangle*/
  public Rectangle(boolean fill, Color shade,int x, int y , int width, int height){
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.shade = shade;
    this.fill=fill;
    
    totalCount+=1;
    thisCount = totalCount;
  }
  
 /*draw this rectangle*/
    public void draw(Graphics g){
        g.setColor(shade);
        if (fill ){
            g.fillRect(x, y, width, height);
        } else  {
            g.drawRect(x, y, width, height);
        }
        g.drawString(thisCount + " of " + totalCount, x, y);
    }
    
}