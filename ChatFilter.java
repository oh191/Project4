import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Chatting program
 *
 * @author Junseok Oh
 * @author Javed Hashim
 * @version 11-15-18
 */

public class ChatFilter {
    ArrayList<String> censoring = new ArrayList<>();

    public ChatFilter(String badWordsFileName) {
        String line;
        try {
            FileReader fr = new FileReader(badWordsFileName);
            BufferedReader br = new BufferedReader(fr);
            while ((line = br.readLine()) != null) {
                censoring.add(line);
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String filter(String msg) {
        String target;
        String censored;
        String passageFixed = msg;
        for (int i = 0; i < censoring.size(); i++) {
            censored = "";
            target = censoring.get(i);
            int lengths = target.length();
            for (int j = 0; j < target.length(); j++) {
                censored += "*";
            }
            for (int j = 0; j <= passageFixed.length() - lengths; j++) {
                if (passageFixed.substring(j, j + lengths).equalsIgnoreCase(target)) {
                    passageFixed = passageFixed.substring(0, j) + censored + passageFixed.substring(j + lengths);
                }
            }
        }
        return passageFixed;
    }
}
