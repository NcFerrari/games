package cz.games.lp.enums;

import lombok.Getter;

@Getter
public enum Sources {
    SETTLER("settler"),
    WOOD("wood"),
    STONE("stone"),
    FOOD("food"),
    GOLD("gold"),
    SWORD("sword"),
    SHIELD("shield"),
    EGYPT_TOKEN("egypt_token"),
    LOCATION("location");

    private final String name;

    Sources(String name) {
        this.name = name;
    }

}
