package cz.games.lp.actions;

import cz.games.lp.panes.PaneModel;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

import java.util.List;
import java.util.Optional;

public class ChoiceDialog extends Dialog<List<String>> {

    private HBox hBox;
    private ToggleGroup sourcesToChoice;
    private List<String>[] lists;

    public ChoiceDialog(PaneModel model) {
        setTitle(model.getManager().getProduceChoiceDialog());
        setHeaderText(getTitle());
        setWidth(model.getManager().getWidth() * 0.164835);
        setHeight(model.getManager().getHeight() * 0.306122);
        setResizable(false);
        getDialogPane().setContent(createContent(model));
    }

    private HBox createContent(PaneModel model) {
        hBox = new HBox();
        sourcesToChoice = new ToggleGroup();
        ButtonType type = new ButtonType(model.getManager().getStartNewGameButtonTtitle(), ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(type);
        setResultConverter(buttonType -> {
            if (buttonType == type) {
                return lists[sourcesToChoice.getToggles().indexOf(sourcesToChoice.getSelectedToggle())];
            }
            return null;
        });
        return hBox;
    }

    public Optional<List<String>> addSources(List<String>... lists) {
        this.lists = lists;
        for (List<String> list : lists) {
            RadioButton radioButton = new RadioButton();
            radioButton.setText(list.getFirst());
            radioButton.setToggleGroup(sourcesToChoice);
            hBox.getChildren().add(radioButton);
        }
        sourcesToChoice.selectToggle(sourcesToChoice.getToggles().getFirst());
        return showAndWait();
    }
}
