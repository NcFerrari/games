package lp;

import cz.games.lp.BackendManager;
import cz.games.lp.MainApp;
import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Faction;
import cz.games.lp.enums.Sex;
import cz.games.lp.enums.Sources;
import cz.games.lp.service.LoggerService;
import cz.games.lp.service_impl.LoggerServiceImpl;
import lombok.Getter;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Manager implements IManager {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private static final int CARD_SPEED = 400;

    private final LoggerService logger = LoggerServiceImpl.getInstance(BackendManager.class);
    private final Sources[] sources = {Sources.SETTLER, Sources.WOOD, Sources.STONE, Sources.FOOD, Sources.COIN, Sources.SWORD, Sources.SHIELD};
    private final CardType[] cardTypes = {CardType.PRODUCTION, CardType.PROPERTIES, CardType.ACTION};
    private final Toolkit tool = Toolkit.getDefaultToolkit();
    private final Dimension src = tool.getScreenSize();
    private final BackendManager backendManager = new BackendManager();
    @Getter
    private final double width = src.getWidth() - 100;
    @Getter
    private final double height = src.getHeight() - 100;
    @Getter
    private final double cardWidth = width * 0.078;
    @Getter
    private final double cardHeight = height * 0.204;
    @Getter
    private final double scoreXMove = width * 0.0219759;
    @Getter
    private final double scoreYMove = height * 0.051015091;

    private Faction selectedFaction;
    private Sex selectedSex;

    private void start() {
        backendManager.loadCardData();
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
        if (Faction.EGYPT.equals(selectedFaction)) {
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
    public String getChooserDialogTitle() {
        return "Výběr frakce";
    }

    @Override
    public String[] getFactionArray() {
        return new String[]{"Barbaři", "Japonci", "Římané", "Egypťané"};
    }

    @Override
    public String[] getSexArray() {
        return new String[]{"Žena", "Muž"};
    }

    @Override
    public String getStartNewGameButtonTtitle() {
        return "Začít novou hru";
    }

    @Override
    public String getFaction() {
        return selectedFaction.getFactionTitle();
    }

    @Override
    public String getFactionBoard() {
        if (Sex.MALE.equals(selectedSex)) {
            return selectedFaction.getMBoard();
        }
        return selectedFaction.getFBoard();
    }

    @Override
    public void setFactionAndSex(String faction, Sex sex) {
        selectedFaction = switch (faction) {
            case "Barbaři" -> Faction.BARBARIAN;
            case "Japonci" -> Faction.JAPAN;
            case "Římané" -> Faction.ROMAN;
            case "Egypťané" -> Faction.EGYPT;
            default -> null;
        };
        selectedSex = sex;
    }

    @Override
    public List<Card> prepareCards(double cardWidth, double cardHeight, String faction, int cardCount) {
        String cardName = faction.substring(0, 3) + "0";
        List<Card> cards = IntStream.range(1, cardCount + 1).mapToObj(i -> {
            String value = "" + i;
            if (i < 10) {
                value = "0" + i;
            }
            return new Card(faction, cardName + value, cardWidth, cardHeight);
        }).collect(Collectors.toList());
        Collections.shuffle(cards);
        return cards;
    }

    @Override
    public Integer getCommonCardCount() {
        return COMMON_CARD_COUNT;
    }

    @Override
    public Integer getFactionCardCount() {
        return FACTION_CARD_COUNT;
    }

    @Override
    public double getCardSpeed() {
        return CARD_SPEED;
    }

    @Override
    public Card fillCardWithData(String cardId) {
        if (backendManager.getCardDtoMap().get(cardId) != null) {
            logger.getLogger().info("{}:{}", cardId, backendManager.getCardDtoMap().get(cardId).getCardName());
        }
        return null;
    }
}
