package cz.games.lp;

import cz.games.lp.api.IManager;
import cz.games.lp.components.Deals;
import cz.games.lp.components.FactionBoard;
import cz.games.lp.components.RoundPhases;
import cz.games.lp.components.ScoreBoard;
import cz.games.lp.panes.PaneModel;
import cz.games.lp.panes.UIPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;

public class MainApp extends Application {

    private static IManager manager;
    private final PaneModel model = new PaneModel();

    public static void run(IManager manager) {
        MainApp.manager = manager;
        launch();
    }

    @Override
    public void start(Stage stage) {
        initModel();
        StackPane mainPane = new StackPane(new UIPane(model), createFrontPane());

        stage.setScene(new Scene(mainPane));
        stage.setWidth(model.getWidth());
        stage.setHeight(model.getHeight());
        stage.setTitle(manager.getTitle());
        stage.setResizable(false);
        stage.show();

        newGame();
    }

    private void initModel() {
        model.setManager(manager);
        model.setWidth(Screen.getPrimary().getBounds().getWidth() - 100);
        model.setHeight(Screen.getPrimary().getBounds().getHeight() - 100);
        model.setCardWidth(model.getWidth() * 0.078);
        model.setCardHeight(model.getHeight() * 0.204);
        model.setScoreXMove(model.getWidth() * 0.07905);
        model.setScoreYMove(model.getHeight() * 0.19157);
        model.setStyle("-fx-background: #078d6f; -fx-background-color: #078d6f");
        model.setHeaderStyle("-fx-background-color: #6ae7ba");
        model.setSources(new LinkedHashMap<>());
        model.setFactionCards(new LinkedHashMap<>());
        model.setCommonCards(new LinkedHashMap<>());
        model.setFactionBoard(new FactionBoard(model));
        model.setScoreBoard(new ScoreBoard(model));
        model.setRoundPhases(new RoundPhases(model));
        model.setDeals(new Deals(model));
        model.setCardsInHand(new HBox());
    }

    private Pane createFrontPane() {
        Pane frontPane = new Pane();
        frontPane.setMouseTransparent(true);
        model.setFrontPane(frontPane);
        return frontPane;
    }

    private void newGame() {
//        // 0 (remove everything)
//        clearTable();
//        // 1 (set 1.st round)
//        scoreBoard.setRound(1);
//        // 2 (prepare common cards)
//        newCommonCards = manager.prepareCards(model.getCardWidth(), model.getCardHeight());
//        Card commonCard = new Card("common", model.getCardWidth(), model.getCardHeight());
//        commonCardsStack.getChildren().addAll(commonCard);
//        // 3 (faction choice)
//        choiceFactionDialog.showAndWait().ifPresent(chosenFaction -> manager.setFactionAndSex(chosenFaction.faction(), chosenFaction.sex()));
//        factionBoard.setImage(manager.getFactionBoard());
//        newFactionCards = manager.prepareCards(model.getCardWidth(), model.getCardHeight(), manager.getFaction(), 30);
//        Card factionCard = new Card(manager.getFaction(), model.getCardWidth(), model.getCardHeight());
//        factionCardsStack.getChildren().addAll(factionCard);
//        scoreBoard.setFactionToken(manager.getFaction(), false);
//        actions.updateFactionCardForEffect(factionCard, commonCard);
        // 4 (first 4 cards)
//        actions.addSettler(8);
//        actions.addCardIntoHand(newFactionCards, factionCardsStack);
//        actions.addCardIntoHand(newFactionCards, factionCardsStack);
//        actions.addCardIntoHand(newCommonCards, commonCardsStack);
//        actions.addCardIntoHand(newCommonCards, commonCardsStack);
    }

    private void clearTable() {
//        model.getSources().forEach((source, sourceStatusBlock) -> sourceStatusBlock.setValue(0));
//        cardsInHand.getChildren().clear();
//        factionCards.forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
//        commonCards.forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
//        roundPhases.reset();
    }
}
