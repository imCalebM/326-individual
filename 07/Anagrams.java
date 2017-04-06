package etude07;

import java.util.*;

public class Anagrams {
    private static ArrayList<String> dictionary = new ArrayList<String>();
    private static HashMap<Integer, ArrayList<String>> wordsWeCanMake = new HashMap<Integer, ArrayList<String>>();

    public static void main(String[] args) {
        String sentenceToRearrange = args[0];
        sentenceToRearrange = sentenceToRearrange.replaceAll("[^a-zA-Z0-9]", "");
        String bag = sentenceToRearrange.toLowerCase();
        int bagSize = sentenceToRearrange.length();

        dictionary.add("hell");
        dictionary.add("ho");

        findAllSubsetsOf(bag);

        for(Map.Entry<Integer, ArrayList<String>> entry : wordsWeCanMake.entrySet()) {
            for(String word : entry.getValue()) {
                System.out.println(word);
            }
        }
    }

    public static void findAllSubsetsOf(String bag) {
        double possibleCombos = Math.pow(2, bag.length());
        for(int i = 0; i < possibleCombos; i++) {
            String word = "";
            for(int bit = 0; bit < bag.length(); bit++) {
               if(((i >> bit) & 1) == 1) word += bag.charAt(bit);
           }
           permutation("", word);
        }
    }

    private static void findAll(String prefix, String str) {
      int n = str.length();
      if (n == 0) {
          if(dictionary.contains(prefix)){
              if(wordsWeCanMake.get(prefix.length()) == null) {
                  wordsWeCanMake.put(new Integer(prefix.length()), new ArrayList<String>());
              }
          wordsWeCanMake.get(prefix.length()).add(prefix);
          }
      } else {
        for (int i = 0; i < n; i++) {
            permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n));
        }
      }
    }

    private static void addWord(String word) {
    }
}
