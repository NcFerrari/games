package cz.games.lp.common.enums;

import lombok.Getter;

public enum Conditions {
    FACTION_CARD_2(null),
    SAMURAI_3(null),
    BLACK_6(Colors.BLACK),
    GOLD_6_CARD(Colors.GOLD),
    PINK_3(Colors.PINK),
    RED_6_CARD(Colors.RED),
    PINK_6_CARD(Colors.PINK),
    GRAY_3(Colors.GRAY),
    RED_3(Colors.RED),
    GOLD_3(Colors.GOLD),
    BROWN_3(Colors.BROWN),
    GRAY_6_CARD(Colors.GRAY),
    BROWN_6_CARD(Colors.BROWN);

    @Getter
    private final Colors color;

    Conditions(Colors color) {
        this.color = color;
    }
}
