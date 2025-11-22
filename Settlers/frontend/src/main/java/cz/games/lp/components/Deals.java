package cz.games.lp.components;

import cz.games.lp.panes.PaneModel;
import javafx.scene.layout.VBox;

public class Deals extends VBox {

    public Deals(PaneModel model) {
        setPrefWidth(model.getCardWidth());
    }
}
