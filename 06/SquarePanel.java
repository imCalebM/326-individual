package etude06;
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class SquarePanel extends JPanel {

  Color color = Color.red;
  double smallerEdge = height > width ? width : height;
  Square currentSquare;
  int iterations;
  double size;

}
