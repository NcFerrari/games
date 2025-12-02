package cz.games.lp.main;

import cz.games.lp.backend.BackendManager;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.main.dto.CardDTO;
import cz.games.lp.main.enums.CardTypes;
import cz.games.lp.main.game.GameDataModel;
import cz.games.lp.main.mapping.CardMapper;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Manager implements IManager {

    private static final int FACTION_CARD_COUNT = 30;
    private static final int COMMON_CARD_COUNT = 84;
    private static final int ANIMATION_SPEED = 400;

    private final BackendManager backendManager = new BackendManager();
    private final GameDataModel gameData = new GameDataModel(backendManager);
    private final CardMapper cardMapper = Mappers.getMapper(CardMapper.class);
    private final CardTypes[] cardTypesForImperialSides = {CardTypes.PRODUCTION, CardTypes.PROPERTIES, CardTypes.ACTION};

    public static void main(String[] args) {
        new Manager().start();
    }

    private void start() {
        backendManager.log(getClass()).info("starting Application...");
        loadAndMapCardData();
        gameData.newGame(COMMON_CARD_COUNT, FACTION_CARD_COUNT);
//        MainApp.run(this);
    }

    private void loadAndMapCardData() {
        backendManager.log(getClass()).info("loadAndMapCardData");
        Map<String, CardDTO> cardMap = new HashMap<>();
        Thread loadingDataThread = new Thread(backendManager::loadAllCardData);
        Thread mapDataThread = new Thread(() -> {
            backendManager.log(getClass()).info("getCardMap");
            cardMapper.mapToCardDTO(backendManager.getCardMap(), cardMap);
        });
        loadingDataThread.start();
        try {
            loadingDataThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        mapDataThread.start();
    }

//    public CardType[] getCardTypesForImperialSides() {
//        return cardTypesForImperialSides;
//    }
//
//    public Sources[] getSourcesInOwnSupply() {
//        if (Faction.EGYPT.equals(selectedFaction)) {
//            Sources[] egyptianSources = new Sources[sourcesInOwnSupply.length + 1];
//            System.arraycopy(sourcesInOwnSupply, 0, egyptianSources, 0, sourcesInOwnSupply.length);
//            egyptianSources[egyptianSources.length - 1] = Sources.EGYPT_TOKEN;
//            return egyptianSources;
//        }
//        return sourcesInOwnSupply;
//    }

//    @Override
//    public String getTitle() {
//        return "Settlers";
//    }
//
//    @Override
//    public String getProduceChoiceDialog() {
//        return "Vyber si produkci";
//    }
//
//    @Override
//    public String getChooserDialogTitle() {
//        return "Výběr frakce";
//    }
//
//    @Override
//    public String[] getFactionArray() {
//        return new String[]{"Barbaři", "Japonci", "Římané", "Egypťané"};
//    }
//
//    @Override
//    public String[] getSexArray() {
//        return new String[]{"Žena", "Muž"};
//    }
//
//    @Override
//    public String getStartNewGameButtonTtitle() {
//        return "Začít novou hru";
//    }
//
//    @Override
//    public String getFaction() {
//        return selectedFaction.getFactionTitle();
//    }
//
//    @Override
//    public String getFactionBoard() {
//        if (Sex.MALE.equals(selectedSex)) {
//            return selectedFaction.getMBoard();
//        }
//        return selectedFaction.getFBoard();
//    }
//
//    @Override
//    public void setFactionAndSex(String faction, Sex sex) {
//        selectedFaction = switch (faction) {
//            case "Barbaři" -> Faction.BARBARIAN;
//            case "Japonci" -> Faction.JAPAN;
//            case "Římané" -> Faction.ROMAN;
//            case "Egypťané" -> Faction.EGYPT;
//            default -> null;
//        };
//        selectedSex = sex;
//    }

//    @Override
//    public List<Card> prepareCards(double cardWidth, double cardHeight, String faction, int cardCount) {
//        String cardName = faction.substring(0, 3) + "0";
//        List<Card> cards = IntStream.range(1, cardCount + 1).mapToObj(i -> {
//            String value = "" + i;
//            if (i < 10) {
//                value = "0" + i;
//            }
//            return new Card(faction, cardName + value, cardWidth, cardHeight);
//        }).collect(Collectors.toList());
//        Collections.shuffle(cards);
//        return cards;
//    }

//    @Override
//    public Integer getCommonCardCount() {
//        return COMMON_CARD_COUNT;
//    }
//
//    @Override
//    public Integer getFactionCardCount() {
//        return FACTION_CARD_COUNT;
//    }
//
//    @Override
//    public double getCardSpeed() {
//        return ANIMATION_SPEED;
//    }

//    @Override
//    public void fillCardWithData(Card card) {
//        mapper.updateCardFromLoaded(backendManager.getCardDtoMap().get(card.getCardId()), card);
//    }
}