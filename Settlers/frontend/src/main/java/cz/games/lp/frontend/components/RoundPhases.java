package cz.games.lp.frontend.components;

//import cz.games.lp.backend.frontend.enums.Phases;
import cz.games.lp.frontend.models.CommonModel;
import javafx.scene.layout.VBox;

public class RoundPhases extends VBox {

//    @Getter
//    private final Map<Phases, Button> buttons = new LinkedHashMap<>();
//    @Getter
//    @Setter
//    private Phases currentPhase;

    public RoundPhases(CommonModel model) {
//        setPrefWidth(model.getManager().getWidth() * 0.0615);
//        setAlignment(Pos.CENTER);
//        IntStream.range(1, 6).forEach(i -> {
//            ImageNode imageNode = new ImageNode(model.getManager().getWidth() * 0.0615 - 10, 30);
//            imageNode.setImage("phase_buttons/" + i + "phase");
//            Button button = new Button();
//            button.setGraphic(imageNode.getImageView());
//            buttons.put(Phases.values()[i - 1], button);
//            getChildren().add(button);
//        });
    }

    public void reset() {
//        buttons.forEach((k, v) -> v.setDisable(true));
//        buttons.get(Phases.LOOKOUT).setDisable(false);
//        setCurrentPhase(Phases.LOOKOUT);
    }

//    public void switchButton(Phases phase) {
//        setCurrentPhase(phase);
//        getButtons().forEach((cardType, button) -> button.setDisable(true));
//        getButtons().get(phase).setDisable(false);
//    }
}
