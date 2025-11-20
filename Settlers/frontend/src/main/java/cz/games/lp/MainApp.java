package cz.games.lp;

import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.components.FractionBoard;
import cz.games.lp.components.RoundPhases;
import cz.games.lp.components.ScoreBoard;
import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.enums.CardType;
import cz.games.lp.enums.Sex;
import cz.games.lp.enums.Sources;
import javafx.application.Application;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainApp extends Application {

    private static IManager manager;

    private final Map<Sources, SourceStatusBlock> sourceStatusMap = new LinkedHashMap<>();
    private final HBox cardsInHand = new HBox();
    private final Map<CardType, HBox> fractionCardPanes = new LinkedHashMap<>();
    private final Map<CardType, HBox> commonCardPanes = new LinkedHashMap<>();
    private final Actions actions = new Actions(cardsInHand, sourceStatusMap);

    private double width;
    private double height;
    private double cardWidth;
    private double cardHeight;
    private List<Card> commonCards;
    private StackPane fractionCardsStack;
    private FractionBoard fractionBoard;
    private ScoreBoard scoreBoard;

    public static void run(IManager manager) {
        MainApp.manager = manager;
        launch();
    }

    @Override
    public void start(Stage stage) {
        width = Screen.getPrimary().getBounds().getWidth() - 100;
        height = Screen.getPrimary().getBounds().getHeight() - 100;
        cardWidth = width * 0.078;
        cardHeight = height * 0.204;
        commonCards = manager.prepareCards(cardWidth, cardHeight);
        BorderPane mainPane = new BorderPane();
        fillRegion(mainPane);
        stage.setScene(new Scene(mainPane));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle(manager.getTitle());
        stage.show();

        mainPane.setTop(createSourceStatusPanel());
        mainPane.setCenter(createCenterPane());
        createCPUPane(mainPane);

        newGame();
    }

    private void fillRegion(Region region) {
        region.setStyle("-fx-background: #078d6f; -fx-background-color: #078d6f");
    }

    private void newGame() {
        fractionCardsStack.getChildren().addAll(new Card(manager.getFraction(), cardWidth, cardHeight));
        fractionBoard.setImage(manager.getFractionBoard(Sex.MALE));
        scoreBoard.setFractionToken(manager.getFraction(), false);
        actions.addSettler(50);
    }

    private FlowPane createSourceStatusPanel() {
        double sourceWidth = width / 9.1;
        double sourceHeight = height / 12.25;
        for (Sources source : manager.getSources()) {
            sourceStatusMap.put(source, new SourceStatusBlock(source, sourceWidth, sourceHeight));
        }
        FlowPane sourceStatusPanel = new FlowPane();
        sourceStatusPanel.setAlignment(Pos.CENTER);
        sourceStatusPanel.setStyle("-fx-background-color: #6ae7ba");

        List<SourceStatusBlock> sourceStatusBlocks = sourceStatusMap
                .values()
                .stream()
                .toList();
        sourceStatusPanel.getChildren().addAll(sourceStatusBlocks);
        return sourceStatusPanel;
    }

    private VBox createCenterPane() {
        HBox incomingPane = new HBox();
        VBox.setVgrow(incomingPane, Priority.ALWAYS);
        initIncomingPane(incomingPane);

        HBox fractionPane = new HBox();
        fractionPane.setPrefHeight(height * 0.612);
        initFractionPane(fractionPane);

        return new VBox(incomingPane, fractionPane);
    }

    private void initIncomingPane(HBox incomingPane) {
        scoreBoard = new ScoreBoard(width * 0.278, height * 0.2663);
        scoreBoard.setFocusTraversable(true);

        RoundPhases roundPhases = new RoundPhases(width * 0.0615, scoreBoard);

        fractionCardsStack = new StackPane();
        fractionCardsStack.setPrefWidth(cardWidth);

        VBox dealPane = new VBox();
        dealPane.setPrefWidth(cardWidth);

        StackPane commonCardsStack = new StackPane();
        commonCardsStack.setPrefWidth(cardWidth);
        commonCardsStack.getChildren().addAll(new Card(manager.getCommonCardImage(), cardWidth, cardHeight));
        commonCardsStack.setOnMouseClicked(event -> actions.addCardIntoHand(commonCards, commonCardsStack));

        Region space2 = new Region();
        space2.setPrefWidth(width * 0.0204);

        ScrollPane cardsInHandScrollPane = new ScrollPane(cardsInHand);
        fillRegion(cardsInHandScrollPane);
        cardsInHandScrollPane.setPrefWidth(width * 0.3285);

        incomingPane.getChildren().addAll(
                scoreBoard,
                roundPhases,
                fractionCardsStack,
                dealPane,
                commonCardsStack,
                space2,
                cardsInHandScrollPane
        );
    }

    private void initFractionPane(HBox fractionPane) {
        VBox fractionSide = setSides(fractionCardPanes);
        VBox commonSide = setSides(commonCardPanes);
        fractionBoard = new FractionBoard(cardWidth, 3 * cardHeight);
        fractionPane.getChildren().addAll(fractionSide, fractionBoard, commonSide);
    }

    private VBox setSides(Map<CardType, HBox> cardPanes) {
        VBox sideVBox = new VBox();
        sideVBox.setPrefWidth(width * 0.41365);

        for (CardType cardType : manager.getCardTypes()) {
            HBox boxForCards = new HBox();
            boxForCards.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            boxForCards.setPrefHeight(cardHeight);
            cardPanes.put(cardType, boxForCards);

            ScrollPane scrollPane = new ScrollPane(boxForCards);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            fillRegion(scrollPane);
            sideVBox.getChildren().add(scrollPane);
        }
        return sideVBox;
    }

    private void createCPUPane(BorderPane mainPane) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(cardWidth, height * 0.878);
        mainPane.setRight(scrollPane);
    }
}
