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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
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
    private final Map<CardType, HBox> fractionCardPanes = new LinkedHashMap<>();
    private final Map<CardType, HBox> commonCardPanes = new LinkedHashMap<>();
    private final Actions actions = new Actions(cardsInHand, sourceStatusMap);

    private double width;
    private double height;
    private double cardWidth;
    private double cardHeight;
    private StackPane fractionCardsStack;
    private StackPane commonCardsStack;
    private FractionBoard fractionBoard;
    private ScoreBoard scoreBoard;
    private Dialog<SelectedFraction> choiceFractionDialog;

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

        BorderPane mainPane = new BorderPane();
        fillRegion(mainPane);
        stage.setScene(new Scene(mainPane));
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle(manager.getTitle());
        stage.setResizable(false);
        stage.show();

        mainPane.setTop(createSourceStatusPanel());
        mainPane.setCenter(createCenterPane());
        createCPUPane(mainPane);

        choiceFractionDialog = createChoiceFractionDialog(width, height);
        newGame();
    }

    private Dialog<SelectedFraction> createChoiceFractionDialog(double width, double height) {
        Dialog<SelectedFraction> dialog = new Dialog<>();
        dialog.setWidth(width * 0.164835);
        dialog.setHeight(height * 0.306122);
        dialog.setResizable(false);
        dialog.setTitle("Výběr frakce");
        dialog.setHeaderText(dialog.getTitle());
        HBox contentPane = new HBox();
        dialog.getDialogPane().setContent(contentPane);
        ToggleGroup fraction = createChoicePane(contentPane, "Barbaři", "Japonci", "Římané", "Egypťané");
        ToggleGroup sex = createChoicePane(contentPane, "Žena", "Muž");
        ButtonType type = new ButtonType("Začít novou hru", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.setResultConverter(button -> new SelectedFraction(
                        ((RadioButton) fraction.getSelectedToggle()).getText(),
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
        List<Card> commonCards = manager.prepareCards(cardWidth, cardHeight);
        commonCardsStack.getChildren().addAll(new Card("common", cardWidth, cardHeight));
        // 3 (fraction choice)
        choiceFractionDialog.showAndWait().ifPresent(chosenFraction -> manager.setFractionAndSex(chosenFraction.fraction(), chosenFraction.sex()));
        fractionBoard.setImage(manager.getFractionBoard());
        List<Card> fractionCards = manager.prepareCards(cardWidth, cardHeight, manager.getFraction(), 5);
        fractionCardsStack.getChildren().addAll(new Card(manager.getFraction(), cardWidth, cardHeight));
        scoreBoard.setFractionToken(manager.getFraction(), false);
        // 4 (first 4 cards)
        actions.addSettler(8);
        actions.addCardIntoHand(fractionCards, fractionCardsStack);
        actions.addCardIntoHand(fractionCards, fractionCardsStack);
        actions.addCardIntoHand(commonCards, commonCardsStack);
        actions.addCardIntoHand(commonCards, commonCardsStack);
    }

    private void clearTable() {
        sourceStatusMap.forEach((source, sourceStatusBlock) -> sourceStatusBlock.setValue(0));
        cardsInHand.getChildren().clear();
        fractionCardPanes.forEach((cardType, fractionCardPane) -> fractionCardPane.getChildren().clear());
        commonCardPanes.forEach((cardType, fractionCardPane) -> fractionCardPane.getChildren().clear());
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

        RoundPhases roundPhases = new RoundPhases(width * 0.0615);

        fractionCardsStack = new StackPane();
        fractionCardsStack.setPrefWidth(cardWidth);

        VBox dealPane = new VBox();
        dealPane.setPrefWidth(cardWidth);

        commonCardsStack = new StackPane();
        commonCardsStack.setPrefWidth(cardWidth);

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
        VBox fractionSide = setSides(fractionCardPanes, NodeOrientation.RIGHT_TO_LEFT);
        VBox commonSide = setSides(commonCardPanes, NodeOrientation.LEFT_TO_RIGHT);
        fractionBoard = new FractionBoard(cardWidth, 3 * cardHeight);
        fractionPane.getChildren().addAll(fractionSide, fractionBoard, commonSide);
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

    private record SelectedFraction(String fraction, Sex sex) {
    }
}
