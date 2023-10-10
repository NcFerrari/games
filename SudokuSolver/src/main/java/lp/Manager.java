package lp;

import lp.be.Sudoku;
import lp.be.enums.TextEnum;
import lp.fe.MainApp;

public class Manager {

    private static Manager manager;
    private final Sudoku sudoku;

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
            javafx.application.Application.launch(MainApp.class);
        }
        return manager;
    }

    private Manager() {
        sudoku = new Sudoku();
        sudoku.loadSudoku(getClass().getClassLoader().getResourceAsStream(TextEnum.TESTING_SUDOKU_1.getText()));
    }

    public static void main(String[] args) {
        getInstance();
    }

    public int getSudokuRowsCount() {
        return sudoku.getData().length;
    }

    public int getSudokuColumnsCount() {
        return sudoku.getData()[0].length;
    }
}
