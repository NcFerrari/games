package cz.games.lp.components;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class RoundPhases extends VBox {

    public RoundPhases(double width, ScoreBoard scoreBoard) {
        setPrefWidth(width);
        setAlignment(Pos.CENTER);
        Button b = new Button("next");
        b.setOnAction(e -> scoreBoard.nextRound());
        getChildren().addAll(b);
    }
}
