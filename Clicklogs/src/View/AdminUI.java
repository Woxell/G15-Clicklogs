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
        for (Alt a : list) {
            print(list.indexOf(a) + ": " + a.getAltLabelText());
        }
    }

    public void printStringList(List<String> list) {
        for (String s : list) {
            print(list.indexOf(s) + ": " + s);
        }
    }

    public String askUserString(String text) {
        print(text);
        return scanner.nextLine();
    }

    public int askUserInt(String text) {
        boolean invalidInput = true;
        int answer = 0;
        while (invalidInput) {
            String input = askUserString(text);
            try {
                answer = Integer.parseInt(input);
                invalidInput = false;
            } catch (NumberFormatException e) {
                invalidInput();
            }
        }
        return answer;
    }

    public boolean askUserBoolean(String text) {
        String answer = askUserString(text);
        return answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes");
    }

    public void closeScanner() {
        scanner.close();
    }
}
