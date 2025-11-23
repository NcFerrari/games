package cz.games.lp.panes;

import cz.games.lp.enums.Sex;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Objects;

public class ChoiceDialog extends Dialog<SelectedFaction> {

    public ChoiceDialog(PaneModel model) {
        setWidth(model.getWidth() * 0.164835);
        setHeight(model.getHeight() * 0.306122);
        setResizable(false);
        setTitle(model.getManager().getChooserDialogTitle());
        setHeaderText(getTitle());
        getDialogPane().setContent(createContent(model));
    }

    private HBox createContent(PaneModel model) {
        HBox contentPane = new HBox();
        ToggleGroup faction = createChoicePane(contentPane, model.getManager().getFactionArray());
        ToggleGroup sex = createChoicePane(contentPane, model.getManager().getSexArray());
        ButtonType type = new ButtonType(model.getManager().getStartNewGameButtonTtitle(), ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(type);
        setResultConverter(button -> new SelectedFaction(
                        ((RadioButton) Objects.requireNonNull(faction).getSelectedToggle()).getText(),
                        "Žena".equals(((RadioButton) Objects.requireNonNull(sex).getSelectedToggle()).getText()) ? Sex.FEMALE : Sex.MALE
                )
        );
        return contentPane;
    }

    private ToggleGroup createChoicePane(HBox contentPane, String... titles) {
        VBox pane = new VBox();
        ToggleGroup group = new ToggleGroup();
        for (String title : titles) {
            RadioButton radioButton = new RadioButton(title);
            radioButton.setFont(new Font(26));
            radioButton.setToggleGroup(group);
            pane.getChildren().add(radioButton);
        }
        group.selectToggle(group.getToggles().getFirst());
        contentPane.getChildren().add(pane);
        return group;
    }
}
