import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dictionary {

    private List<String>[] hashtable;
    private final int size;

    public Dictionary(int size) {
        this.size = size;
        hashtable = new List[size];
    }

    public void readFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String word;

            while ((word = reader.readLine()) != null) {
                if (word.length() != 7) {
                    continue;
                }

                int index = calculateHashValue(word);

                if (hashtable[index] == null) {
                    hashtable[index] = new ArrayList<>();
                }
                hashtable[index].add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private int calculateHashValue(String word) {
        long hashvalue = 0;
        char[] chars = word.toCharArray();
        Arrays.sort(chars);

        for (int i = 0; i < chars.length; i++) {
            int characterValue = (int) chars[i] - 97;
            hashvalue += Math.pow(2, characterValue);
        }

        return (int) (hashvalue % size);
    }

    public List<String>[] getHashtable() {
        return hashtable;
    }

    public void printHastable() {
        for (int i = 0; i < getHashtable().length; i++) {
            List<String> list = getHashtable()[i];

            StringBuilder sb = new StringBuilder();

            if (list == null) continue;

            for (String word : list) {
                sb.append(word).append(", ");
            }
            System.out.println(i + ": " + sb);
        }
    }

    public void checkWord(String word) {
        List<String> list = getHashtable()[calculateHashValue(word)];

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
        Dictionary hashtable = new Dictionary(3947);
        hashtable.readFile("words.txt");

        hashtable.printHastable();

        hashtable.checkWord("aaghhrr");
    }

}
