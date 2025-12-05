package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import lombok.Getter;

public class CardsInHandPane extends ScrollPane {

    private final HBox cards = new HBox();

    public CardsInHandPane(CommonModel model) {
        setPrefWidth(model.getUIConfig().getCardInHandsWidth());
        setStyle(model.getUIConfig().getStyle());
        setContent(cards);
    }

    public ObservableList<Node> getCards() {
        return cards.getChildren();
    }
}
