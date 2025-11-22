package cz.games.lp.components;

import cz.games.lp.api.IManager;
import cz.games.lp.enums.Phases;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.stream.IntStream;

public class RoundPhases extends VBox {

    private final IManager manager;
    private final ObservableList<Button> buttons = FXCollections.observableArrayList();

    public RoundPhases(double width, IManager manager) {
        this.manager = manager;
        setPrefWidth(width);
        setAlignment(Pos.CENTER);
        IntStream.range(1, 5).forEach(i -> {
            ImageNode imageNode = new ImageNode(width - 10, 30);
            imageNode.setImage("phase_buttons/" + i + "phase");
            Button button = new Button();
            button.setGraphic(imageNode.getImageView());
            buttons.add(button);
            getChildren().add(button);
        });
        reset();
    }

    public void reset() {
        getChildren().forEach(button -> button.setDisable(true));
        getChildren().getFirst().setDisable(false);
        manager.setCurrentPhase(Phases.LOOKOUT);
    }

    public ObservableList<Button> getButtons() {
        return buttons;
    }
}
