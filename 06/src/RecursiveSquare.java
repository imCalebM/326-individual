import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class RecursiveSquare {
  private int generation;
  private int x;
  private int y;
  private int size;
  private RecursiveSquare[] squares;

  public RecursiveSquare(int generation, int x, int y, int size){
    this.generation = generation;
    this.x = x;
    this.y = y;
    this.size = size;
  }

  public void subdivideAndFill(Graphics g, int iterations) {
    if(generation == iterations + 1 || size == 0) return;
    int newSize = size / 3;
    int newGeneration = generation + 1;
    squares = new RecursiveSquare [9];
    for(int i = 0; i < 9; i ++){
      squares[i] = new RecursiveSquare(newGeneration, x + (i % 3) * newSize, y + (i / 3) * newSize, newSize);
      if(i != 4) {
        squares[i].subdivideAndFill(g, iterations);
      } else {
        g.setColor(Color.red);
        g.fillRect(squares[4].x, squares[4].y, squares[4].size, squares[4].size);
      }
    }
  }

  public BufferedImage scaleToScreen(BufferedImage original) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int largerEdge = height < width ? (int) width : (int) height;

    BufferedImage scaledImage = new BufferedImage(largerEdge, largerEdge, BufferedImage.TYPE_BYTE_INDEXED);
    Graphics g = scaledImage.createGraphics();
    g.drawImage(original, 0, 0, largerEdge, largerEdge, null);
    g.dispose();

    return scaledImage;
  }

  public void saveImage(BufferedImage image, String name) {
    try {
      File outputFile = new File(name);
      ImageIO.write(image, "png", outputFile);
      System.out.println("Saved your squares to " + name);
    } catch (IOException e) {
      System.out.println("Failed to save image");
      System.exit(0);
    }
  }

  public static void main(String[] args){
    int iterations = Integer.parseInt(args[0]);
    double size = (int) Math.pow(3, iterations + 1);

    BufferedImage output = new BufferedImage((int) size, (int) size, BufferedImage.TYPE_BYTE_INDEXED);
    Graphics2D g = output.createGraphics();
    g.setColor(Color.white);
    g.fillRect(0, 0, (int)size, (int)size);

    System.out.println("Drawing squares...");

    new RecursiveSquare(0, 0, 0, (int) size).subdivideAndFill(g, iterations);
    
    g.dispose();

    saveImage(scaleToScreen(output), "fractal_squares_" + iterations + ".png");
  }
}
