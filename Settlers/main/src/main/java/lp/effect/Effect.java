package lp.effect;

import cz.games.lp.components.Card;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.Sources;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface Effect {

    CompletableFuture<Void> produce(Card card, Map<Sources, SourceStatusBlock> sources);
}
