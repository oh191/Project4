import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Practice {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String replacedWord = "";
        String replacedWord1 = "";
        int k = 0;
        System.out.println("Welcome to TextFilter!");

        boolean keepFiltering;

            System.out.println("Please select one of the following filtering options:");
            System.out.println("1. Filter Word\n" +
                    "2. Find-And-Replace\n" +
                    "3. Censor Information\n\n");


            int choice = scanner.nextInt();
            scanner.nextLine();
            replacedWord = "";
            replacedWord1 = "";

            String passage = "";
            System.out.println("Please enter the passage you would like filtered: ");
            passage = scanner.nextLine();


            String word;
            System.out.println("Please enter the word you would like to censor: ");
            word = scanner.nextLine();
            System.out.println("Uncensored: ");
            System.out.println(passage);


            int lengthDifference = passage.length() - word.length();
            for (int i = 0; i < word.length(); i++)
                replacedWord += "X";

            if (passage.substring(lengthDifference).equals(word)) {
                if ((passage.charAt(lengthDifference - 1) == ' ')) {
                    passage = passage.substring(0, lengthDifference) + replacedWord;
                }
            }
            for (int i = 0; i < lengthDifference; i++) {
                String a = passage.substring(i, i + word.length());

                if (a.equals(word)) {
                    if (i == 0) {
                        if ((passage.charAt(i + word.length()) == ' ') || (passage.charAt(i + word.length()) == '!')
                                || (passage.charAt(i + word.length()) == '.')
                                || (passage.charAt(i + word.length()) == '?')
                                || (passage.charAt(i + word.length()) == ',')) {
                            passage = replacedWord + passage.substring(word.length());
                        }
                    } else if (((passage.charAt(i - 1) == ' ') &&
                            ((passage.charAt(i + word.length()) == ' ')
                                    || (passage.charAt(i + word.length()) == '!')
                                    || (passage.charAt(i + word.length()) == '.')
                                    || (passage.charAt(i + word.length()) == '?')
                                    || (passage.charAt(i + word.length()) == ',')))) {
                        passage = passage.substring(0, i) + replacedWord + passage.substring(i + word.length());
                    }
                }
            }
            System.out.println("Censored: ");
            System.out.println(passage);
        }
    }

