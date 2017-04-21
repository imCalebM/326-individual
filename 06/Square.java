package etude06;

import java.awt.*;

public class Square {
  public int generation;
  private int x;
  private int y;
  private Color shade;
  private int size;
  private Square[] squares;

  public Square(int generation, Color shade, int x, int y, int size){
    this.generation = generation;
    this.x = x;
    this.y = y;
    this.size = size;
    this.shade = shade;
  }

  public void subdivide(Graphics g, int iterations) {
    if(generation == iterations + 1) return;
    int newSize = size / 3;
    System.out.println("generation " + generation);
    System.out.println("size " + size);
    int newGeneration = generation + 1;
    squares = new Square [9];
    for(int i = 0; i < 9; i ++){
      squares[i] = new Square(newGeneration, shade, x + (i % 3) * newSize, y + (i / 3) * newSize, newSize);
      if(i != 4) {
        squares[i].subdivide(g, iterations);
      } else {
        g.setColor(shade);
        g.fillRect(squares[4].x, squares[4].y, squares[4].size, squares[4].size);
      }
    }
  }

}
