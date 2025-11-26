package lp.effect;


import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.Sources;

import java.util.Arrays;
import java.util.Map;

public class EffectImpl implements Effect {

    @Override
    public void produce(Card card, Map<Sources, SourceStatusBlock> sources) {
        if (card.getCardEffect().contains("||")) {
            Arrays.stream(card.getCardEffect().split("\\|\\|")).forEach(source -> {
                sources.get(Sources.valueOf(source)).select();
            });
        }
        if (card.getCardEffect().contains("&&")) {
            Arrays.stream(card.getCardEffect().split("&&")).forEach(source -> sources.get(Sources.valueOf(source)).addOne());
        }
    }
}
