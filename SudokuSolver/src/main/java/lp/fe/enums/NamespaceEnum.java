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
    RED_COLOR_STYLE("red-color");

    private final String text;

    NamespaceEnum(String text) {
        this.text = text;
    }
}
