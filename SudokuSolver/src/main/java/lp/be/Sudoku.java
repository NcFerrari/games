package lp.be;

import lp.Manager;
import lp.be.service.LoggerService;
import lp.be.serviceimpl.LoggerServiceImpl;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Sudoku {

    private final LoggerService loggerService = LoggerServiceImpl.getInstance(Manager.class);
    private final Logger log = loggerService.getLog();
    private NumberChooser numberChooser;
    private int[][] sudokuData;

    public void loadSudoku(File file) {
        if (file != null && file.exists()) {
            try {
                loadSudoku(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                log.error(e);
            }
        }
    }

    public void loadSudoku(InputStream is) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            List<int[]> sudokuInList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                sudokuInList.add(getIntArray(line));
            }
            mapListToSudoku(sudokuInList);
        } catch (IOException e) {
            log.error(e);
        }
        numberChooser = new NumberChooser(this);
    }

    public void process() {
        numberChooser.processRow(numberChooser.getFieldList().get(0));
        numberChooser.processColumn(0);
        numberChooser.processSquare(1);
        numberChooser.checkResult();
    }

    public void output() {
        numberChooser.getFieldList().forEach(fields -> {
            fields.forEach(field -> System.out.print(field.getResultNumber() + "\t\t"));
            System.out.println();
        });
    }

    public boolean isSudokuLoaded() {
        return sudokuData.length != 0;
    }

    public int[][] getData() {
        return sudokuData;
    }

    private int[] getIntArray(String line) {
        int[] numbers = new int[line.split(" ").length];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = Integer.parseInt(line.split(" ")[i]);
        }
        return numbers;
    }

    private void mapListToSudoku(List<int[]> sudokuInList) {
        sudokuData = new int[sudokuInList.size()][];
        for (int i = 0; i < sudokuInList.size(); i++) {
            sudokuData[i] = sudokuInList.get(i);
        }
    }
}