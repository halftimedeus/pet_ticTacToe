package pet_project_TicTacToe;

import java.util.Arrays;
import java.util.Scanner;

public class CheckUserInput {
    //проверяет правильность ввода, если ввод неверный, возвращает false
    public static final String[] KEYS = {"7", "8", "9", "4", "5", "6", "1", "2", "3"};
    public static final String[] SYMBOLS = {"X", "O"};

    public static boolean checkCorrectInputNum(String userInput1) {
        if (userInput1.isEmpty()) {
            System.out.println("Your input is empty. Enter number 1-9");
            return false;
        }
        if (userInput1.length() > 1) {
            System.out.println("Please enter one number 1-9");
            checkIfRepeatTutorial();
            return false;
        } else {
            String convertUserInput = String.valueOf(userInput1.charAt(0));
            if (!Arrays.asList(KEYS).contains(userInput1)) {
                System.out.println("Invalid input. Enter number 1-9");
                checkIfRepeatTutorial();
                return false;
            }
        }
        return true;
    }

    //проверяет, что пользователь правильно выбрал символ
    public static boolean checkCorrectXO(String userInput1) {
        if (userInput1.isEmpty()) {
            System.out.println("Your input is empty. Type 'X' or 'O'");
            return false;
        }
        if (userInput1.length() > 1) {
            System.out.println("Please type 'X' or 'O'");
            return false;
        } else {
            String convertUserInput = String.valueOf(userInput1.charAt(0));
            if (!Arrays.asList(SYMBOLS).contains(userInput1.toUpperCase())) {
                System.out.println("Invalid input. Type 'X' or 'O'");
                return false;
            }
        }
        return true;
    }

    //проверяет, хочет ли игрок снова распечатать управление после неверного ввода
    private static void checkIfRepeatTutorial() {
        System.out.println("Show the controls again? YES/NO");

        boolean correctAnswer = false;
        while (!correctAnswer) {
            String yesOrNo = new Scanner(System.in).nextLine();
            if (!checkYesAndNo(yesOrNo)) {
                System.out.println("Not a valid answer. Type YES or NO to continue");
            }
            if (yesOrNo.equalsIgnoreCase("yes")) {
                _Core.printField(_Core.CONTROLS_TABLE);
                System.out.println("Enter number 1-9 to make a move");
                correctAnswer = true;
            }
            if (yesOrNo.equalsIgnoreCase("no")) {
                System.out.println("Okay. You can scroll the cmd up to see the controls\n");
                _Core.pause(1000);
                System.out.println("Enter number 1-9 to make a move");
                correctAnswer = true;
            }
        }
    }

    //проверяет, ответил ли игрок "да" или "нет"
    private static boolean checkYesAndNo(String yesOrNo) {
        return yesOrNo.equalsIgnoreCase("YES") || yesOrNo.equalsIgnoreCase("NO");
    }
}
