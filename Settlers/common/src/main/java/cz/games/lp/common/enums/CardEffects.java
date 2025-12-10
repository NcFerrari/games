package cz.games.lp.common.enums;

import lombok.Getter;

public enum CardEffects {
    SWORD(Sources.SWORD),
    ANOTHER_PRODUCTION(null),
    SETTLER(Sources.SETTLER),
    STONE(Sources.STONE),
    WOOD(Sources.WOOD),
    COMMON_CARD(null),
    FACTION_CARD(null),
    GOLD(Sources.GOLD),
    FOOD(Sources.FOOD),
    SCORE_POINT(null),
    CARD(null),
    TWO_SAMURAIS(null),
    TWO_CARDS(null),
    TWO_SETTLERS(null),
    FOUR_SCORE_POINTS(null);

    @Getter
    private final Sources source;

    CardEffects(Sources source) {
        this.source = source;
    }
}
