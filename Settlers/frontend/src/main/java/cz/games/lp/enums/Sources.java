package cz.games.lp.enums;

public enum Sources {
    SETTLER("settler"),
    WOOD("wood"),
    STONE("stone"),
    FOOD("food"),
    GOLD("gold"),
    SWORD("sword"),
    SHIELD("shield"),
    EGYPT_TOKEN("egypt_token"),
    LOCATION("location"),
    SCORE_POINT("score_point");

    private final String name;

    Sources(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
