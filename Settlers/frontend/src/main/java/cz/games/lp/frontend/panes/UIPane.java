package cz.games.lp.frontend.panes;

import javafx.scene.layout.BorderPane;

public class UIPane extends BorderPane {

    public UIPane(PaneModel model) {
        setStyle(model.getStyle());
        setTop(new SourcePane(model));
        setCenter(new CenterPane(model));
        setBottom(new BottomPane(model));
    }
}
