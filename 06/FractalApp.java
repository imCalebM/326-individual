package etude06;

import javax.swing.*;
import java.awt.BorderLayout;

public class FractalApp{
  public static void main(String[] args){
    JFrame mainFrame = new JFrame();
    mainFrame.setLayout(new BorderLayout());
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.add(new SquarePanel(Integer.parseInt(args[0])), BorderLayout.CENTER);
    mainFrame.pack();
    mainFrame.setVisible(true);
    mainFrame.setTitle("Fractal Squares");
  }
}
