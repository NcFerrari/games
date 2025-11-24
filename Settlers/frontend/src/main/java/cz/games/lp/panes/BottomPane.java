package cz.games.lp.panes;

import cz.games.lp.enums.CardType;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class BottomPane extends HBox {

    public BottomPane(PaneModel model) {
        setPrefHeight(model.getManager().getHeight() * 0.612);

        VBox factionSide = setSides(model, model.getBuiltFactionCards(), NodeOrientation.RIGHT_TO_LEFT);
        VBox commonSide = setSides(model, model.getBuiltCommonCards(), NodeOrientation.LEFT_TO_RIGHT);
        getChildren().addAll(factionSide, model.getFactionBoard(), commonSide);
    }

    private VBox setSides(PaneModel model, Map<CardType, HBox> cardPanes, NodeOrientation orientation) {
        VBox sideVBox = new VBox();
        sideVBox.setPrefWidth(model.getManager().getWidth() * 0.41365);

        for (CardType cardType : model.getManager().getCardTypes()) {
            HBox boxForCards = new HBox();
            boxForCards.setNodeOrientation(orientation);
            boxForCards.setPrefHeight(model.getManager().getCardHeight());
            cardPanes.put(cardType, boxForCards);

            ScrollPane scrollPane = new ScrollPane(boxForCards);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setStyle(model.getStyle());
            sideVBox.getChildren().add(scrollPane);
        }
        return sideVBox;
    }
}
