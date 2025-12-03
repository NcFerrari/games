package cz.games.lp.frontend.panes;

//import cz.games.lp.backend.frontend.enums.CardType;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class BottomPane extends HBox {
//    private final CardTypes[] cardTypesForImperialSides = {CardTypes.PRODUCTION, CardTypes.PROPERTIES, CardTypes.ACTION};
    public BottomPane(CommonModel model) {
        setPrefHeight(model.getUIConfig().getHeight() * 0.612);

//        VBox factionSide = setSides(model, model.getBuiltFactionCards(), NodeOrientation.RIGHT_TO_LEFT);
//        VBox commonSide = setSides(model, model.getBuiltCommonCards(), NodeOrientation.LEFT_TO_RIGHT);
//        getChildren().addAll(factionSide, model.getFactionBoard(), commonSide);
    }

//    private VBox setSides(PaneModel model, Map<CardType, HBox> cardPanes, NodeOrientation orientation) {
//        VBox sideVBox = new VBox();
//        sideVBox.setPrefWidth(model.getManager().getWidth() * 0.41365);
//
//        for (CardType cardType : model.getManager().getCardTypes()) {
//            HBox boxForCards = new HBox();
//            boxForCards.setNodeOrientation(orientation);
//            boxForCards.setPrefHeight(model.getManager().getCardHeight());
//            cardPanes.put(cardType, boxForCards);
//
//            ScrollPane scrollPane = new ScrollPane(boxForCards);
//            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
//            scrollPane.setStyle(model.getStyle());
//            sideVBox.getChildren().add(scrollPane);
//        }
//        return sideVBox;
//    }
}
