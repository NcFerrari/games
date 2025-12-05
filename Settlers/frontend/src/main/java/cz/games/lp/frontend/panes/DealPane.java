package cz.games.lp.frontend.panes;

import cz.games.lp.frontend.components.transition_components.Card;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class DealPane extends ScrollPane {

    private final VBox cardPane;

    public DealPane(CommonModel model) {
        setPrefWidth(model.getUIConfig().getCardWidth());
        setHbarPolicy(ScrollBarPolicy.NEVER);
        setFitToHeight(true);
        setStyle(model.getUIConfig().getStyle());
        cardPane = new VBox();
        cardPane.setPadding(new Insets(0, 0, model.getUIConfig().getDealSpacing(), 0));
        cardPane.setAlignment(Pos.BOTTOM_CENTER);
        cardPane.setSpacing(model.getUIConfig().getDealSpacing());
        setContent(cardPane);
    }

    public void makeADeal(Card card) {
        card.getNodes().setRotate(180);
        cardPane.getChildren().add(card.getNodes());
    }

    public void clear() {
        cardPane.getChildren().clear();
    }
}
