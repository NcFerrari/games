package lp;

import lp.be.Sudoku;
import lp.be.enums.TextEnum;
import lp.be.serviceimpl.LoggerServiceImpl;
import lp.fe.MainApp;
import org.apache.log4j.Logger;

public class Manager {

    private static Manager manager;
    private final Logger log = LoggerServiceImpl.getInstance(Manager.class).getLog();

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
            javafx.application.Application.launch(MainApp.class);
        }
        return manager;
    }

    private Manager() {
    }

    public void process() {
        Sudoku sudoku = new Sudoku();
        long start = System.currentTimeMillis();
        sudoku.loadSudoku(getClass().getClassLoader().getResourceAsStream(TextEnum.TESTING_SUDOKU_1.getText()));
        log.info((System.currentTimeMillis() - start) + TextEnum.LOADING_TIME.getText());
        start = System.currentTimeMillis();
        sudoku.process();
        log.info((System.currentTimeMillis() - start) + TextEnum.PROCESS_TIME.getText());
        sudoku.output();
    }

    public static void main(String[] args) {
        getInstance();
    }
}
