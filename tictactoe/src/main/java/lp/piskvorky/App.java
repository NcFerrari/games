package lp.piskvorky;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lp.piskvorky.shapes.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private static final int FIELD_SIZE = 43;
    private static final int COUNT_FOR_WIN = 5;
    private static final int RECOUNTED_WIDTH = (int) (Math.floor((double) WIDTH / FIELD_SIZE) * FIELD_SIZE);
    private static final int RECOUNTED_HEIGHT = (int) (Math.floor((double) HEIGHT / FIELD_SIZE) * FIELD_SIZE);
    private final Shape[] shapes = new Shape[(RECOUNTED_WIDTH / FIELD_SIZE) * (RECOUNTED_HEIGHT / FIELD_SIZE)];
    private final List<Shape> addedShapes = new ArrayList<>();
    private final List<Shape> winningShapes = new ArrayList<>();
    private final List<Shape> possibleWinShapes = new ArrayList<>();
    private final Player player = new Player();
    private Pane pane;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        scene = new Scene(pane, RECOUNTED_WIDTH, RECOUNTED_HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        drawLines();
        addEvents();
        player.setPlayer(0);
    }

    private void addEvents() {
        pane.setOnMousePressed(evt -> {
            double x = Math.floor(evt.getX() / FIELD_SIZE) * FIELD_SIZE;
            double y = Math.floor(evt.getY() / FIELD_SIZE) * FIELD_SIZE;
            int index = (int) ((y / FIELD_SIZE) * ((double) Math.min(RECOUNTED_WIDTH, RECOUNTED_HEIGHT) / FIELD_SIZE) + (x / FIELD_SIZE));
            if (shapes[index] == null) {
                shapes[index] = player.getActivePlayer(pane, x, y, FIELD_SIZE, FIELD_SIZE);
                addedShapes.add(shapes[index]);
                checkWin(index);
                player.changePlayer();
            }
        });

        scene.setOnKeyPressed(evt -> {
            if (evt.getCode().equals(KeyCode.SPACE)) {
                Arrays.fill(shapes, null);
                winningShapes.clear();
                addedShapes.clear();
                pane.getChildren().clear();
                drawLines();
            }
        });
    }

    private void drawLines() {
        for (int i = 0; i <= RECOUNTED_WIDTH; i += FIELD_SIZE) {
            Line line = new Line(i, 0, i, RECOUNTED_HEIGHT);
            pane.getChildren().add(line);
        }
        for (int i = 0; i <= RECOUNTED_HEIGHT; i += FIELD_SIZE) {
            Line line = new Line(0, i, RECOUNTED_WIDTH, i);
            pane.getChildren().add(line);
        }
    }

    private void checkWin(int index) {
        winningShapes.clear();

        checkAxis(index, 1);
        checkAxis(index, Math.min(RECOUNTED_WIDTH, RECOUNTED_HEIGHT) / FIELD_SIZE);
        checkAxis(index, Math.min(RECOUNTED_WIDTH, RECOUNTED_HEIGHT) / FIELD_SIZE + 1);
        checkAxis(index, Math.min(RECOUNTED_WIDTH, RECOUNTED_HEIGHT) / FIELD_SIZE - 1);

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
