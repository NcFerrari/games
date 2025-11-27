package cz.games.lp.actions;

import cz.games.lp.components.SourceStatusBlock;
import cz.games.lp.panes.PaneModel;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public class ProduceChoiceDialog extends Dialog<SourceStatusBlock> {

    public ProduceChoiceDialog(PaneModel model) {
        setTitle(model.getManager().getProduceChoiceDialog());
        setHeaderText(getTitle());
        setWidth(model.getManager().getWidth() * 0.164835);
        setHeight(model.getManager().getHeight() * 0.306122);
        setResizable(false);
        getDialogPane().setContent(createContentPane(model));
    }

    private HBox createContentPane(PaneModel model) {
        HBox hBox = new HBox();

        return hBox;
    }
}
