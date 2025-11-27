package cz.games.lp.actions;

import cz.games.lp.enums.Sources;
import cz.games.lp.panes.PaneModel;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class ProduceChoiceDialog extends Dialog<Sources> {

    public ProduceChoiceDialog(PaneModel model) {
        setTitle(model.getManager().getProduceChoiceDialog());
        setHeaderText(getTitle());
        setWidth(model.getManager().getWidth() * 0.164835);
        setHeight(model.getManager().getHeight() * 0.306122);
        setResizable(false);
        getDialogPane().setContent(createContent(model, "SETTLER", "FOOD"));
    }

    //    private HBox createContent(PaneModel model, ImageView... images) {
    private HBox createContent(PaneModel model, String... texts) {
        HBox hBox = new HBox();
        ToggleGroup sourcesToChoice = new ToggleGroup();
        for (String image : texts) {
            RadioButton radioButton = new RadioButton();
            radioButton.setText(image);
            //add image to radio button
            radioButton.setToggleGroup(sourcesToChoice);
            hBox.getChildren().add(radioButton);
        }
        sourcesToChoice.selectToggle(sourcesToChoice.getToggles().getFirst());
        ButtonType type = new ButtonType(model.getManager().getStartNewGameButtonTtitle(), ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().add(type);
        return hBox;
    }
}
