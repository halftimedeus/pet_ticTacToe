package pet_project_TicTacToe;

public class CheckState {

    //проверяет текущее состояние игрового поля по всем клеткам
    public static void checkWinCondition(String[][] gameField) {
        check3InRow(gameField);
        check3InColumn(gameField);
        check3Diagonal(gameField);
    }

    //проверяет, есть ли 3 одинаковых символа по диагонали
    private static void check3Diagonal(String[][] gameField) {
        //проверяем диагональ с левого верхнего угла
        int threeComputer = 0;
        int threePlayer = 0;
        for (int i = 0; i < 3; i++) {
            if (gameField[i][i].equals(_Core.computerSymbol)) {
                threeComputer++;
            } else if (gameField[i][i].equals(_Core.playerSymbol)) {
                threePlayer++;
            }
        }
        checkWhoWins(threeComputer, threePlayer, gameField);

        threeComputer = 0;
        threePlayer = 0;
        //проверяем диагональ с правого верхнего угла
        for (int col = 2, row = 0; col >= 0 && row <= 2; col--, row++) {
            if (gameField[row][col].equals(_Core.computerSymbol)) {
                threeComputer++;
            } else if (gameField[row][col].equals(_Core.playerSymbol)) {
                threePlayer++;
            }
        }
        checkWhoWins(threeComputer, threePlayer, gameField);
    }

    //проверяем, есть ли 3 одинаковых символов в колонке
    private static void check3InColumn(String[][] gameField) {
        for (int col = 0; col < 3; col++) {
            int threeComputer = 0;
            int threePlayer = 0; // сброс счетчиков
            for (int row = 0; row < 3; row++) {
                if (gameField[row][col].equals(_Core.computerSymbol)) {
                    threeComputer++;
                } else if (gameField[row][col].equals(_Core.playerSymbol)) {
                    threePlayer++;
                }
                checkWhoWins(threeComputer, threePlayer, gameField);
            }
        }
    }

    //проверяем, есть ли 3 одинаковых символах в строке
    private static void check3InRow(String[][] gameField) {
        for (int row = 0; row < 3; row++) {
            int threeComputer = 0;
            int threePlayer = 0; // сброс счетчиков
            for (int col = 0; col < 3; col++) {
                if (gameField[row][col].equals(_Core.computerSymbol)) {
                    threeComputer++;
                } else if (gameField[row][col].equals(_Core.playerSymbol)) {
                    threePlayer++;
                }
                checkWhoWins(threeComputer, threePlayer, gameField);
            }
        }
    }

    //проверяем, сколько ячеек в поле заполнены - если все, объявляем ничью
    public static boolean checkIfDraw(String[][] gameField) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (gameField[row][col].equals(" "))
                    return false;
            }
        }
        return true;
    }

    //в зависимости от результата проверок выводим на экран сообщение о победе и завершаем игру
    public static void checkWhoWins(int threeComputer, int threePlayer, String[][] gameField) {
        if (threePlayer == 3) {
            _Core.printField(gameField);
            System.out.println("YOU WIN!\n" +
                    "CONGRATS");
            System.exit(1);
        } else if (threeComputer == 3) {
            _Core.printField(gameField);
            System.out.println("COMPUTER WINS!\n" +
                    "BETTER LUCK NEXT TIME");
            System.exit(2);
        } else if (checkIfDraw(gameField)) {
            _Core.printField(gameField);
            System.out.println("SORRY, DRAW!\n" +
                    "GAME OVER");
            System.exit(2);
        }
    }
}
