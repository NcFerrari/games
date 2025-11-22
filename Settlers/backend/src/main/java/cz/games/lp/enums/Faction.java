package cz.games.lp.enums;

public enum Faction {
    BARBARIAN("barbarian", "m_barbarian", "f_barbarian"),
    JAPAN("japan", "m_japan", "f_japan"),
    ROMAN("roman", "m_roman", "f_roman"),
    EGYPT("egypt", "m_egypt", "f_egypt");

    private final String factionTitle;
    private final String mBoard;
    private final String fBoard;

    Faction(String factionTitle, String mBoard, String fBoard) {
        this.factionTitle = factionTitle;
        this.mBoard = mBoard;
        this.fBoard = fBoard;
    }

    public String getFactionTitle() {
        return factionTitle;
    }

    public String getMBoard() {
        return mBoard;
    }

    public String getFBoard() {
        return fBoard;
    }
}
