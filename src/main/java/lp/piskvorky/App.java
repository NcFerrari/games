package lp.piskvorky;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class App extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private static final int FIELD_SIZE = 30;
    private static final int COUNT_FOR_WIN = 5;
    private final Shape[] shapes = new Shape[(WIDTH / FIELD_SIZE) * (HEIGHT / FIELD_SIZE)];
    private final List<Shape> addedShapes = new ArrayList<>();
    private final List<Shape> winningShapes = new ArrayList<>();
    private final List<Shape> possibleWinShapes = new ArrayList<>();
    private Pane pane;
    private Shape activePlayer;
    private int playerIndex;

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
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
                addedShapes.add(shapes[index]);
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
        if (playerIndex++ == 0) {
            activePlayer = new Cross("Cross");
        } else {
            activePlayer = new Circle("Circle");
        }

        if (playerIndex == 2) {
            playerIndex = 0;
        }
    }

    private void checkWin(int index) {
        winningShapes.clear();

        checkAxis(index, 1);
        checkAxis(index, FIELD_SIZE);
        checkAxis(index, FIELD_SIZE + 1);
        checkAxis(index, FIELD_SIZE - 1);

        if (!winningShapes.isEmpty()) {
            win(shapes[index].getName());
        }
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
        countWinning(newPosition, movingValue);
    }

    private void countWinning(int index, int movingValue) {
        possibleWinShapes.clear();
        int newPosition = index;
        while (newPosition < shapes.length && shapes[newPosition] != null && shapes[index].getName().equals(shapes[newPosition].getName())) {
            possibleWinShapes.add(shapes[newPosition]);
            newPosition = index + movingValue * possibleWinShapes.size();
        }

        if (possibleWinShapes.size() >= COUNT_FOR_WIN) {
            winningShapes.addAll(possibleWinShapes);
        }
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
        alert.setTitle("Výhra");
        alert.setHeaderText("VÍTĚZSTVÍ!!!");
        alert.setContentText("Vyhrává " + title);
        alert.show();

        winningShapes.forEach(Shape::victoryBackground);
        addedShapes.forEach(Shape::setUsedName);
    }
}
