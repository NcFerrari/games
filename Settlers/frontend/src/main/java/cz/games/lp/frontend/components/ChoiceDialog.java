package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.Factions;
import cz.games.lp.frontend.enums.Texts;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.HBox;

public class ChoiceDialog extends Dialog<Factions> {

    public ChoiceDialog(CommonModel model) {
        setTitle(Texts.CHOICE.get());
        setWidth(model.getUIConfig().getDialogWidth());
        setHeight(model.getUIConfig().getDialogHeight());
        setResizable(false);
        getDialogPane().setContent(createContent());
    }

    private HBox createContent() {
        HBox contentPane = new HBox();
        ButtonType type = new ButtonType(Texts.DIALOG_BUTTON.get(), ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(type);
        setResultConverter(buttonType -> null);
        return contentPane;
    }
}
