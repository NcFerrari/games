package lp.piskvorky;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import lp.piskvorky.shapes.Circle;
import lp.piskvorky.shapes.Cross;
import lp.piskvorky.shapes.Shape;

import java.util.List;
import java.util.function.Supplier;

public class Player {

    private final List<Supplier<Shape>> classList = List.of(
            () -> new Cross("Cross", Color.RED),
            () -> new Circle("Circle", Color.BLUE)
    );
    private Shape activePlayer;
    private int playerIndex;

    public void setPlayer(int number) {
        if (number >= 0 && number < classList.size()) {
            playerIndex = number;
        }
        changePlayer();
    }

    public void changePlayer() {
        activePlayer = classList.get(playerIndex++).get();
        if (playerIndex == classList.size()) {
            playerIndex = 0;
        }
    }

    public Shape addShapeToPane(Pane pane, double x, double y, int width, int height) {
        return activePlayer.fillField(pane, x, y, width, height);
    }
}
