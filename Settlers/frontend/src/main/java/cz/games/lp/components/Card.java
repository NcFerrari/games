package cz.games.lp.components;

import cz.games.lp.panes.PaneModel;
import javafx.scene.Group;
import lombok.Getter;

@Getter
public class Card extends Group {

    private final String cardId;

    public Card(String cardName, PaneModel model) {
        this("", cardName, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String path, String cardName, PaneModel model) {
        this(path, cardName, model.getManager().getCardWidth(), model.getManager().getCardHeight());
    }

    public Card(String path, String cardName, double width, double height) {
        cardId = cardName;
        ImageNode imageNode = new ImageNode(width, height);
        imageNode.setImage("cards/" + path + "/" + cardName);
        getChildren().add(imageNode.getImageView());
    }
}
