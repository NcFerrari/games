package cz.games.lp.panes;

import javafx.scene.control.ScrollPane;

public class CPUPane extends ScrollPane {

    public CPUPane(PaneModel model) {
        setPrefWidth(model.getCardWidth());
    }
}
