package etude06;

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

public class FractalApp{
  public static void main(String[] args){
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    double width = screenSize.getWidth();
    double height = screenSize.getHeight();
    int largerEdge = height < width ? (int) width : (int) height;

    int iterations = Integer.parseInt(args[0]);
    double size = (int) Math.pow(3, iterations + 1);

    BufferedImage output = new BufferedImage((int) size, (int) size, BufferedImage.TYPE_BYTE_INDEXED);
    Graphics2D g = output.createGraphics();
    g.setColor(Color.white);
    System.out.println("Drawing squares...");
    g.fillRect(0, 0, (int)size, (int)size);
    new Square(0, 0, 0, (int) size).subdivideAndFill(g, iterations);
    g.dispose();

    /* Scale image and write to file. */
    try {
      BufferedImage newImage = new BufferedImage(largerEdge, largerEdge, BufferedImage.TYPE_BYTE_INDEXED);
      Graphics g2 = newImage.createGraphics();
      g2.drawImage(output, 0, 0, largerEdge, largerEdge, null);
      g2.dispose();
      File outputFile = new File("fractal_squares_" + iterations + ".png");
      ImageIO.write(newImage, "png", outputFile);
      System.out.println("Saved your squares to fractal_squares_" + iterations + ".png");
    } catch (IOException e) {
        System.out.println("failed to save image");
        System.exit(0);
    }
  }
}
