package cz.games.lp.frontend;

import cz.games.lp.common.enums.CardTypes;
import cz.games.lp.common.enums.Factions;
import cz.games.lp.common.enums.Phases;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.components.transition_components.FactionToken;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import cz.games.lp.frontend.panes.FrontPane;
import cz.games.lp.frontend.panes.UIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static IManager manager;

    private final CommonModel model = new CommonModel();

    public static void run(IManager manager) {
        MainApp.manager = manager;
        launch();
    }

    @Override
    public void start(Stage stage) {
        StackPane stackPane = new StackPane(new UIPane(model), new FrontPane(model));
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);
        stage.setWidth(model.getUIConfig().getWidth());
        stage.setHeight(model.getUIConfig().getHeight());
        stage.setTitle(Texts.TITLE.get());
        stage.setResizable(false);
        stage.show();

        initModel();

        newGame();
    }

    private void initModel() {
        model.setManager(manager);
        model.setGameData(manager.getGameData());
    }

    private void newGame() {
        clearAll();

        model.getGameData().newGame();
        model.getRoundPhases().reset();
        model.getCommonDeck().createCard(Texts.COMMON.get());

//        model.getFactionChoiceDialog().showAndWait();
//        initFactionComponents(model.getFactionChoiceDialog().getResult());
//        model.getActionManager().prepareFirstFourCards();

        mockData();
    }

    private void initFactionComponents(Factions factions) {
        model.getGameData().setSelectedFaction(factions);
        model.getRoundPhases().reset();
        model.getFactionBoard().setImage(factions);
        model.getSourcePane().generateNewSources();
        model.getFactionDeck().createCard(factions.getFactionCardPath());
        model.setFactionToken(new FactionToken(model));
    }

    private void mockData() {
        model.getGameData().setCurrentPhase(Phases.PRODUCTION);
        model.getManager().getGameData().setSelectedFaction(Factions.ROMAN_F);
        initFactionComponents(Factions.ROMAN_F);
        int[] egypt = new int[]{4};
        for (Integer c : egypt) {
            String cardId = "egy" + (c < 10 ? "00" : "0") + c;
            Card card = new Card("egypt/" + cardId, cardId, model);
            ((HBox) model.getFactionCards().get(CardTypes.PRODUCTION).getContent()).getChildren().add(card);
        }
//        int[] commmons = new int[]{7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 23, 24, 25, 26, 27, 28, 29, 62, 63, 64, 65, 66, 67, 68};
//        for (Integer c : commmons) {
//            String cardId = "com" + (c < 10 ? "00" : "0") + c;
//            Card card = new Card("common/" + cardId, cardId, model);
//            ((HBox) model.getCommonCards().get(CardTypes.PRODUCTION).getContent()).getChildren().add(card);
//        }
//        int[] deals = new int[]{1, 2, 3};
//        for (Integer c : deals) {
//            String cardId = "bar00" + c;
//            Card card = new Card("barbarian/" + cardId, cardId, model);
//            model.getDeals().makeADeal(card);
//        }
//        int[] deals2 = new int[]{11, 15, 5, 13, 10, 12, 23, 24, 2, 3, 9, 29, 8, 26};
//        for (Integer c : deals2) {
//            String cardId = "jap" + (c < 10 ? "00" : "0") + c;
//            Card card = new Card("japan/" + cardId, cardId, model);
//            model.getDeals().makeADeal(card);
//        }
    }

    private void clearAll() {
        model.getCardsInHand().getCards().clear();
        model.getFactionCards().forEach((cardTypes, cards) -> ((HBox) cards.getContent()).getChildren().clear());
        model.getDeals().clear();
        model.getCommonCards().forEach((cardTypes, cards) -> ((HBox) cards.getContent()).getChildren().clear());
    }
}
