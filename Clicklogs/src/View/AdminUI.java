
package View;

import Model.Alt;
import java.util.List;
import java.util.Scanner;

/***
 * The AdminUI class provides a command-line user interface for the application's administrator.
 * It allows the administrator to interact with the application by inputting strings, integers, and boolean values.
 * @author Andre
 */
public class AdminUI {

    /**
     * The scanner used to read user input.
     */
    private Scanner scanner;

    /**
     * Constructs a new AdminUI object with a new Scanner.
     */
    public AdminUI() {
        scanner = new Scanner(System.in);
    }

    /**
     * Prints the given text to the console.
     *
     * @param text The text to print.
     */
    public void print(String text) {
        System.out.println(text);
    }

    /**
     * Prints an error message indicating invalid input.
     */

    public void invalidInput() {
        print("Invalid input, try again.");
    }

    /**
     * Prints a list of alternatives to the console.
     *
     * @param list The list of alternatives to print.
     */
    public void printAltList(List<Alt> list) {
        for (Alt a : list) {
            print(list.indexOf(a) + ": " + a.getAltLabelText());
        }
    }

    /**
     * Asks the user to input a string and returns it.
     *
     * @param text The text to display before the user inputs a string.
     * @return The string input by the user.
     */
    public String askUserString(String text) {
        print(text);
        return scanner.nextLine();
    }

    /**
     * Asks the user to input an integer and returns it.
     *
     * @param text The text to display before the user inputs an integer.
     * @return The integer input by the user.
     */

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

    /**
     * Asks the user to input a boolean and returns it.
     *
     * @param text The text to display before the user inputs a boolean.
     * @return The boolean input by the user.
     */
    public boolean askUserBoolean(String text) {
        String answer = askUserString(text);
        return answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes");
    }

    /**
     * Closes the scanner.
     */
    public void closeScanner() {
        scanner.close();
    }
}
