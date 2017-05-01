/**
 * COSC 326
 * Etude 6
 * Fractal Squares
 *
 * RecursiveSquare.java
 *
 * @author Rhianne Price
 */
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

/**
 * A recursive representation of a square. A square is made up of 9 smaller
 * squares, can fill itself, and scale and save images.
 */
public class RecursiveSquare {
  private int generation;
  private int x;
  private int y;
  private int size;
  private RecursiveSquare[] squares;

  /**
   * Constructor sets the position of the top left corner of the square and
   * the size.
   * @param x x position of the top left corner.
   * @param y y position of the top left corner.
   * @param size width/height of the square in pixels.
   */
  public RecursiveSquare(int generation, int x, int y, int size){
    this.generation = generation;
    this.x = x;
    this.y = y;
    this.size = size;
  }

  /**
   * Splits this square into 9 uniform squares, and fills the one in the centre.
   * @param g graphics object to draw to.
   */
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

  /**
   * Creates a square buffered image the same size as the smaller dimension of
   * the users display and draws the orignal image to it.
   * @param original buffered image to be scaled.
   * @return the scaled image.
   */
  public static BufferedImage scaleToScreen(BufferedImage original) {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int smallerEdge = height > width ? (int) width : (int) height;

    BufferedImage scaledImage = new BufferedImage(smallerEdge, smallerEdge, BufferedImage.TYPE_BYTE_INDEXED);
    Graphics g = scaledImage.createGraphics();
    g.drawImage(original, 0, 0, smallerEdge, smallerEdge, null);
    g.dispose();

    return scaledImage;
  }

  /**
   * Saves a buffered image as a PNG.
   * @param image buffered image to be saved.
   * @param name filename to write the file to.
   */
  public static void saveImage(BufferedImage image, String name) {
    try {
      File outputFile = new File(name);
      ImageIO.write(image, "png", outputFile);
      System.out.println("Saved your squares to " + name);
    } catch (IOException e) {
      System.out.println("Failed to save image");
      System.exit(0);
    }
  }

  /**
   * Creates a new square the size of the most squares that will fill a row
   * depending on the number of generations to be drawn, and a new buffered
   * image of the same dimensions. The square then recursively splits itself
   * into smaller squares and fills the corrosponding areas on the buffered
   * image. The final image is then scaled and saved.
   *
   * @param args the first aragument specifies the number of generations of
   *             squares to draw.
   */
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
