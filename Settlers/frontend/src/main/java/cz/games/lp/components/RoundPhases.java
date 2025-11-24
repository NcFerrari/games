package cz.games.lp.components;

import cz.games.lp.enums.Phases;
import cz.games.lp.panes.PaneModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class RoundPhases extends VBox {

    @Getter
    private final Map<Phases, Button> buttons = new LinkedHashMap<>();
    @Getter
    @Setter
    private Phases currentPhase;

    public RoundPhases(PaneModel model) {
        setPrefWidth(model.getManager().getWidth() * 0.0615);
        setAlignment(Pos.CENTER);
        IntStream.range(1, 6).forEach(i -> {
            ImageNode imageNode = new ImageNode(model.getManager().getWidth() * 0.0615 - 10, 30);
            imageNode.setImage("phase_buttons/" + i + "phase");
            Button button = new Button();
            button.setGraphic(imageNode.getImageView());
            buttons.put(Phases.values()[i - 1], button);
            getChildren().add(button);
        });
    }

    public void reset() {
        buttons.forEach((k, v) -> v.setDisable(true));
        buttons.get(Phases.LOOKOUT).setDisable(false);
        setCurrentPhase(Phases.LOOKOUT);
    }

    public void switchButton(Phases phase) {
        getButtons().forEach((cardType, button) -> button.setDisable(true));
        getButtons().get(phase).setDisable(false);
    }
}
