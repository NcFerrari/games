package lp.effect;


import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.Sources;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class EffectImpl implements Effect {

    @Override
    public CompletableFuture<Void> produce(Card card, Map<Sources, SourceStatusBlock> sources) {
        if (card.getCardEffect().contains("||")) {
            String[] choices = card.getCardEffect().split("\\|\\|");
            CompletableFuture[] futures = new CompletableFuture[choices.length];
            for (int i = 0; i < choices.length; i++) {
                futures[i] = sources.get(Sources.valueOf(choices[i])).waitForSelection();
            }
            return CompletableFuture.anyOf(futures).thenAccept(selectedObj -> {
                SourceStatusBlock selected = (SourceStatusBlock) selectedObj;
                selected.addOne();
                for (String choice : choices) {
                    sources.get(Sources.valueOf(choice)).deselect();
                }
            }).thenApply(v -> null);
        }
        if (card.getCardEffect().contains("&&")) {
            Arrays.stream(card.getCardEffect().split("&&")).forEach(source ->
                    sources.get(Sources.valueOf(source)).addOne()
            );
        }
        return CompletableFuture.completedFuture(null);
    }
}
