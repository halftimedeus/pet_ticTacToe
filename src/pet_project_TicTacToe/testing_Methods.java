package pet_project_TicTacToe;

import java.util.Arrays;

public class testing_Methods {

    private static final String[][] gameField = {
            {" ", " ", "O"},
            {" ", "O", " "},
            {"O", "X", " "}
    };

    public static void main(String[] args) {
        System.out.println(Arrays.toString(checkIfCanWinDiagonal(gameField)));
        CheckState.checkWinCondition(gameField);
    }

    private static boolean almost3Col;

    public static int[] checkIfTwoSymbolsColumn(String[][] gameField) {
        almost3Col = false;
        int[] emptyCell = new int[2];
        int[] draw = new int[2];
        int[] win = new int[2];
        int playerSymbol2 = 0;
        int computerSymbol2 = 0;

        for (int col = 0; col < 3; col++) {
            //проверяет, заполнен ли столбец
            /* if (isColumnFull(gameField)) {
                continue;
            } */
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
            if (gameField[i][i].equals(_Core.computerSymbol)) {
                count++;
            }
        }
        return count == 3;
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
        for (int col = 2, row = 0; col > 0 && row < 2; col--, row++) {
            if (gameField[row][col].equals(_Core.computerSymbol)) {
                count++;
            }
        }
        return count == 3;
    }

    public static void checkWhoWins(int threeComputer, int threePlayer, String[][] gameField) {
        if (threeComputer == 3) {
            _Core.printField(gameField);
            System.out.println("YOU WIN!\n" +
                    "CONGRATS");
            System.exit(1);
        } else if (threePlayer == 3) {
            _Core.printField(gameField);
            System.out.println("COMPUTER WINS!\n" +
                    "BETTER LUCK NEXT TIME");
            System.exit(2);
        } else if (CheckState.checkIfDraw(gameField)) {
            _Core.printField(gameField);
            System.out.println("SORRY, DRAW!\n" +
                    "GAME OVER");
            System.exit(2);
        }
    }

}
