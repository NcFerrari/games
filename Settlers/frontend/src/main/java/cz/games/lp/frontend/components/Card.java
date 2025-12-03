package cz.games.lp.frontend.components;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;

public class Card extends Group {

    private static final int BORDER_WIDTH = 4;
    private final Rectangle border;
    @Getter
    private final String cardId;
    @Setter
    @Getter
    private boolean samurai;

    public Card(String cardName, CommonModel model) {
        this("", cardName, model);
    }

    public Card(String path, String cardName, CommonModel model) {
        cardId = cardName;
        ImageNode imageNode = new ImageNode(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight());
        imageNode.setImage("cards/" + path + "/" + cardName);

        border = new Rectangle(model.getUIConfig().getCardWidth(), model.getUIConfig().getCardHeight() - BORDER_WIDTH * 1.5);
        border.setFill(null);
        border.setStrokeWidth(BORDER_WIDTH);

        getChildren().addAll(imageNode.getImageView(), border);

    }

    public void select() {
        border.setStroke(Color.RED);
    }

    public void deselect() {
        border.setStroke(null);
    }
}
