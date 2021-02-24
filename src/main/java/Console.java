import java.util.Scanner;

public class Console {
    Scanner s = new Scanner(System.in);

    public String promptForString(String prompt) {
        System.out.println(prompt);
        String inputString = s.nextLine().toLowerCase().trim();
        return inputString;
    }
    public void sendMessage(String message) {
        System.out.println(message);
    }
}
