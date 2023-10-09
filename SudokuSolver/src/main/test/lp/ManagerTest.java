package lp;

import lp.be.Sudoku;
import lp.be.enums.TextEnum;
import lp.be.serviceimpl.LoggerServiceImpl;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {

    private final Logger log = LoggerServiceImpl.getInstance(Manager.class).getLog();

    @Test
    void process() {
        String[] test = {"534678912", "672195348", "198342567", "859761423", "426853791", "713924856", "961537284",
                "287419635", "345286179"};
        Sudoku sudoku = new Sudoku();
        long start = System.currentTimeMillis();
        sudoku.loadSudoku(getClass().getClassLoader().getResourceAsStream(TextEnum.TESTING_SUDOKU_1.getText()));
        log.info((System.currentTimeMillis() - start) + TextEnum.LOADING_TIME.getText());
        start = System.currentTimeMillis();
        sudoku.process();
        log.info((System.currentTimeMillis() - start) + TextEnum.PROCESS_TIME.getText());
        String[] sudokuData = sudoku.output();
        for (int i = 0; i < test.length; i++) {
            assertEquals(sudokuData[i], test[i]);
        }
    }
}
