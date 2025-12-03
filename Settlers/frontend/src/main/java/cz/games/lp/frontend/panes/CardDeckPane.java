package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.components.Card;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class CardDeckPane extends VBox {

    private final CommonModel model;
    private Card card;

    public CardDeckPane(CommonModel model) {
        this.model = model;
        setAlignment(Pos.CENTER);
        setPrefWidth(model.getUIConfig().getCardWidth());
    }

    public void createCard(String cardPath) {
        card = new Card(cardPath, model);
        getChildren().add(card);
    }

    public void showCard(boolean visibleCard) {
        card.setVisible(visibleCard);
    }

    public boolean isCardVisible() {
        return card.isVisible();
    }
}
