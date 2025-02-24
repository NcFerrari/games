package lp.piskvorky;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import javax.swing.JOptionPane;
import java.util.function.BiPredicate;

public class App extends Application {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 900;
    private static final int FIELD_SIZE = 30;
    private static final int COUNT_FOR_WIN = 5;
    private final String[] shapes = new String[(WIDTH / FIELD_SIZE) * (HEIGHT / FIELD_SIZE)];
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
                checkDirections(index);
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

    private void checkDirections(int index) {
        int startIndex = checkAxis(index, this::xAxeCheck);
        checkWin(startIndex, 1);
    }

    private void checkWin(int startIndex, int increment) {
        String title = shapes[startIndex];
        for (int i = 0; i < COUNT_FOR_WIN; i++) {
            int checkingIndex = startIndex + i * increment;
            if (checkingIndex >= shapes.length || !title.equals(shapes[checkingIndex])) {
                return;
            }
        }
        win(title);
    }

    private int checkAxis(int index, BiPredicate<Integer, Integer> predicate) {
        int i = 0;
        while (predicate.test(index, i) && i < COUNT_FOR_WIN && shapes[index].equals(shapes[index - i])) {
            i++;
        }
        i--;
        return index - i;
    }

    private boolean xAxeCheck(int fieldIndex, int index) {
        return fieldIndex - index >= (fieldIndex / FIELD_SIZE) * FIELD_SIZE;
    }

    private void win(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("VICTORY");
        alert.setHeaderText("VÍTĚZSTVÍ!!!");
        alert.setContentText("Vyhrává " + title);
        alert.show();
    }
}
