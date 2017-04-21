/** FilePanel.java
  * Lab 20, COMP160,  2014
  * 
  * a JPanel which creates 2 instances of Rectangle objects, 
  * stores them in an array, and draws them 
  */
import java.io.*;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class FilePanel extends JPanel{
  private Rectangle[] drawObjects = new Rectangle [10];
  private int count;
  
  
  /**constructor instantiates 6 Rectangle objects*/
  public FilePanel(){
    
    String fileName = "LongBadData.txt"; // data field declarations
    boolean fillb = true;
    Color color = Color.red;
    int fill [] = new int [6]; 
    
    try{
      Scanner fileScan = new Scanner(new File(fileName));
      
      while (fileScan.hasNextLine() && count < 10){
        
        String inputLine = fileScan.nextLine();
        int i = 0;
        
        if (inputLine.matches("\\d+ \\d+ \\d+ \\d+ \\d+ \\d+")){
          Scanner line = new Scanner(inputLine);
          while (i < 6){
            fill[i] = line.nextInt();
            i++;
          }
        }
        
        // loop sets whether to fill rect or not depending on the input read from the file
        if (fill[0] == 1){
          fillb = true;
        } else {
          fillb = false;
        } // end else
        
        // loop sets color depending on the input read from the file
        if (fill[1] == 1){
          color = Color.red;
        } else if (fill[1] == 2){
          color = Color.blue;
        } else {
          color = Color.green;
        }  // end else
        
        drawObjects[count] = new Rectangle(fillb, color, fill[2], fill[3], fill[4], fill[5]); // creates rectangle object
        count++;
        
      } // end while
      
    } catch (FileNotFoundException e){
      System.out.println("File not found. Check file name and location.");
      System.exit(1);
    } // end catch
    
    
    
    setPreferredSize(new Dimension(300,300));
    setBackground(Color.yellow);
  }
  
  /**each Rectangle will draw itself*/
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    for(int i = 0; i < count; i++){
      drawObjects[i].draw(g);
    } 
  }
}