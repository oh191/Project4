import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Chatting program
 *
 * @author Junseok Oh
 * @author Javed Hashim
 * @version 11-15-18
 */

public class Practice {
    public void censored(String target) {

    }

    public static void main(String[] args) {

        ArrayList<String> targets = new ArrayList<>();
        targets.add("add");
        targets.add("iU");

        Scanner s = new Scanner(System.in);
        String beFiltered = s.nextLine();
        String target = "";
        String filtered;
        for (int i = 0; i < targets.size(); i++) {
            filtered = "";
            target = targets.get(i);
            int lengths = target.length();
            for (int j = 0; j < target.length(); j++) {
                filtered += "*";
            }
            for (int j = 0; j <= beFiltered.length() - lengths; j++) {
                if (beFiltered.substring(j, j + lengths).equalsIgnoreCase(target)) {
                    beFiltered = beFiltered.substring(0, j) + filtered + beFiltered.substring(j + lengths);
                }
            }
        }
        System.out.println(beFiltered);
    }
}
