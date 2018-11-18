import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

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
            target = censoring.get(i);
            censored = "";
            for (int j = 0; j < target.length(); j++) {
                censored += "*";
            }
            passageFixed = passageFixed.replaceAll("(?i)" + target, censored);
        }
        return passageFixed;
    }
}
