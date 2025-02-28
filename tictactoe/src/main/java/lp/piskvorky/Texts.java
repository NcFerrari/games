package lp.piskvorky;

public enum Texts {

    TITLE("Piškvorky"),
    WIN("Výhra"),
    VICTORY("VÍTĚZSTVÍ !!!"),
    WINS("Vyhrává ");

    private final String text;

    Texts(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
