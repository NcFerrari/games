package cz.games.lp;

import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.components.Deals;
import cz.games.lp.components.FactionBoard;
import cz.games.lp.components.RoundPhases;
import cz.games.lp.components.ScoreBoard;
import cz.games.lp.panes.ChoiceDialog;
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
        model.setScoreXMove(model.getWidth() * 0.0219759);
        model.setScoreYMove(model.getHeight() * 0.051015091);
        model.setStyle("-fx-background: #078d6f; -fx-background-color: #078d6f");
        model.setHeaderStyle("-fx-background-color: #6ae7ba");
        model.setSources(new LinkedHashMap<>());
        model.setBuiltFactionCards(new LinkedHashMap<>());
        model.setBuiltCommonCards(new LinkedHashMap<>());
        model.setFactionBoard(new FactionBoard(model));
        model.setScoreBoard(new ScoreBoard(model));
        model.setRoundPhases(new RoundPhases(model));
        model.setDeals(new Deals(model));
        model.setCardsInHand(new HBox());
        model.setFactionCardsStack(new StackPane());
        model.setCommonCardsStack(new StackPane());
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
        model.setCommonCards(manager.prepareCards(model.getCardWidth(), model.getCardHeight()));
        model.setCommonCard(new Card("common", model));
        model.getCommonCardsStack().getChildren().add(model.getCommonCard());
        // 3 (faction choice)
//        choiceFactionDialog.showAndWait().ifPresent(chosenFaction -> manager.setFactionAndSex(chosenFaction.faction(), chosenFaction.sex()));
        manager.setFactionAndSex("Barbaři", cz.games.lp.enums.Sex.MALE);
        model.getFactionBoard().setImage(manager.getFactionBoard());
        model.setFactionCards(manager.prepareCards(model.getCardWidth(), model.getCardHeight(), manager.getFaction(), 5));
        model.setFactionCard(new Card(manager.getFaction(), model));
        model.getFactionCardsStack().getChildren().add(model.getFactionCard());
        model.getScoreBoard().setFactionToken(manager.getFaction(), false);
        model.setActions(new Actions(model));
        // 4 (first 4 cards)
        model.getActions().prepareFirstFourCards();
    }

    private void clearTable() {
        model.getSources().forEach((source, sourceStatusBlock) -> sourceStatusBlock.setValue(0));
        model.getCardsInHand().getChildren().clear();
        model.getBuiltFactionCards().forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
        model.getBuiltCommonCards().forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
        model.getRoundPhases().reset();
    }
}
