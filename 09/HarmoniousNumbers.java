/**
 * COSC 326 Etude 9
 * Harmonious Numbers
 *
 * HarmoniousNumbers.java
 *
 * @author Rhianne Price
 * May 2017
 */

import java.util.*;

/**
 * Finds and prints all pairs of "harmonious numbers" less than 2000000.
 */
public class HarmoniousNumbers {

  /* Keep track of all the pairs to avoid duplicates. */
  private static HashMap<Integer, Integer> pairs = new HashMap<Integer, Integer>();

  /* Upper bound for the problem. */
  private static final int MAX = 2000000;

  /**
   * Finds the sum of all divisors of a given number except for 1 and itself.
   * @param candidate the number to find the sum of the divisors for.
   * @return the sum of all divisors except 1 and the number.
   */
  private static int sumOfProperDivisors(int candidate){
      int maxDivisor = (int) Math.sqrt(candidate);
      int sum = 0;
      for(int d = 2; d <= maxDivisor; d++) {
          if(candidate % d == 0) {
              /* Only add a square divisor once. */
              int otherDivisor = candidate / d;
              if(otherDivisor != d) sum += otherDivisor;
              sum += d;
          }
      }
      return sum;
  }

  /**
   * Entry point of the program. Tries all integers between 2 and the upper
   * limit, and checks if it is part of a harmonious pair, and prints the pair
   * if it hasn't already been printed.
   * @param args command line arguments not used.
   */
  public static void main(String[] args) {
    for(int i = 2; i < MAX; i++) {
      /* For a harmonious pair, use sum of divisors(p1) = p2 and vice versa */
      int pair1 = sumOfProperDivisors(i);
      int pair2 = sumOfProperDivisors(pair1);

      if(pairs.get(pair1) == null) {
        if(pair1 != i && i == pair2) {
          if(pair1 < pair2){
            pairs.put(pair1, pair2);
            System.out.println(pair1 + " " + pair2);
          } else {
            pairs.put(pair2, pair1);
            System.out.println(pair2 + " " + pair1);
          }
        }
      }
    }
  }
}
