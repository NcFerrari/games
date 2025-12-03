package cz.games.lp.frontend.components;

import cz.games.lp.common.enums.Phases;
import cz.games.lp.frontend.models.CommonModel;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class RoundPhases extends VBox {

    private final Map<Phases, Button> buttons = new LinkedHashMap<>();
    private final CommonModel model;

    public RoundPhases(CommonModel model) {
        this.model = model;
        setPrefWidth(model.getUIConfig().getRoundPhasesButtonsWidth());
        setAlignment(Pos.CENTER);
        for (Phases phase : model.getGameData().getOrderedPhases()) {
            ImageNode imageNode = new ImageNode(model.getUIConfig().getPhaseButtonWidth(), model.getUIConfig().getPhaseButtonHeight());
            imageNode.setImage("phase_buttons/" + phase.name());
            Button button = new Button();
            button.setGraphic(imageNode.getImageView());
            button.setOnAction(evt -> {
                enabledOneButtonOnly(phase.getFollowingPhase());
                addButtonListener(phase);
            });
            buttons.put(phase, button);
            getChildren().add(button);
        }
    }

    public void reset() {
        enabledOneButtonOnly(Phases.LOOKOUT);
    }

    private void enabledOneButtonOnly(Phases enabledPhase) {
        buttons.forEach((phase, button) -> button.setDisable(true));
        buttons.get(enabledPhase).setDisable(false);
        model.getGameData().setCurrentPhase(enabledPhase);
    }

    private void addButtonListener(Phases phase) {
        switch (phase) {
            case LOOKOUT -> {
            }
            case PRODUCTION -> {
            }
            case ACTION -> {
            }
            case PASS_ACTION -> {
            }
            case CLEANUP -> {
            }
            default -> throw new IllegalArgumentException("Not existing phase");
        }
    }
}
