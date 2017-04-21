package etude06;

import java.awt.*;

public class Square {
  private int generation;
  private int x;
  private int y;
  private int size;
  private Square[] squares;

  public Square(int generation, int x, int y, int size){
    this.generation = generation;
    this.x = x;
    this.y = y;
    this.size = size;
  }

  public void subdivideAndFill(Graphics g, int iterations) {
    if(generation == iterations + 1 || size == 0) return;
    int newSize = size / 3;
    int newGeneration = generation + 1;
    squares = new Square [9];
    for(int i = 0; i < 9; i ++){
      squares[i] = new Square(newGeneration, x + (i % 3) * newSize, y + (i / 3) * newSize, newSize);
      if(i != 4) {
        squares[i].subdivideAndFill(g, iterations);
      } else {
        g.setColor(Color.red);
        g.fillRect(squares[4].x, squares[4].y, squares[4].size, squares[4].size);
      }
    }
  }
}
