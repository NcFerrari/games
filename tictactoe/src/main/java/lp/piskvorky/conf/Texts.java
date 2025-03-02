package lp.piskvorky.conf;

import lombok.Getter;

@Getter
public enum Texts {

    TITLE("Piškvorky"),
    WIN("Výhra"),
    VICTORY("VÍTĚZSTVÍ !!!"),
    WINS("Vyhrává ");

    private final String text;

    Texts(String text) {
        this.text = text;
    }
}
