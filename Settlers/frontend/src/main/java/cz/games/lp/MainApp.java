package cz.games.lp;

import cz.games.lp.api.IManager;
import cz.games.lp.components.Card;
import cz.games.lp.components.FactionBoard;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainApp extends Application {

    private static IManager manager;

    private final Map<Sources, SourceStatusBlock> sourceStatusMap = new LinkedHashMap<>();
    private final HBox cardsInHand = new HBox();
    private final Map<CardType, HBox> factionCardPanes = new LinkedHashMap<>();
    private final Map<CardType, HBox> commonCardPanes = new LinkedHashMap<>();
    private final Pane frontPane = new Pane();

    private double width;
    private double height;
    private double cardWidth;
    private double cardHeight;
    private List<Card> commonCards;
    private List<Card> factionCards;
    private StackPane factionCardsStack;
    private StackPane commonCardsStack;
    private FactionBoard factionBoard;
    private ScoreBoard scoreBoard;
    private Dialog<SelectedFaction> choiceFactionDialog;
    private RoundPhases roundPhases;
    private Actions actions;

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
        ActionMessenger actionMessenger = new ActionMessenger(manager, cardsInHand, sourceStatusMap, cardWidth, cardHeight, frontPane, factionCards, commonCards);
        actions = new Actions(actionMessenger);
        BorderPane mainUIPane = new BorderPane();
        fillRegion(mainUIPane);
        stage.setScene(new Scene(new StackPane(mainUIPane, frontPane)));
        frontPane.setMouseTransparent(true);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle(manager.getTitle());
        stage.setResizable(false);
        stage.show();

        mainUIPane.setTop(createSourceStatusPanel());
        mainUIPane.setCenter(createCenterPane());
        createCPUPane(mainUIPane);

        choiceFactionDialog = createChoiceFactionDialog(width, height);
        newGame();
    }

    private Dialog<SelectedFaction> createChoiceFactionDialog(double width, double height) {
        Dialog<SelectedFaction> dialog = new Dialog<>();
        dialog.setWidth(width * 0.164835);
        dialog.setHeight(height * 0.306122);
        dialog.setResizable(false);
        dialog.setTitle("Výběr frakce");
        dialog.setHeaderText(dialog.getTitle());
        HBox contentPane = new HBox();
        dialog.getDialogPane().setContent(contentPane);
        ToggleGroup faction = createChoicePane(contentPane, "Barbaři", "Japonci", "Římané", "Egypťané");
        ToggleGroup sex = createChoicePane(contentPane, "Žena", "Muž");
        ButtonType type = new ButtonType("Začít novou hru", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setResultConverter(button -> new SelectedFaction(
                        ((RadioButton) faction.getSelectedToggle()).getText(),
                        "Žena".equals(((RadioButton) sex.getSelectedToggle()).getText()) ? Sex.FEMALE : Sex.MALE
                )
        );
        return dialog;
    }

    private ToggleGroup createChoicePane(HBox contentPane, String... titles) {
        VBox pane = new VBox();
        ToggleGroup group = new ToggleGroup();
        for (String title : titles) {
            RadioButton radioButton = new RadioButton(title);
            radioButton.setFont(new Font(26));
            radioButton.setToggleGroup(group);
            pane.getChildren().add(radioButton);
        }
        group.selectToggle(group.getToggles().getFirst());
        contentPane.getChildren().add(pane);
        return group;
    }

    private void fillRegion(Region region) {
        region.setStyle("-fx-background: #078d6f; -fx-background-color: #078d6f");
    }

    private void newGame() {
        // 0 (remove everything)
        clearTable();
        // 1 (set 1.st round)
        scoreBoard.setRound(1);
        // 2 (prepare common cards)
        commonCards = manager.prepareCards(cardWidth, cardHeight);
        Card commonCard = new Card("common", cardWidth, cardHeight);
        commonCardsStack.getChildren().addAll(commonCard);
        // 3 (faction choice)
        choiceFactionDialog.showAndWait().ifPresent(chosenFaction -> manager.setFactionAndSex(chosenFaction.faction(), chosenFaction.sex()));
        factionBoard.setImage(manager.getFactionBoard());
        factionCards = manager.prepareCards(cardWidth, cardHeight, manager.getFaction(), 30);
        Card factionCard = new Card(manager.getFaction(), cardWidth, cardHeight);
        factionCardsStack.getChildren().addAll(factionCard);
        scoreBoard.setFactionToken(manager.getFaction(), false);
        actions.updateFactionCardForEffect(factionCard, commonCard);
        // 4 (first 4 cards)
        actions.addSettler(8);
        actions.addCardIntoHand(factionCards, factionCardsStack);
        actions.addCardIntoHand(factionCards, factionCardsStack);
        actions.addCardIntoHand(commonCards, commonCardsStack);
        actions.addCardIntoHand(commonCards, commonCardsStack);
    }

    private void clearTable() {
        sourceStatusMap.forEach((source, sourceStatusBlock) -> sourceStatusBlock.setValue(0));
        cardsInHand.getChildren().clear();
        factionCardPanes.forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
        commonCardPanes.forEach((cardType, factionCardPane) -> factionCardPane.getChildren().clear());
        roundPhases.reset();
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

        HBox factionPane = new HBox();
        factionPane.setPrefHeight(height * 0.612);
        initFactionPane(factionPane);

        return new VBox(incomingPane, factionPane);
    }

    private void initIncomingPane(HBox incomingPane) {
        scoreBoard = new ScoreBoard(width * 0.278, height * 0.2663);

        roundPhases = new RoundPhases(width * 0.0615, manager);
        roundPhases.getButtons().getFirst().setOnAction(evt -> actions.activateLookupPhase());

        createFactionCardStack();

        VBox dealPane = new VBox();
        dealPane.setPrefWidth(cardWidth);

        createCommonCardStack();

        Region space2 = new Region();
        space2.setPrefWidth(width * 0.0204);

        ScrollPane cardsInHandScrollPane = createCardsInHandPane();

        incomingPane.getChildren().addAll(
                scoreBoard,
                roundPhases,
                factionCardsStack,
                dealPane,
                commonCardsStack,
                space2,
                cardsInHandScrollPane
        );
    }

    private ScrollPane createCardsInHandPane() {
        ScrollPane cardsInHandScrollPane = new ScrollPane(cardsInHand);
        fillRegion(cardsInHandScrollPane);
        cardsInHandScrollPane.setPrefWidth(width * 0.3285);
        return cardsInHandScrollPane;
    }

    private void createCommonCardStack() {
        commonCardsStack = new StackPane();
        commonCardsStack.setPrefWidth(cardWidth);
    }

    private void createFactionCardStack() {
        factionCardsStack = new StackPane();
        factionCardsStack.setPrefWidth(cardWidth);
    }

    private void initFactionPane(HBox factionPane) {
        VBox factionSide = setSides(factionCardPanes, NodeOrientation.RIGHT_TO_LEFT);
        VBox commonSide = setSides(commonCardPanes, NodeOrientation.LEFT_TO_RIGHT);
        factionBoard = new FactionBoard(cardWidth, 3 * cardHeight);
        factionPane.getChildren().addAll(factionSide, factionBoard, commonSide);
    }

    private VBox setSides(Map<CardType, HBox> cardPanes, NodeOrientation orientation) {
        VBox sideVBox = new VBox();
        sideVBox.setPrefWidth(width * 0.41365);

        for (CardType cardType : manager.getCardTypes()) {
            HBox boxForCards = new HBox();
            boxForCards.setNodeOrientation(orientation);
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

    private record SelectedFaction(String faction, Sex sex) {
    }
}
