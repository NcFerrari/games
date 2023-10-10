package lp.fe.enums;

import lombok.Getter;

@Getter
public enum NamespaceEnum {

    TITLE("Sudoku"),
    CSS_STYLE("css/main.css");

    private final String text;

    NamespaceEnum(String text) {
        this.text = text;
    }
}
