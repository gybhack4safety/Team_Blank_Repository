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
    private boolean hasDiscrimination;
    private boolean hasThreat;
    private boolean hasHarassment;

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
        this.hasDiscrimination = false;
        this.hasThreat = false;
        this.hasHarassment = false;
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
            determineNonthreateningWords();
    }

    /**
     * Outputs all of the flagged phrases based on severity
     */
    public void listPhrases() {
        determineExistingCategories();
        String potentialThreat = "The following phrases have been flagged as potentially dangerous to your " +
                "mental health because they contain traces of: ";
        if (hasDiscrimination) potentialThreat += "discrimination ";
        if (hasThreat) potentialThreat += "threat ";
        if (hasDiscrimination) potentialThreat += "harassment ";
        System.out.println(potentialThreat);
        for (String phrase: sortPhrases())
            System.out.println(phrase);
    }

    /**
     * Sorts the given phrases based on severity
     * @return an ArrayList with sorted phrases
     */
    public ArrayList<String> sortPhrases() {
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
     * Determines if there are phrases containing traces of discrimination, threats, or harassment
     */
    public void determineExistingCategories() {
        for (String phrase: sortPhrases()) {
            if (hasDiscrimination && hasThreat && hasHarassment) break;
            if (phrase.getClassfication().equalsIgnoreCase("discrimination")) {
                hasDiscrimination = true;
            }
            else if (phrase.getClassfication().equalsIgnoreCase("threat")) {
                hasThreat = true;
            }
            else (phrase.getClassfication().equalsIgnoreCase("harassment"))
            hasHarassment = true;
        }
    }

    /**
     * Prompts user to determine which key words from phrases are offensive. Offensive words will have their severity
     * set to 0
     */
    public void determineNonthreateningWords() {
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

    /**
     * Sends user a supportive message containing a list of possible resources based on severity of phrases
     * @return a supportive message for user
     */
    public void sendHelpfulMessage() {
        if (hasDiscrimination) discriminationMessage();
        if (hasThreat) threatMessage();
        if (hasHarassment) harassmentMessage();
    }

    /**
     * Prints out helpful resources to prevent discrimination
     */
    private void discriminationMessage() {
        System.out.println("Here are some useful links to prevent discrimination: ");
        System.out.println("https://www.fordfoundation.org/the-latest/news/a-new-hotline-for-anonymous-harassment-and-discrimination-complaints/\n" +
                "https://www.sterlingattorneys.com/blog/2017/08/first-steps-to-take-if-you-are-being-discriminated-against.shtml\n" +
                "https://www.chwilliamslaw.com/what-are-signs-that-i-may-be-being-discriminated-against-at-work/");
        System.out.println("");
    }

    /**
     * Prints out helpful resources to prevent threats
     */
    private void threatMessage() {
        System.out.println("Here are some useful links to prevent threats: ");
        System.out.println("https://www.runawayhelpline.org.uk/stages-of-running/im-being-hurt-abused-or-threatened/\n" +
                "https://blogs.findlaw.com/law_and_life/2017/02/what-to-do-if-someone-threatens-you.html");
        System.out.println("");
    }

    /**
     * Prints out helpful resources to prevent harassment
     */
    private void harassmentMessage() {
        System.out.println("Here are some useful links to prevent harassment: ");
        System.out.println("https://www.thehotline.org/2016/04/21/reporting-to-police-options-tips-for-being-prepared/\n" +
                "http://www.plea.org/legal_resources/?a=303\n" +
                "https://www.beliefnet.com/wellness/galleries/7-ways-to-deal-with-harassment-of-any-kind.aspx");
        System.out.println("");
    }
}
