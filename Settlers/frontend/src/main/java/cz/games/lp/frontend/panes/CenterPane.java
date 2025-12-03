package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class CenterPane extends VBox {

    public CenterPane(CommonModel model) {
        HBox incomingPane = new HBox();
        VBox.setVgrow(incomingPane, Priority.ALWAYS);

        model.getFactionCardsStack().setPrefWidth(model.getUIConfig().getCardWidth());
        model.getCommonCardsStack().setPrefWidth(model.getUIConfig().getCardWidth());

        Region space = new Region();
        space.setPrefWidth(model.getUIConfig().getSpace());
        Button b = new Button("test");

        b.setOnAction(evt -> {
            model.getScoreBoard().scorePoint(5);
        });
        incomingPane.getChildren().addAll(
                model.getScoreBoard(),
                b
//                model.getRoundPhases(),
//                model.getFactionCardsStack(),
//                model.getDeals(),
//                model.getCommonCardsStack(),
//                space,
//                createCardsInHandPane(model)
        );
        getChildren().add(incomingPane);
    }

//    private ScrollPane createCardsInHandPane(CommonModel model) {
////        ScrollPane cardsInHandScrollPane = new ScrollPane(model.getCardsInHand());
////        cardsInHandScrollPane.setStyle(model.getStyle());
////        cardsInHandScrollPane.setPrefWidth(model.getManager().getWidth() * 0.3285);
//        return cardsInHandScrollPane;
//    }
}
