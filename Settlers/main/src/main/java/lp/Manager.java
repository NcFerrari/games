package lp;

import cz.games.lp.MainApp;
import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Fraction;
import cz.games.lp.enums.Sex;
import cz.games.lp.enums.Sources;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Manager implements IManager {

    private static final Fraction SELECTED_FRACTION = Fraction.JAPAN;

    private final Sources[] sources = {Sources.SETTLER, Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.COIN, Sources.SWORD, Sources.SHIELD};
    private final CardType[] cardTypes = {CardType.PRODUCTION, CardType.PROPERTIES, CardType.ACTION};

    private void start() {
        MainApp.run(this);
    }

    public static void main(String[] args) {
        new Manager().start();
    }

    @Override
    public CardType[] getCardTypes() {
        return cardTypes;
    }

    @Override
    public Sources[] getSources() {
        if (Fraction.EGYPT.equals(SELECTED_FRACTION)) {
            Sources[] egyptianSources = new Sources[sources.length + 1];
            System.arraycopy(sources, 0, egyptianSources, 0, sources.length);
            egyptianSources[egyptianSources.length - 1] = Sources.EGYPT_TOKEN;
            return egyptianSources;
        }
        return sources;
    }

    @Override
    public String getTitle() {
        return "Settlers";
    }

    @Override
    public String getCommonCardImage() {
        return "common";
    }

    @Override
    public String getFraction() {
        return SELECTED_FRACTION.getFractionTitle();
    }

    @Override
    public String getFractionBoard(Sex sex) {
        if (Sex.MALE.equals(sex)) {
            return SELECTED_FRACTION.getMBoard();
        }
        return SELECTED_FRACTION.getFBoard();
    }

    @Override
    public List<Card> prepareCards(double cardWidth, double cardHeight) {
        List<Card> cards = IntStream
                .range(1, 85)
                .mapToObj(i -> {
                    String value = "" + i;
                    if (i < 10) {
                        value = "0" + i;
                    }
                    return new Card("commons/com0" + value, cardWidth, cardHeight);
                })
                .collect(Collectors.toList());
        Collections.shuffle(cards);
        return cards;
    }
}
