package cz.games.lp;

import cz.games.lp.actions.ActionManager;
import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.components.Deals;
import cz.games.lp.components.FactionBoard;
import cz.games.lp.components.RoundPhases;
import cz.games.lp.components.ScoreBoard;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Phases;
import cz.games.lp.enums.Sex;
import cz.games.lp.panes.ChoiceDialog;
import cz.games.lp.panes.PaneModel;
import cz.games.lp.panes.UIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    private static IManager manager;

    private final PaneModel model = new PaneModel();

    private ChoiceDialog choiceFactionDialog;

    public static void run(IManager manager) {
        MainApp.manager = manager;
        launch();
    }

    @Override
    public void start(Stage stage) {
        initModel();
        choiceFactionDialog = new ChoiceDialog(model);
        StackPane mainPane = new StackPane(new UIPane(model), createFrontPane());

        stage.setScene(new Scene(mainPane));
        stage.setWidth(model.getManager().getWidth());
        stage.setHeight(model.getManager().getHeight());
        stage.setTitle(manager.getTitle());
        stage.setResizable(false);
        stage.show();

        setPhases();

        mockData();
//        newGame();
    }

    private void initModel() {
        model.setManager(manager);
        model.setCardSpeed(Duration.millis(manager.getCardSpeed()));
        model.setStyle("-fx-background: #078d6f; -fx-background-color: #078d6f");
        model.setHeaderStyle("-fx-background-color: #6ae7ba");
        model.setFactionBoard(new FactionBoard(model));
        model.setScoreBoard(new ScoreBoard(model));
        model.setRoundPhases(new RoundPhases(model));
        model.setDeals(new Deals(model));
        model.setActionManager(new ActionManager(model));
    }

    private Pane createFrontPane() {
        Pane frontPane = new Pane();
        frontPane.setMouseTransparent(true);
        model.setFrontPane(frontPane);
        return frontPane;
    }

    private void newGame() {
        // 0 (remove everything)
        clearTable();
        // 1 (set 1.st round
        model.getScoreBoard().setRound(1);
        // 2 (prepare common cards)
        model.setCommonCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), "commons", model.getCardSizeMap().get(CardType.COMMON)));
        model.setCommonCard(new Card("common", model));
        model.getCommonCardsStack().getChildren().add(model.getCommonCard());
        // 3 (faction choice)
        choiceFactionDialog.showAndWait().ifPresent(chosenFaction -> manager.setFactionAndSex(chosenFaction.faction(), chosenFaction.sex()));
        model.getFactionBoard().setImage(manager.getFactionBoard());
        model.setFactionCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), manager.getFaction(), model.getCardSizeMap().get(CardType.FACTION)));
        model.setFactionCard(new Card(manager.getFaction(), model));
        model.getFactionCardsStack().getChildren().add(model.getFactionCard());
        model.getScoreBoard().setFactionToken(manager.getFaction(), false);
        // 4 (first 4 cards)
        model.getActionManager().prepareFirstFourCards();
    }

    private void clearTable() {
        model.getCardSizeMap().put(CardType.COMMON, model.getManager().getCommonCardCount());
        model.getCardSizeMap().put(CardType.FACTION, model.getManager().getFactionCardCount());
        model.getSources().forEach((source, sourceStatusBlock) -> sourceStatusBlock.setValue(0));
        model.getCardsInHand().getChildren().clear();
        model.getBuiltFactionCards().forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
        model.getBuiltCommonCards().forEach((cardType, commonCardPane) -> commonCardPane.getChildren().clear());
        model.getRoundPhases().reset();
    }

    private void setPhases() {
        model.getRoundPhases().getButtons().forEach((phase, button) -> {
            switch (phase) {
                case Phases.LOOKOUT -> button.setOnAction(evt -> lookoutPhase());
                case Phases.PRODUCTION -> button.setOnAction(evt -> productionPhase());
                case Phases.ACTION -> button.setOnAction(evt -> actionPhase());
                case Phases.PASS_ACTION -> button.setOnAction(evt -> passActions());
                case Phases.CLEANUP -> button.setOnAction(evt -> cleanUpPhase());
                default -> throw new IllegalStateException("unknown phase");
            }
        });
    }

    private void lookoutPhase() {
        if (model.getActionManager() == null || !model.getRoundPhases().getCurrentPhase().equals(Phases.LOOKOUT)) {
            return;
        }
        model.getActionManager().proceedLookout(() -> model.getRoundPhases().switchButton(Phases.PRODUCTION));
    }

    private void productionPhase() {
        if (model.getActionManager() == null || !model.getRoundPhases().getCurrentPhase().equals(Phases.PRODUCTION)) {
            return;
        }
        model.getActionManager().proceedProduction(() -> model.getRoundPhases().switchButton(Phases.ACTION));
    }

    private void mockData() {
        model.getCardSizeMap().put(CardType.COMMON, model.getManager().getCommonCardCount());
        model.setCommonCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), "commons", model.getCardSizeMap().get(CardType.COMMON)));
        model.setCommonCard(new Card("common", model));
        model.getCommonCardsStack().getChildren().add(model.getCommonCard());
        model.getCardSizeMap().put(CardType.FACTION, model.getManager().getFactionCardCount());
        manager.setFactionAndSex("Egypťané", Sex.MALE);
        model.getRoundPhases().setCurrentPhase(Phases.PRODUCTION);
        model.getFactionBoard().setImage(manager.getFactionBoard());
        model.getScoreBoard().setFactionToken(manager.getFaction(), false);
        model.setFactionCards(manager.prepareCards(model.getManager().getCardWidth(), model.getManager().getCardHeight(), manager.getFaction(), model.getCardSizeMap().get(CardType.FACTION)));
        model.setFactionCard(new Card(manager.getFaction(), model));
        model.getFactionCardsStack().getChildren().add(model.getFactionCard());
        String[] cards = new String[]{"egy004"};
        for (String cardName : cards) {
            Card card = new Card("egypt/", cardName, model);
            model.getBuiltFactionCards().get(CardType.PRODUCTION).getChildren().add(card);
            model.getManager().fillCardWithData(card);
            card.setSamurai(true);
        }
    }

    private void actionPhase() {
        //TODO - prepared method
    }

    private void passActions() {
        //TODO - prepared method
    }

    private void cleanUpPhase() {
        //TODO - prepared method
    }
}
