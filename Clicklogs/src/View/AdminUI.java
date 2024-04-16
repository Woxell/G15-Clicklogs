package View;

import Model.Alt;

import java.util.List;
import java.util.Scanner;

public class AdminUI {
    private Scanner scanner;

    public AdminUI() {
        scanner = new Scanner(System.in);
    }

    public void print(String text) {
        System.out.println(text);
    }

    public void invalidInput() {
        print("Invalid input, try again.");
    }

    public void printAltList(List<Alt> list) {
        int n = 0;
        for (Alt a : list) {
            print(n++ + ": " + a.getAltLabelText());
        }
    }

    public void printStringList(List<String> list) {
        int n = 0;
        for (String s : list) {
            print(n++ + ": " + s);
        }
    }

    public String askUserString(String text) {
        print(text);
        String answer = scanner.nextLine();
        return answer;
    }

    public int askUserInt(String text) {
        boolean invalidInput = true;
        int answer = 0;
        while (invalidInput) {
            try {
                answer = Integer.parseInt(askUserString(text));
                invalidInput = false;
            } catch (NumberFormatException e) {
                invalidInput();
            }
        }
        return answer;
    }

    public boolean askUserBoolean(String text) {
        String answer = askUserString(text);
        return answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("y");
    }

    public void closeScanner() {
        scanner.close();
    }
}
