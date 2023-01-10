package pet_project_TicTacToe;

import java.util.HashMap;
import java.util.Scanner;

public class _Core {
    //TODO: make difficulty choice (easy: computer makes random turns on empty fields, hard: le current algorithm)
    public static void main(String[] args) {
        keySet(); // <-- создаёт HashMap, где key - цифра из KEYS, value - ячейка gameField
        System.out.println("Welcome to Tic-Tac-Toe!");
        System.out.println("Use the following mapping table to specify a cell using numbers 1-9:\n");
        pause(500);

        printField(CONTROLS_TABLE); // <-- печатает управление
        System.out.println();
        chooseSymbol(); // <-- позволяет выбрать крестики или нолики, первый ход за компьютером

        firstComputerTurn();
        userTurn();
        secondComputerTurn();

        //дальнейшие ходы по итеративному алгоритму
        while(true) {
            userTurn();
            computerTakeWinOrDraw();
        }
    }

    private static void firstComputerTurn() {
        printStage();
        ComputerAlgorithm.firstComputerTurn();
        printField(gameField);
    }

    private static void secondComputerTurn() {
        printStage();
        ComputerAlgorithm.secondComputerTurn();
        printField(gameField);
    }

    private static void userTurn() {
        printStage();
        getUserInput();
        CheckState.checkWinCondition(gameField);
        printField(gameField);
    }
    private static void computerTakeWinOrDraw() {
        printStage();
        ComputerAlgorithm.computerTakeWinOrDraw();
        CheckState.checkWinCondition(gameField);
        printField(gameField);
    }

    //раскладка управления для игрового поля
    public static final String[][] CONTROLS_TABLE = {
            {"7", "8", "9"},
            {"4", "5", "6"},
            {"1", "2", "3"}
    };

    //пустое поле по умолчанию
    public static String[][] gameField = {
            {" ", " ", " "},
            {" ", " ", " "},
            {" ", " ", " "}
    };

    //выводит на экран игровое поле
    public static void printField(String[][] field) {
        System.out.print("-------------\n" +
                "| " + field[0][0] + " | " + field[0][1] + " | " + field[0][2] + " |\n" +
                "-------------\n" +
                "| " + field[1][0] + " | " + field[1][1] + " | " + field[1][2] + " |\n" +
                "-------------\n" +
                "| " + field[2][0] + " | " + field[2][1] + " | " + field[2][2] + " |\n" +
                "-------------\n" +
                "\n");
    }

    //позволяет игроку выбрать крестики или нолики, компьютер выбирает другой знак
    public static String playerSymbol;
    public static String computerSymbol;

    public static void chooseSymbol() {
        System.out.println("Are you 'X' or 'O'?");
        boolean symbolIsCorrect = false;
        while (!symbolIsCorrect) {
            playerSymbol = new Scanner(System.in).nextLine();
            symbolIsCorrect = CheckUserInput.checkCorrectXO(playerSymbol);
        }
        if (playerSymbol.equalsIgnoreCase("X")) {
            System.out.println("Okay, I'll be 'O' then");
            _Core.pause(500);
            System.out.println("Let's begin >:)");
            _Core.pause(500);
            System.out.println("I'll go first - try to outplay me!\n");
            _Core.pause(500);
            computerSymbol = "O";
        } else if (playerSymbol.equalsIgnoreCase("O")) {
            System.out.println("Okay, I'll be 'X' then");
            _Core.pause(500);
            System.out.println("Let's begin >:)");
            _Core.pause(500);
            System.out.println("I'll go first - try to outplay me!\n");
            _Core.pause(500);
            computerSymbol = "X";
        }
    }

    //печатает текущую стадию игры
    public static int stageCount = 1;

    public static void printStage() {
        System.out.println("STAGE " + stageCount);
        stageCount++;
    }

    //получает ввод от игрока, пока игрок не введёт число из списка
    public static String userInput;

    public static void getUserInput() {
        System.out.println("Your turn!");
        boolean inputIsCorrect = false;
        while (!inputIsCorrect) {
            userInput = new Scanner(System.in).nextLine();
            inputIsCorrect = CheckUserInput.checkCorrectInputNum(userInput);
        }
        inputToKey(userInput);
        ComputerAlgorithm.notUsedKeys.remove(userInput);
    }

    //помещаем userInput в ячейку
    public static void inputToKey(String userInput) {
        int[] cell = cellsMapping.get(userInput);
        gameField[cell[0]][cell[1]] = playerSymbol;
    }

    //создаем hashmap координат ячеек, где ключ — номер из KEYS
    public static HashMap<String, int[]> cellsMapping = new HashMap<>();

    public static void keySet() {
        int numberCount = 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                cellsMapping.put(CheckUserInput.KEYS[numberCount], new int[]{row, col});
                numberCount++;
            }
        }
    }

    //делает паузу
    public static void pause(int timeInMS) {
        try {
            Thread.sleep(timeInMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
