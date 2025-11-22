package cz.games.lp.panes;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class CenterPane extends HBox {

    public CenterPane(PaneModel model) {
        VBox.setVgrow(this, Priority.ALWAYS);

        Region space = new Region();
        space.setPrefWidth(model.getWidth() * 0.0204);

        getChildren().addAll(
                model.getScoreBoard(),
                model.getRoundPhases(),
                createFactionCardStack(model),
                model.getDeals(),
                createCommonCardStack(model),
                space,
                createCardsInHandPane(model)
        );
    }

    private StackPane createFactionCardStack(PaneModel model) {
        StackPane factionCardsStack = new StackPane();
        factionCardsStack.setPrefWidth(model.getCardWidth());
        return factionCardsStack;
    }

    private StackPane createCommonCardStack(PaneModel model) {
        StackPane commonCardsStack = new StackPane();
        commonCardsStack.setPrefWidth(model.getCardWidth());
        return commonCardsStack;
    }

    private ScrollPane createCardsInHandPane(PaneModel model) {
        ScrollPane cardsInHandScrollPane = new ScrollPane(model.getCardsInHand());
        cardsInHandScrollPane.setStyle(model.getStyle());
        cardsInHandScrollPane.setPrefWidth(model.getWidth() * 0.3285);
        return cardsInHandScrollPane;
    }
}
