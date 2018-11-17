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
            FileWriter fw = new FileWriter(badWordsFileName);
            String temp = "";
            while ((line = br.readLine()) != null) {
                temp += line + "\n";
                censoring.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void add(String location, String aLine){
        String line;
        try {
            FileReader fr = new FileReader(location);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fw = new FileWriter(location);
            String temp = "";
            while ((line = br.readLine()) != null) {
                temp += line + "\n";
            }
            temp += aLine + "\n";
            fw.write(temp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String filter(String msg) {
        String target;
        int lengthDifference;
        String censored;
        String passageFixed = msg;
        for (int i = 0; i < censoring.size(); i++) {
            target = censoring.get(i);
            lengthDifference = msg.length() - target.length();
            censored = "";
            for (int j = 0; j < lengthDifference; j++) {
                censored += "*";
            }
            for (int j = 0; j < lengthDifference; j++) {
                String checker = msg.substring(i, i + target.length());
                if (checker.equals(target)){
                    passageFixed = passageFixed.substring(0, i) +
                            censored + passageFixed.substring(i + target.length());
                }
            }
        }
        return passageFixed;
    }
}
