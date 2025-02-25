package lp.piskvorky;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;

public class App extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private static final int FIELD_SIZE = 30;
    private static final int COUNT_FOR_WIN = 5;
    private final Shape[] shapes = new Shape[(WIDTH / FIELD_SIZE) * (HEIGHT / FIELD_SIZE)];
    private final List<Shape> winningShapes = new ArrayList<>();
    private final List<Shape> possibleWinShapes = new ArrayList<>();
    private Pane pane;
    private Players activePlayer;
    private int playerIndex;

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.show();

        drawLines();
        addEvents();
        changePlayer();
    }

    private void addEvents() {
        pane.setOnMousePressed(evt -> {
            double x = Math.floor(evt.getX() / FIELD_SIZE) * FIELD_SIZE;
            double y = Math.floor(evt.getY() / FIELD_SIZE) * FIELD_SIZE;
            int index = (int) (y + (x / FIELD_SIZE));
            if (shapes[index] == null) {
                shapes[index] = activePlayer.fillField(pane, x, y, FIELD_SIZE, FIELD_SIZE);
                checkWin(index);
                changePlayer();
            }
        });
    }

    private void drawLines() {
        for (int i = 0; i <= WIDTH; i += FIELD_SIZE) {
            Line line = new Line(i, 0, i, WIDTH);
            pane.getChildren().add(line);
        }
        for (int i = 0; i <= HEIGHT; i += FIELD_SIZE) {
            Line line = new Line(0, i, HEIGHT, i);
            pane.getChildren().add(line);
        }
    }

    private void changePlayer() {
        activePlayer = Players.values()[playerIndex++];
        if (playerIndex == Players.values().length) {
            playerIndex = 0;
        }
    }

    private void checkWin(int index) {
//        checkAxis(index, 1);
//        checkAxis(index, FIELD_SIZE);
//        checkAxis(index, FIELD_SIZE + 1);
//        checkAxis(index, FIELD_SIZE - 1);
    }

    private void checkAxis(int index, int movingValue) {
        int newPosition = index;
        for (int i = 1; i < COUNT_FOR_WIN; i++) {
            if (isValidateShape(index, movingValue, index - i * movingValue)) {
                newPosition = index - i * movingValue;
            } else {
                break;
            }
        }
        System.out.println(newPosition);
    }

    private boolean isValidateShape(int index, int movingValue, int newPosition) {
        if (newPosition < 0 || (movingValue == 1 && newPosition < (index / FIELD_SIZE) * FIELD_SIZE)) {
            return false;
        }
        if (shapes[newPosition] == null) {
            return false;
        }
        return shapes[index].getName().equals(shapes[newPosition].getName());
    }

    private void win(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("VICTORY");
        alert.setHeaderText("VÍTĚZSTVÍ!!!");
        alert.setContentText("Vyhrává " + title);
        alert.show();
    }
}
