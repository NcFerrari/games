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


    private final Sources[] sources = {Sources.SETTLER, Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.COIN, Sources.SWORD, Sources.SHIELD};
    private final CardType[] cardTypes = {CardType.PRODUCTION, CardType.PROPERTIES, CardType.ACTION};
    private Fraction selectedFraction;
    private Sex selectedSex;

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
        if (Fraction.EGYPT.equals(selectedFraction)) {
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
    public String getFraction() {
        return selectedFraction.getFractionTitle();
    }

    @Override
    public String getFractionBoard() {
        if (Sex.MALE.equals(selectedSex)) {
            return selectedFraction.getMBoard();
        }
        return selectedFraction.getFBoard();
    }

    @Override
    public void setFractionAndSex(String fraction, Sex sex) {
        selectedFraction = switch (fraction) {
            case "Barbaři" -> Fraction.BARBARIAN;
            case "Japonci" -> Fraction.JAPAN;
            case "Římané" -> Fraction.ROMAN;
            case "Egypťané" -> Fraction.EGYPT;
            default -> null;
        };
        selectedSex = sex;
    }

    @Override
    public List<Card> prepareCards(double cardWidth, double cardHeight) {
        return prepareCards(cardWidth, cardHeight, "commons", 84);
    }

    @Override
    public List<Card> prepareCards(double cardWidth, double cardHeight, String fraction, int cardCount) {
        String imagePrefix = fraction + "/" + fraction.substring(0, 3) + "0";
        List<Card> cards = IntStream
                .range(1, cardCount + 1)
                .mapToObj(i -> {
                    String value = "" + i;
                    if (i < 10) {
                        value = "0" + i;
                    }
                    return new Card(imagePrefix + value, cardWidth, cardHeight);
                })
                .collect(Collectors.toList());
        Collections.shuffle(cards);
        return cards;
    }
}
