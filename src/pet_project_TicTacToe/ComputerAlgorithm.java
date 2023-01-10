package pet_project_TicTacToe;

import java.util.*;

public class ComputerAlgorithm {

    static Random rand = new Random();

    //список клавиш, который привязан к ходам компьютера и игрока
    //если алгоритм выходит на ничью, он выбирает из него ход случайным образом
    public static ArrayList<String> notUsedKeys = new ArrayList<>(Arrays.asList(CheckUserInput.KEYS));

    //делает ход в зависимости от этапа игры

    public static void firstComputerTurn() {
        computerInputToKey("5");
        notUsedKeys.remove("5");
    }

    public static int keysLength = CheckUserInput.KEYS.length - 1;

    public static void secondComputerTurn() {
        int numericUserInput = Integer.parseInt(_Core.userInput);
        if (numericUserInput % 2 > 0) {
            checkOddTurn(keysLength);
        } else if (numericUserInput % 2 == 0) {
            checkEvenTurn(numericUserInput);
        }
    }

    public static void computerTakeWinOrDraw() {
        int[] turn;
        //проверка по столбцам
        turn = checkIfTwoSymbolsColumn(_Core.gameField);
        if (almost3Col) {
            computerInputToKey(getKey(_Core.cellsMapping, turn));
            return;
        }
        //проверка по строкам
        turn = checkIfTwoSymbolsPlayerRow(_Core.gameField);
        if (almost3Row) {
            computerInputToKey(getKey(_Core.cellsMapping, turn));
            return;
        }
        //проверка по диагоналям
        turn = checkIfCanWinDiagonal(_Core.gameField);
        if (almost3Diagonal) {
            computerInputToKey(getKey(_Core.cellsMapping, turn));
            return;
        }
        //компьютер ходит случайно в случае скорой ничьей
        String randomElement = notUsedKeys.get(rand.nextInt(notUsedKeys.size()));
        computerInputToKey(randomElement);
    }

    //если игрох ходит в угол, ходим в противоположный угол
    private static void checkOddTurn(int keysLength) {
        for (int i = 0; i <= keysLength; i++) {
            if (CheckUserInput.KEYS[i].equals(_Core.userInput)) {
                String key = CheckUserInput.KEYS[keysLength - i];
                computerInputToKey(key);
                notUsedKeys.remove(key);
            }
        }
    }

    //выбираем случайный дальний угол в зависимости от того, куда походил игрок
    private static void checkEvenTurn(int numericUserInput) {
        switch (numericUserInput) {
            case (8) -> computerInputToKey(chooseRandom(1, 3));
            case (4) -> computerInputToKey(chooseRandom(3, 9));
            case (2) -> computerInputToKey(chooseRandom(7, 9));
            case (6) -> computerInputToKey(chooseRandom(1, 7));
        }
    }

    public static String turn;

    public static String chooseRandom(int a, int b) {
        turn = String.valueOf(rand.nextBoolean() ? a : b);
        notUsedKeys.remove(turn);
        return turn;
    }

    //находит ключ от значения
    private static String getKey(HashMap<String, int[]> cellsMapping, int[] turn) {
        String key = null;
        for (Map.Entry<String, int[]> entry : cellsMapping.entrySet()) {
            key = entry.getKey();
            int[] value = entry.getValue();

            if (Arrays.equals(value, turn)) {
                return key;
            }
        }
        return key;
    }

    //делает ход компьютера
    public static void computerInputToKey(String computerInput) {
        notUsedKeys.remove(computerInput);
        int[] cell = _Core.cellsMapping.get(computerInput);
        _Core.gameField[cell[0]][cell[1]] = _Core.computerSymbol;
        printRandomPhrase();
        _Core.pause(1000);
    }

    //печатаем случайную фразу
    private static final String[] PHRASES = {"Praise the sun!", "Endure and survive...", "Do you like hurting other people?",
            "It’s dangerous to go alone, take this!", "I'm kind out of options...", "At least I have chicken!",
            "Do a barrel roll!", "I never asked for this"};

    private static void printRandomPhrase() {
        int index = rand.nextInt(PHRASES.length);
        System.out.println(PHRASES[index]);
    }

    // проверяет, есть ли выигрышное условие по колонкам
    public static boolean almost3Col;
    public static int[] checkIfTwoSymbolsColumn(String[][] gameField) {
        almost3Col = false;
        int[] emptyCell = new int[2];
        int[] draw = new int[2];
        int[] win = new int[2];
        int playerSymbol2 = 0;
        int computerSymbol2 = 0;

        for (int col = 0; col < 3; col++) {
            //проверяет, заполнен ли столбец
            if (isColumnFull(gameField)) {
                continue;
            }
            int playerSymbolCount = 0;
            int computerSymbolCount = 0;

            //проверили одну колонку на победное условие
            for (int row = 0; row < 3; row++) {
                if (gameField[row][col].equals(_Core.playerSymbol)) {
                    playerSymbolCount++;
                    if (playerSymbolCount > 1)
                        playerSymbol2++;
                } else if (gameField[row][col].equals(_Core.computerSymbol)) {
                    computerSymbolCount++;
                    if (computerSymbolCount > 1)
                        computerSymbol2++;
                } else if (gameField[row][col].equals(" ")) {
                    emptyCell[0] = row;
                    emptyCell[1] = col;
                }
            }
            //если столбец не заполнен и в нём больше двух символов, запоминаем пустую ячейку, куда можно походить
            if (playerSymbolCount > 1) {
                draw[0] = emptyCell[0];
                draw[1] = emptyCell[1];
            } else if (computerSymbolCount > 1) {
                win[0] = emptyCell[0];
                win[1] = emptyCell[1];
            }
            //если почти выиграл и человек и компьютер, компьютер выигрывает
            //если почти выиграл человек, а компьютер нет, то компьютер пытается помешать
        }
        if (playerSymbol2 > 0 && computerSymbol2 > 0) {
            almost3Col = true;
            return win;
        } else if (computerSymbol2 > 0 && playerSymbol2 == 0) {
            almost3Col = true;
            return win;
        } else if (playerSymbol2 > 0 && computerSymbol2 == 0) {
            almost3Col = true;
            return draw;
        }
        return null;
    }

