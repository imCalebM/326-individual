/**
 * COSC 326
 * Etude 6
 * Fractal Squares
 *
 * IterativeSquare.java
 *
 * @author Rhianne Price
 */

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.*;

/**
 * A representation of a square with functionality to divide itself square into
 * smaller squares, fill itself, and scale and save images.
 */
public class IterativeSquare {
  private int generation;
  private int x;
  private int y;
  private int size;

  /**
   * Constructor sets the position of the top left corner of the square and
   * the size.
   * @param x x position of the top left corner.
   * @param y y position of the top left corner.
   * @param size width/height of the square in pixels.
   */
  public IterativeSquare(int x, int y, int size){
    this.x = x;
    this.y = y;
    this.size = size;
  }

  /**
   * Splits this square into 9 uniform squares, and fills the one in the centre.
   * @param g graphics object to draw to.
   * @return an array of 9 smaller squares.
   */
  public ArrayList<IterativeSquare> subdivideAndFill(Graphics g){
    int newSize = size / 3;
    ArrayList<IterativeSquare> squares; squares = new ArrayList<IterativeSquare>();
    for(int i = 0; i < 9; i ++){
      squares.add(new IterativeSquare(x + (i % 3) * newSize, y + (i / 3) * newSize, newSize));
    }
    g.setColor(Color.red);
    IterativeSquare middle = squares.get(4);
    g.fillRect(middle.x, middle.y, middle.size, middle.size);
    squares.remove(4);
    return squares;
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
   * Creates a new square the size of the most squares that will fill a row,
   * and a new buffered image of the same dimensions. It uses a queue to
   * iteratively split the square into smaller squares, and fills the
   * corrosponding area on the buffered image. The final image is then scaled
   * and saved.
   *
   * @param args the first aragument specifies the number of generations of
   *             squares to draw.
   */
  public static void main(String[] args){
    int iterations = Integer.parseInt(args[0]);
    double size = (int) Math.pow(3, iterations + 1);

    /* Create image and paint it white. */
    BufferedImage output = new BufferedImage((int) size, (int) size, BufferedImage.TYPE_BYTE_INDEXED);
    Graphics2D g = output.createGraphics();
    g.setColor(Color.white);
    g.fillRect(0, 0, (int)size, (int)size);

    System.out.println("Drawing squares...");

    ConcurrentLinkedQueue<IterativeSquare> q = new ConcurrentLinkedQueue<IterativeSquare>();
    q.add(new IterativeSquare(0, 0, (int) size));

    /* Subdivide and fill each square until the size of the squares gets down
       to one pixel. */
    while(!q.isEmpty()) {
      IterativeSquare currentSquare = q.poll();
      if(currentSquare.size > 1) {
          q.addAll(currentSquare.subdivideAndFill(g));
      }
    }
    g.dispose();

    saveImage(scaleToScreen(output), "fractal_squares_" + iterations + ".png");
  }
}
