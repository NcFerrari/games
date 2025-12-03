package cz.games.lp.frontend;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.api.IManager;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import cz.games.lp.frontend.panes.UIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
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
        initModel();
        StackPane mainPane = new StackPane(new UIPane(model), createFrontPane());
        stage.setScene(new Scene(mainPane));
        stage.setWidth(model.getUIConfig().getWidth());
        stage.setHeight(model.getUIConfig().getHeight());
        stage.setTitle(Texts.TITLE.get());
        stage.setResizable(false);
        stage.show();

//        setPhases();

//        mockData();
        newGame();
    }

    private void initModel() {
        model.setGameData(manager.getGameData());
//        model.setCardSpeed(Duration.millis(manager.getAnimationSpeed()));
//        model.setStyle("-fx-background: #078d6f; -fx-background-color: #078d6f");
//        model.setHeaderStyle("-fx-background-color: #6ae7ba");
//        model.setFactionBoard(new FactionBoard(model));
//        model.setScoreBoard(new ScoreBoard(model));
//        model.setActionManager(new ActionManager(model));
    }

    private Pane createFrontPane() {
        Pane frontPane = new Pane();
        frontPane.setMouseTransparent(true);
        model.setFrontPane(frontPane);
        return frontPane;
    }

    private void newGame() {
        clearTable();
        model.getGameData().newGame();

        model.getGameData().setSelectedFaction(Factions.BARBARIAN_M);
        model.getScoreBoard().createFactionToken();
        model.getSourcePane().generateNewSources();
        // 2 (prepare common cards)
//        model.setCommonCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), "commons", model.getCardSizeMap().get(CardType.COMMON)));
//        model.setCommonCard(new Card("common", model));
//        model.getCommonCardsStack().getChildren().add(model.getCommonCard());
//        // 3 (faction choice)
//        choiceFactionDialog.showAndWait().ifPresent(chosenFaction -> manager.setFactionAndSex(chosenFaction.faction(), chosenFaction.sex()));
//        model.getFactionBoard().setImage(manager.getFactionBoard());
//        model.setFactionCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), manager.getFaction(), model.getCardSizeMap().get(CardType.FACTION)));
//        model.setFactionCard(new Card(manager.getFaction(), model));
//        model.getFactionCardsStack().getChildren().add(model.getFactionCard());
//        model.getScoreBoard().setFactionToken(manager.getFaction(), false);
        // 4 (first 4 cards)
//        model.getActionManager().prepareFirstFourCards();
    }

    private void clearTable() {
//        model.getCardSizeMap().put(CardType.COMMON, model.getManager().getCommonCardCount());
//        model.getCardSizeMap().put(CardType.FACTION, model.getManager().getFactionCardCount());
//        model.getSources().forEach((source, sourceStatusBlock) -> sourceStatusBlock.setValue(0));
//        model.getCardsInHand().getChildren().clear();
//        model.getBuiltFactionCards().forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
//        model.getBuiltCommonCards().forEach((cardType, commonCardPane) -> commonCardPane.getChildren().clear());
//        model.getRoundPhases().reset();
    }

    private void setPhases() {
//        model.getRoundPhases().getButtons().forEach((phase, button) -> {
//            switch (phase) {
//                case Phases.LOOKOUT -> button.setOnAction(evt -> lookoutPhase());
//                case Phases.PRODUCTION -> button.setOnAction(evt -> productionPhase());
//                case Phases.ACTION -> button.setOnAction(evt -> actionPhase());
//                case Phases.PASS_ACTION -> button.setOnAction(evt -> passActions());
//                case Phases.CLEANUP -> button.setOnAction(evt -> cleanUpPhase());
//                default -> throw new IllegalStateException("unknown phase");
//            }
//        });
    }

    private void lookoutPhase() {
//        if (model.getActionManager() == null || !model.getRoundPhases().getCurrentPhase().equals(Phases.LOOKOUT)) {
//            return;
//        }
//        model.getActionManager().proceedLookout(() -> model.getRoundPhases().switchButton(Phases.PRODUCTION));
    }

    private void productionPhase() {
//        if (model.getActionManager() == null || !model.getRoundPhases().getCurrentPhase().equals(Phases.PRODUCTION)) {
//            return;
//        }
//        model.getActionManager().proceedProduction(() -> model.getRoundPhases().switchButton(Phases.ACTION));
    }

    private void mockData() {
//        model.getCardSizeMap().put(CardType.COMMON, model.getManager().getCommonCardCount());
//        model.setCommonCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), "commons", model.getCardSizeMap().get(CardType.COMMON)));
//        model.setCommonCard(new Card("common", model));
//        model.getCommonCardsStack().getChildren().add(model.getCommonCard());
//        model.getCardSizeMap().put(CardType.FACTION, model.getManager().getFactionCardCount());
//        manager.setFactionAndSex("Římané", Sex.MALE);
//        model.getRoundPhases().setCurrentPhase(Phases.PRODUCTION);
//        model.getFactionBoard().setImage(manager.getFactionBoard());
//        model.getScoreBoard().setFactionToken(manager.getFaction(), false);
//        model.setFactionCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), manager.getFaction(), model.getCardSizeMap().get(CardType.FACTION)));
//        model.setFactionCard(new Card(manager.getFaction(), model));
//        model.getFactionCardsStack().getChildren().add(model.getFactionCard());
//        String[] cards = new String[]{"rom011"};
//        for (String cardName : cards) {
//            Card card = new Card("roman/", cardName, model);
//            model.getBuiltFactionCards().get(CardType.PRODUCTION).getChildren().add(card);
//            model.getManager().fillCardWithData(card);
//            card.setSamurai(true);
//        }
    }
}
