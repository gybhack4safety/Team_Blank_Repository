import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * Receives data from JSON file through requests,
 * Input: is the message that will be parsed
 * Severity: indicates the warning given to the user
 */
public class StringParser {

    private int severity;
    private Scanner s;
    // Used to identify the most commonly derogatory or negative words
    private HashMap<String, Integer> frequencyMap;
    FileReader fr;

    // Initializes the parser and converts the word to alphabetical characters
    public StringParser(String in) {
        File fr = new File(in);
        severity = 0;
        try {
            s = new Scanner(fr);
        } catch (FileNotFoundException f) {
            System.out.println("File not found: " + in);
        }
        frequencyMap = new HashMap<>();
    }

    /**
     * Parses each line from the input individually
     * For each word in the sentence, send a request to JSON file for keyword; Returns a Keyword object which contains: Severity, Frequency, and Keyword
     * @return int Level of severity for the given phrase
     */
    public void parseLine() {
        String input = s.nextLine();
        String sentence = input.toLowerCase();
        String regex = "[^A-Za-z\\s]+";
        // Change all the characters in the string into spaces and alphabetical characters
        sentence = sentence.replaceAll(regex, "");
        // Local severity level
        int levSeverity = 0;
        int i = 0;
        int curr = 0;
        // Calculate the number of words within the sentence
        int end = sentence.length() - sentence.replaceAll(" ", "").length();
        // Loop through the given sentence
        while (i < end + 1) {
            String word;
            if (end == i) {
                word = sentence.substring(curr);
            } else {
                int index = sentence.indexOf(" ", curr);
                word = sentence.substring(curr, index);
                curr = index + 1;
            }
            /* Create JSON request to retrieve severity from JSON Object */
            levSeverity += 0; /* --REPLACE-- JSON object severity */
            frequencyMap.put(word, 0/* Letter frequency */);
            i++;
        }
        // Increment the overall severity by the calculated sentence severity
        severity += levSeverity;
    }

    /**
     * Runs parseLine until the file has no more lines
     */
    public void parseBody() {
        while (s.hasNext()) {
            parseLine();
        }
    }

    /**
     * Allows the admin to add a word to the JSON file
     * @param word Word being inserted into the JSON file
     * @return Boolean to determine if the word was added (word doesn't exist in the JSON file already)
     */
    public boolean adminAddKeyWord(String word) {
        /* --Add the word into the JSON file-- */
        /* --Return value of the success/failure of addition-- */
        return false;
    }

    /**
     * Allows the admin to remove a word within the JSON file
     * @param word Word being removed into the JSON file
     * @return Boolean to determine if the word was removed (word doesn't exist in the JSON file already)
     */
    public boolean adminRemoveKeyWord(String word) {
        /* --Create a request for a word within the JSON file-- */
        /* If the request returns null return false, else return true (word was removed) */
        return false;
    }

    /**
     * Create a word that goes into the JSON file that the user has recommended, incrementing the occurrences stored in JSON object
     */
    public void userRecommendKeyWord(String word) {
        /* --Add the word to a list stored within the JSON file-- */
    }

    /**
     * Receive notification from notification class to add to the permanent list of keywords
     */
    public boolean userAddKeyWord(String word) {
        /* --Add the word to a list stored within the JSON file-- */
        /* --Return value of the success/failure of addition-- */
        return false;
    }


    public static void main(String[] args) {
        StringParser sp = new StringParser("tessst.txt");
        sp.parseBody();
    }
}
