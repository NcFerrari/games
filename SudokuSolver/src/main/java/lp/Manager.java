package lp;

import lp.be.Sudoku;

public class Manager {

    private static Manager manager;

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private Manager() {
    }

    public void test() {
        Sudoku sudoku = new Sudoku();
        sudoku.loadSudoku(getClass().getClassLoader().getResourceAsStream("testfiles/sudoku1.txt"));
        sudoku.output();
    }

    public static void main(String[] args) {
        getInstance().test();
    }
}
