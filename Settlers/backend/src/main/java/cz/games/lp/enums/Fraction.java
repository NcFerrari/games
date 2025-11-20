package cz.games.lp.enums;

public enum Fraction {
    BARBARIAN("barbarian", "m_barbarian", "f_barbarian"),
    JAPAN("japan", "m_japan", "f_japan"),
    ROMAN("roman", "m_roman", "f_roman"),
    EGYPT("egypt", "m_egypt", "f_egypt");

    private final String fractionTitle;
    private final String mBoard;
    private final String fBoard;

    Fraction(String fractionTitle, String mBoard, String fBoard) {
        this.fractionTitle = fractionTitle;
        this.mBoard = mBoard;
        this.fBoard = fBoard;
    }

    public String getFractionTitle() {
        return fractionTitle;
    }

    public String getMBoard() {
        return mBoard;
    }

    public String getFBoard() {
        return fBoard;
    }
}
