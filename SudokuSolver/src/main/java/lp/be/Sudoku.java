package lp.be;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Sudoku {

    private int[][] sudokuInString;

    public void loadSudoku(int[][] sudokuInString) {
        this.sudokuInString = sudokuInString;
    }

    public void loadSudoku(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}