import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dictionary {

    private List<String>[] hashtable;
    private final int size;

    private int numOfElements;
    private int numOfCollisions;
    private int maxChainLength;
    private int numOfEmptyCells;

    public Dictionary(int size) {
        this.size = size;
        hashtable = new List[size];

        numOfElements = 0;
        numOfCollisions = 0;
        maxChainLength = 0;
        numOfEmptyCells = 0;
    }

    public void readFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String word;

            while ((word = reader.readLine()) != null) {
                if (word.length() != 7) {
                    continue;
                }

                numOfElements++;

                int index = calculateHashValue(word);

                if (hashtable[index] == null) {
                    hashtable[index] = new ArrayList<>();
                } else {
                    numOfCollisions++;
                }

                hashtable[index].add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int calculateHashValue(String word) {
        long hashvalue = 0;
        char[] chars = word.toLowerCase().toCharArray();
        Arrays.sort(chars);

        for (int i = 0; i < chars.length; i++) {
            int characterValue = (int) chars[i] - 97;
            hashvalue += Math.pow(2, characterValue);
        }

        return (int) (hashvalue % size);
    }

    public void calculateStats() {
        for (List<String> list : hashtable) {
            if (list == null)
                numOfEmptyCells++;
            else if (list.size() > maxChainLength)
                maxChainLength = list.size();
        }
    }

    public void printHastable() {
        for (int i = 0; i < hashtable.length; i++) {
            List<String> list = hashtable[i];

            StringBuilder sb = new StringBuilder();

            if (list == null) continue;

            for (String word : list) {
                sb.append(word).append(", ");
            }
            System.out.println(i + ": " + sb);
        }

        calculateStats();
        System.out.println("\nElements: " + numOfElements);
        System.out.println("Collisions: " + numOfCollisions);
        System.out.println("Max Chain Length: " + maxChainLength);
        System.out.println("Empty Cells: " + numOfEmptyCells + "\n");
    }

    public void checkWord(String word) {
        List<String> list = hashtable[calculateHashValue(word)];

        char[] chars = word.toCharArray();
        Arrays.sort(chars);

        for (String listEntry : list) {
            char[] listEntryChars = listEntry.toCharArray();
            Arrays.sort(listEntryChars);

            if (Arrays.equals(chars, listEntryChars)) {
                System.out.println(listEntry);
            }
        }
    }

    public static void main(String[] args) {
        Dictionary dictionary = new Dictionary(3947);
        dictionary.readFile("words.txt");

        dictionary.printHastable();

        dictionary.checkWord("aaghhrr");
    }

}
