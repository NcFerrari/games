package lp.piskvorky;

import lp.piskvorky.frontend.shapes.Circle;
import lp.piskvorky.frontend.shapes.Cross;
import lp.piskvorky.frontend.shapes.Shape;

import java.util.List;
import java.util.function.Supplier;

public class Player {

    private final List<Supplier<Shape>> classList = List.of(
            () -> new Cross("Cross", "#FF0000"),
            () -> new Circle("Circle", "#0000FF")
    );
    private Shape activePlayer;
    private int playerIndex;

    public Player() {
        changePlayer();
    }

    public void changePlayer() {
        activePlayer = classList.get(playerIndex++).get();
        if (playerIndex == classList.size()) {
            playerIndex = 0;
        }
    }

    public Shape getActivePlayerShape() {
        return activePlayer;
    }
}
