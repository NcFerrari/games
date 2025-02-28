package lp.piskvorky;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import lp.piskvorky.shapes.Shape;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.function.IntPredicate;

public class App extends Application {

    private final int countForWin;
    private final int recountedWidth;
    private final int recountedHeight;
    private final int smallerRand;
    private final int fieldSize;
    private final Shape[] shapes;
    private final List<Shape> addedShapes = new ArrayList<>();
    private final List<Shape> winningShapes = new ArrayList<>();
    private final List<Shape> possibleWinShapes = new ArrayList<>();
    private final Player player = new Player();
    private final Properties properties = new Properties();
    private Pane pane;
    private Scene scene;

    public App() {
        loadProperties();
        fieldSize = getPropertyInt("field-size");
        countForWin = getPropertyInt("count-for-win");
        recountedWidth = (int) (Math.floor(getPropertyDouble("width") / fieldSize) * fieldSize);
        recountedHeight = (int) (Math.floor(getPropertyDouble("height") / fieldSize) * fieldSize);
        smallerRand = Math.min(recountedWidth, recountedHeight);
        shapes = new Shape[(recountedWidth / fieldSize) * (recountedHeight / fieldSize)];
    }

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        scene = new Scene(pane, recountedWidth, recountedHeight);
        stage.setScene(scene);
        stage.setTitle(Texts.TITLE.getText());
        stage.setResizable(false);
        stage.show();

        addEvents();

        drawLines();
        player.setPlayer(0);
    }

    private void addEvents() {
        pane.setOnMousePressed(evt -> {
            double x = Math.floor(evt.getX() / fieldSize) * fieldSize;
            double y = Math.floor(evt.getY() / fieldSize) * fieldSize;
            getIndexAndCheckWin(x, y);
        });

        scene.setOnKeyPressed(this::shortCuts);
    }

    private void shortCuts(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.SPACE)) {
            Arrays.fill(shapes, null);
            addedShapes.forEach(Shape::removeFromPane);
            addedShapes.clear();
        }
    }

    private void getIndexAndCheckWin(double x, double y) {
        int index = getShapeIndex(x, y);
        if (shapes[index] == null) {
            shapes[index] = player.getActivePlayer(pane, x, y, fieldSize, fieldSize);
            addedShapes.add(shapes[index]);
            checkWin(index);
            player.changePlayer();
        }
    }

    private int getShapeIndex(double x, double y) {
        return (int) ((y * smallerRand + fieldSize * x) / (fieldSize * fieldSize));
    }

    private void drawLines() {
        for (int i = 0; i <= recountedWidth; i += fieldSize) {
            Line line = new Line(i, 0, i, recountedHeight);
            pane.getChildren().add(line);
        }
        for (int i = 0; i <= recountedHeight; i += fieldSize) {
            Line line = new Line(0, i, recountedWidth, i);
            pane.getChildren().add(line);
        }
    }

    private void checkWin(int index) {
        winningShapes.clear();

        checkHorizontalAxis(index);
        checkVerticalAxis(index);
        checkMainDiagonalAxis(index);
        checkSecondaryDiagonalAxis(index);

        if (!winningShapes.isEmpty()) {
            win(shapes[index].getName());
        }
    }

    private void checkHorizontalAxis(int index) {
        int fieldCounts = smallerRand / fieldSize;
        int movingBy = 1;
        int startFrom = getStartingIndex(index, movingBy, startingIndex ->
                (index / fieldCounts) * fieldCounts == ((startingIndex - movingBy) / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                (actualIndex / fieldCounts) * fieldCounts == ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private void checkVerticalAxis(int index) {
        int movingBy = smallerRand / fieldSize;
        int startFrom = getStartingIndex(index, movingBy, startingIndex -> true);

        checkFields(startFrom, movingBy, actualIndex -> true);
    }

    private void checkMainDiagonalAxis(int index) {
        int fieldCounts = smallerRand / fieldSize;
        int movingBy = fieldCounts + 1;
        int startFrom = getStartingIndex(index, movingBy, startingIndex ->
                ((((startingIndex - movingBy) / fieldCounts) + 1) * fieldCounts) == (startingIndex / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                ((actualIndex / fieldCounts) + 1) * fieldCounts == ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private void checkSecondaryDiagonalAxis(int index) {
        int fieldCounts = smallerRand / fieldSize;
        int movingBy = fieldCounts - 1;
        int startFrom = getStartingIndex(index, movingBy, startingIndex ->
                ((startingIndex - movingBy) / fieldCounts) * fieldCounts != (startingIndex / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                (actualIndex / fieldCounts) * fieldCounts != ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private int getStartingIndex(int index, int movingBy, IntPredicate predicate) {
        int startingIndex = index;
        while (startingIndex - movingBy >= 0 &&
                predicate.test(startingIndex) &&
                shapes[startingIndex - movingBy] != null &&
                shapes[startingIndex - movingBy].getName().equals(shapes[index].getName())) {
            startingIndex -= movingBy;
        }
        return startingIndex;
    }

    private void checkFields(int startFrom, int movingBy, IntPredicate predicate) {
        possibleWinShapes.clear();
        int index = startFrom;
        possibleWinShapes.add(shapes[index]);
        while (index + movingBy < shapes.length &&
                predicate.test(index) &&
                shapes[index + movingBy] != null &&
                shapes[index + movingBy].getName().equals(shapes[index].getName())) {
            index = index + movingBy;
            possibleWinShapes.add(shapes[index]);
        }
        if (possibleWinShapes.size() >= countForWin) {
            winningShapes.addAll(possibleWinShapes);
        }
    }

    private void win(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Texts.WIN.getText());
        alert.setHeaderText(Texts.VICTORY.getText());
        alert.setContentText(Texts.WINS.getText() + title);
        alert.show();

        addedShapes.forEach(Shape::setUsedName);
        winningShapes.forEach(Shape::victoryBackground);
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new IllegalArgumentException("Missing configuration file for " + getClass().getName());
            }
            try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                properties.load(reader);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("IO Exception");
        }
    }

    private int getPropertyInt(String propertyName) {
        return Integer.parseInt(getPropertyString(propertyName));
    }

    private double getPropertyDouble(String propertyName) {
        return Double.parseDouble(getPropertyString(propertyName));
    }

    private String getPropertyString(String propertyName) {
        return (String) properties.get(propertyName);
    }
}
