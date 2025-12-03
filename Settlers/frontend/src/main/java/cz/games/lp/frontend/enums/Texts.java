package cz.games.lp.frontend.enums;

public enum Texts {

    TITLE("Imperial Settlers"),
    COMMON_CARD("common"),
    CHOICE("Výběr"),
    DIALOG_BUTTON("OK");

    private final String text;

    Texts(String text) {
        this.text = text;
    }

    public String get() {
        return text;
    }
}
