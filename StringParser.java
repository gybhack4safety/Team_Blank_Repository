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
    private FileReader fr;
    private HashMap<String, Keyword> keywords;
    private ArrayList<Keyword> kw;

    // Initializes the parser and converts the word to alphabetical characters
    public StringParser(String in) {
        File fr = new File(in);
        severity = 0;
        keywords = new HashMap<>();
        try {
            s = new Scanner(fr);
        } catch (FileNotFoundException f) {
            System.out.println("File not found: " + in);
            System.exit(1);
        }
        kw = Keyword.createKeywordList(new File("database.json"));
        for (Keyword k: kw) {
            keywords.put(k.getWord(), k);
        }
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
            if (keywords.containsKey(word)) {
                levSeverity += keywords.get(word).getSeverity();
                System.out.println(word);
            }

            i++;
        }
        // Increment the overall severity by the calculated sentence severity
        severity += levSeverity;
    }

    /**
     * Runs parseLine until the file has no more lines
     */
    public void parseBody() {
        System.out.println("Bad words:\n");
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
        Scanner s2 = new Scanner(System.in);
        System.out.println("Please enter 1: Threat, 2: Discrimination, 3: Harassment");
        int in = Integer.parseInt(s2.next());
        if (in == 1) {
            keywords.put(word, Keyword.createEntry(word, Classification.THREAT));
            kw.add(Keyword.createEntry(word, Classification.THREAT));
        } else if (in == 2) {
            keywords.put(word, Keyword.createEntry(word, Classification.DISCRIMINATION));
            kw.add(Keyword.createEntry(word, Classification.DISCRIMINATION));
        } else if (in == 3) {
            keywords.put(word, Keyword.createEntry(word, Classification.HARASSMENT));
            kw.add(Keyword.createEntry(word, Classification.HARASSMENT));
        } else {
            System.out.println("You entered an invalid entry");
            return false;
        }
        return true;
    }

    /**
     * Allows the admin to remove a word within the JSON file
     * @param word Word being removed into the JSON file
     * @return Boolean to determine if the word was removed (word doesn't exist in the JSON file already)
     */
    public boolean adminRemoveKeyWord(String word) {
        /* If the request returns null return false, else return true (word was removed) */
        if (keywords.containsKey(word)) {
            keywords.remove(word);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        StringParser sp = new StringParser("test.txt");
        sp.parseBody();
        System.out.println("Here are some useful links to prevent discrimination: ");
        System.out.println("https://www.fordfoundation.org/the-latest/news/a-new-hotline-for-anonymous-harassment-and-discrimination-complaints/\n" +
                "https://www.sterlingattorneys.com/blog/2017/08/first-steps-to-take-if-you-are-being-discriminated-against.shtml\n" +
                "https://www.chwilliamslaw.com/what-are-signs-that-i-may-be-being-discriminated-against-at-work/");
        System.out.println("");
        System.out.println("Here are some useful links to prevent threats: ");
        System.out.println("https://www.runawayhelpline.org.uk/stages-of-running/im-being-hurt-abused-or-threatened/\n" +
                "https://blogs.findlaw.com/law_and_life/2017/02/what-to-do-if-someone-threatens-you.html");
        System.out.println("");

        System.out.println("Here are some useful links to prevent harassment: ");
        System.out.println("https://www.thehotline.org/2016/04/21/reporting-to-police-options-tips-for-being-prepared/\n" +
                "http://www.plea.org/legal_resources/?a=303\n" +
                "https://www.beliefnet.com/wellness/galleries/7-ways-to-deal-with-harassment-of-any-kind.aspx");
        System.out.println("");
    }
}
