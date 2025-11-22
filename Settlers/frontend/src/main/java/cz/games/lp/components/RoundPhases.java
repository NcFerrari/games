package cz.games.lp.components;

import cz.games.lp.enums.Phases;
import cz.games.lp.panes.PaneModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.IntStream;

public class RoundPhases extends VBox {

    @Getter
    private final ObservableList<Button> buttons = FXCollections.observableArrayList();
    @Getter
    @Setter
    private Phases currentPhase;

    public RoundPhases(PaneModel model) {
        setPrefWidth(model.getWidth() * 0.0615);
        setAlignment(Pos.CENTER);
        IntStream.range(1, 5).forEach(i -> {
            ImageNode imageNode = new ImageNode(model.getWidth() * 0.0615 - 10, 30);
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
        setCurrentPhase(Phases.LOOKOUT);
    }
}