    //проверяем, заполнен ли столбец
    private static boolean isColumnFull(String[][] gameField) {
        for (int col = 0; col < 3; col++) {
            int symbol = 0;
            for (int row = 0; row < 3; row++) {
                if (gameField[row][col].equals("X") || gameField[row][col].equals("O")) {
                    symbol++;
                }
            }
            if (symbol == 3) {
                return true;
            }
        }
        return false;
    }

    // проверяет, есть ли выигрышное условие по столбцу
    public static boolean almost3Row;
    public static int[] checkIfTwoSymbolsPlayerRow(String[][] gameField) {
        almost3Row = false;
        int[] emptyCell = new int[2];
        int[] draw = new int[2];
        int[] win = new int[2];
        int playerSymbol2 = 0;
        int computerSymbol2 = 0;

        for (int row = 0; row < 3; row++) {
            if (isRowFull(gameField)) {
                continue;
            }
            int playerSymbolCount = 0;
            int computerSymbolCount = 0;

            //проверили одну строку на победное условие
            for (int col = 0; col < 3; col++) {
                if (gameField[row][col].equals(_Core.playerSymbol)) {
                    playerSymbolCount++;
                    if (playerSymbolCount > 1)
                        playerSymbol2++;
                } else if (gameField[row][col].equals(_Core.computerSymbol)) {
                    computerSymbolCount++;
                    if (playerSymbolCount > 1)
                        computerSymbol2++;
                } else if (gameField[row][col].equals(" ")) {
                    emptyCell[0] = row;
                    emptyCell[1] = col;
                }

            }
            // схожий принцип работы, что и в методе с проверкой колонки
            if (playerSymbolCount > 0) {
                draw[0] = emptyCell[0];
                draw[1] = emptyCell[1];
            } else if (computerSymbolCount > 0) {
                win[0] = emptyCell[0];
                win[1] = emptyCell[1];
            }
        }
        if (playerSymbol2 > 0 && computerSymbol2 > 0) {
            almost3Row = true;
            return win;
        } else if (computerSymbol2 > 0 && playerSymbol2 == 0) {
            almost3Row = true;
            return win;
        } else if (playerSymbol2 > 0 && computerSymbol2 == 0) {
            almost3Row = true;
            return draw;
        }
        return null;
    }

    //проверяет,заполнена ли строка
    private static boolean isRowFull(String[][] gameField) {
        for (int row = 0; row < 3; row++) {
            int symbol = 0;
            for (int col = 0; col < 3; col++) {
                if (gameField[row][col].equals("X") || gameField[row][col].equals("O")) {
                    symbol++;
                }
            }
            if (symbol == 3) {
                return true;
            }
        }
        return false;
    }

    // проверяет, есть ли выигрышное условие по диагоналям
    private static boolean almost3Diagonal;
    private static int[] checkIfCanWinDiagonal(String[][] gameField) {
        almost3Diagonal = false;
        int[] emptyCell = new int[2];
        int[] win = new int[2];
        //проверяем диагональ с левого верхнего угла
        int twoComputerSymbols = 0;
        if (DiagonalFullLeftRight(gameField)) {
            return checkIfCanWinDiagonalRightLeft(gameField);
        }
        for (int i = 0; i < 3; i++) {
            if (gameField[i][i].equals(_Core.computerSymbol)) {
                twoComputerSymbols++;
            } else if (gameField[i][i].equals(" ")) {
                emptyCell[0] = i;
                emptyCell[1] = i;
            }
        }
        if (twoComputerSymbols > 1) {
            win[0] = emptyCell[0];
            win[1] = emptyCell[1];
            almost3Diagonal = true;
            return win;
        }
        return checkIfCanWinDiagonalRightLeft(gameField);
    }

    private static boolean DiagonalFullLeftRight(String[][] gameField) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (!gameField[i][i].equals(" ")) {
                count++;
            }
        }
        if (count == 3) {
            return true;
        } else
            return false;
    }

    private static int[] checkIfCanWinDiagonalRightLeft(String[][] gameField) {
        int[] emptyCell = new int[2];
        int[] win = new int[2];
        int twoComputerSymbols = 0;
        //проверяем диагональ с правого верхнего угла
        if (DiagonalFullRightLeft(gameField)) {
            return null;
        }
        for (int col = 2, row = 0; col >= 0 && row <= 2; col--, row++) {
            if (gameField[row][col].equals(_Core.computerSymbol)) {
                twoComputerSymbols++;
            } else if (gameField[row][col].equals(" ")) {
                emptyCell[0] = row;
                emptyCell[1] = col;
            }
        }
        if (twoComputerSymbols > 1) {
            win[0] = emptyCell[0];
            win[1] = emptyCell[1];
            almost3Diagonal = true;
            return win;
        }
        return null;
    }

    private static boolean DiagonalFullRightLeft(String[][] gameField) {
        int count = 0;
        for (int col = 2, row = 0; col >= 0 && row <= 2; col--, row++) {
            if (!gameField[row][col].equals(" ")) {
                count++;
            }
        }
        if (count == 3) {
            return true;
        } else
            return false;
    }
}
