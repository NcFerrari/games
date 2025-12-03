package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.ScrollPane;

public class CardsInHandPane extends ScrollPane {

    public CardsInHandPane(CommonModel model) {
        setPrefWidth(model.getUIConfig().getCardInHandsWidth());
        setStyle(model.getUIConfig().getStyle());
        setContent(model.getCardsInHand());
    }
}
