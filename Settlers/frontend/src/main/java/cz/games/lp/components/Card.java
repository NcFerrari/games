package cz.games.lp.components;

import cz.games.lp.panes.PaneModel;
import javafx.scene.Group;
import lombok.Getter;

@Getter
public class Card extends Group {

    private final String cardId;

    public Card(String cardImage, PaneModel model) {
        this(cardImage, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String cardImage, double width, double height) {
        cardId = cardImage;
        ImageNode imageNode = new ImageNode(width, height);
        imageNode.setImage("cards/" + cardImage);
        getChildren().add(imageNode.getImageView());
    }
}
