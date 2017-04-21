package etude06;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class SquarePanel extends JPanel {
  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
  double width = screenSize.getWidth();
  double height = screenSize.getHeight();

  Color color = Color.red;
  double smallerEdge = height > width ? width : height;
  Square currentSquare;
  int iterations;
  double size;

  public SquarePanel(int iterations) {
    this.iterations = iterations;
    size = Math.pow(3, iterations);
    setPreferredSize(new Dimension((int)size, (int)size));
    setBackground(Color.yellow);
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);

    currentSquare = new Square(0, color, 0, 0, (int)size);
    currentSquare.subdivide(g, iterations);
  }
}
