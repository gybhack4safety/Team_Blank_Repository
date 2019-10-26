import java.util.ArrayList;
import java.util.Scanner;

/**
 * The Notification class receives flagged phrases from the Message class and outputs
 * to user a helpful message and third-party resources depending on the severity of the phrases.
 *
 * @author Kenny Nguyen
 * @version 1.0
 * @since 2019-10-26
 */
public class Notification {

    private ArrayList<String> phrases;
    private String firstTimeStamp;
    private String lastTimeStamp;

    private static final String DISCRIMINATION_MESSAGE = "";
    private static final String THREAT_MESSAGE = "";
    private static final String HARASSMENT_MESSAGE = "";

    /**
     * Constructs a Notification object
     * @param phrases phrases that has been flagged by Message
     * @param firstTimeStamp timestamp of the first phrase
     * @param lastTimeStamp timestamp of the last phrase
     */
    public Notification(ArrayList<String> phrases, String firstTimeStamp, String lastTimeStamp) {
        this.phrases = phrases;
        this.firstTimeStamp = firstTimeStamp;
        this.lastTimeStamp = lastTimeStamp;
    }

    /**
     * Outputs all of the flagged phrases based on severity
     */
    private void listPhrases() {
        System.out.println("The following phrases have been flagged as potentially dangerous to your mental health:");
        for (String phrase: sortPhrases())
            System.out.println(phrase);
    }

    /**
     * Sorts the given phrases based on severity
     * @return an ArrayList with sorted phrases
     */
    private ArrayList<String> sortPhrases() {
        ArrayList<String> temp = phrases;
        ArrayList<String> sortedPhrases = new ArrayList<>();
        for (String phrase: temp) {
            int highestSeverity = 0;
            String highestSeverePhrase = "";
            for (String phrase2: temp) {
                if (phrase2.getSeverity() > highestSeverity) {
                    highestSeverity = phrase2.getSeverity();
                    highestSeverePhrase = phrase2;
                }
            }
            temp.remove(highestSeverePhrase);
            sortedPhrases.add(highestSeverePhrase);
        }
        return sortedPhrases;
    }

    /**
     * Asks users if they find the outputted phrases offensive
     * @return whether user finds this phrase offensive or not
     */
    public boolean doesUserFindPhraseOffensive() {
        listPhrases();
        System.out.println("These messages were from " + firstTimeStamp + " to " + lastTimeStamp);
        String isOffensive;
        do {
            System.out.println("Do you believe all of these phrases are offensive? (Y/N)");
            Scanner userInput = new Scanner(System.in);
            isOffensive = userInput.next();
        } while (!isOffensive.equalsIgnoreCase("Y") && !isOffensive.equalsIgnoreCase("N"));

        if (isOffensive.equalsIgnoreCase("Y"))
            sendHelpfulMessage();
        else
            notAThreat();
    }

    /**
     * Sends user a supportive message containing a list of possible resources based on severity of phrases
     * @return a supportive message for user
     */
    public String sendHelpfulMessage() {

    }

    /**
     * Prompts user to determine which key words from phrases are offensive. Offensive words will have their severity
     * set to 0
     */
    public void notAThreat() {
        System.out.println("Which of the words do you find offensive? Please enter them one by one. Enter in " +
                "\"Quit application\" to quit.");
        ArrayList<String> harmlessWords = new ArrayList<>();
        String keyword = "";
        do {
            Scanner input = new Scanner(System.in);
            keyword = input.next();
            harmlessWords.add(keyword);
        } while (!keyword.equalsIgnoreCase("Quit application"));
        for (String harmlessWord: harmlessWords)
            harmlessWord.setSeverity(0);
    }
}
