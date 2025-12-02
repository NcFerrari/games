package cz.games.lp.frontend.panes;

import cz.games.lp.common.enums.Sources;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;


public class CenterPane extends VBox {

    public CenterPane(CommonModel model) {
        HBox incomingPane = new HBox();
        VBox.setVgrow(incomingPane, Priority.ALWAYS);
//        model.getFactionCardsStack().setPrefWidth(model.getManager().getCardWidth());
//
//        model.getCommonCardsStack().setPrefWidth(model.getManager().getCardWidth());

        Region space = new Region();
//        space.setPrefWidth(model.getManager().getWidth() * 0.0204);

//        incomingPane.getChildren().addAll(
//                model.getScoreBoard(),
//                model.getRoundPhases(),
//                model.getFactionCardsStack(),
//                model.getDeals(),
//                model.getCommonCardsStack(),
//                space,
//                createCardsInHandPane(model)
//        );
        getChildren().add(incomingPane);
    }

//    private ScrollPane createCardsInHandPane(CommonModel model) {
////        ScrollPane cardsInHandScrollPane = new ScrollPane(model.getCardsInHand());
////        cardsInHandScrollPane.setStyle(model.getStyle());
////        cardsInHandScrollPane.setPrefWidth(model.getManager().getWidth() * 0.3285);
//        return cardsInHandScrollPane;
//    }
}
