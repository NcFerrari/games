package lp.be.enums;

import lombok.Getter;

@Getter
public enum TextEnum {

    TESTING_SUDOKU_1("testfiles/sudoku1.txt"),
    TESTING_SUDOKU_2("testfiles/sudoku2.txt"),
    LOADING_TIME(" loading time"),
    PROCESS_TIME(" process_time"),
    SPACE(" ");

    private final String text;

    TextEnum(String text) {
        this.text = text;
    }

}
