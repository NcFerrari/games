package cz.games.lp.panes;

import cz.games.lp.MainApp;
import cz.games.lp.enums.Sex;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ChoiceDialog {

    private Dialog<SelectedFaction> createChoiceFactionDialog() {
//        Dialog<MainApp.SelectedFaction> dialog = new Dialog<>();
//        dialog.setWidth(model.getWidth() * 0.164835);
//        dialog.setHeight(model.getHeight() * 0.306122);
//        dialog.setResizable(false);
//        dialog.setTitle("Výběr frakce");
//        dialog.setHeaderText(dialog.getTitle());
//        HBox contentPane = new HBox();
//        dialog.getDialogPane().setContent(contentPane);
//        ToggleGroup faction = createChoicePane(contentPane, "Barbaři", "Japonci", "Římané", "Egypťané");
//        ToggleGroup sex = createChoicePane(contentPane, "Žena", "Muž");
//        ButtonType type = new ButtonType("Začít novou hru", ButtonBar.ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().add(type);
//        dialog.setResultConverter(button -> new MainApp.SelectedFaction(
//                        ((RadioButton) faction.getSelectedToggle()).getText(),
//                        "Žena".equals(((RadioButton) sex.getSelectedToggle()).getText()) ? Sex.FEMALE : Sex.MALE
//                )
//        );
//        return dialog;
        return null;
    }

    private ToggleGroup createChoicePane(HBox contentPane, String... titles) {
//        VBox pane = new VBox();
//        ToggleGroup group = new ToggleGroup();
//        for (String title : titles) {
//            RadioButton radioButton = new RadioButton(title);
//            radioButton.setFont(new Font(26));
//            radioButton.setToggleGroup(group);
//            pane.getChildren().add(radioButton);
//        }
//        group.selectToggle(group.getToggles().getFirst());
//        contentPane.getChildren().add(pane);
//        return group;
        return null;
    }
}
