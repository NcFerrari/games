package lp.fe.enums;

import lombok.Getter;

@Getter
public enum NamespaceEnum {

    TITLE("Sudoku"),
    CSS_STYLE("css/main.css"),
    RIGHT_STYLE("right"),
    EMPTY_TEXT(""),
    RIGHT_UP_STYLE("right-and-up"),
    UP_STYLE("up"),
    RED_COLOR_STYLE("red-color"),
    BLACK_COLOR_STYLE("black-color"),
    LOAD_FILE_BUTTON_TEXT("Načíst soubor"),
    CHOOSE_FILE_TEXT("Vyberte sudoku soubor");

    private final String text;

    NamespaceEnum(String text) {
        this.text = text;
    }
}
