package lp.piskvorky;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
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

    private static final String DEFAULT_WIDTH = "900";
    private static final String DEFAULT_HEIGHT = "900";
    private static final String DEFAULT_FIELD_SIZE = "50";
    private static final String DEFAULT_COUNT_FOR_WIN = "5";
    private static final String DEFAULT_USED_COLOR = "#A6A6A4FF";
    private static final String DEFAULT_VICTORY_COLOR = "#EEEE05FF";
    private final int countForWin;
    private final int recountedWidth;
    private final int recountedHeight;
    private final int fieldSize;
    private final Shape[] shapes;
    private final List<Shape> addedShapes = new ArrayList<>();
    private final List<Shape> winningShapes = new ArrayList<>();
    private final List<Shape> possibleWinShapes = new ArrayList<>();
    private final Player player = new Player();
    private final Properties properties = new Properties();
    private final Canvas canvasForGrid = new Canvas();
    private Pane pane;
    private Scene scene;

    public App() {
        loadProperties();
        fieldSize = getPropertyInt("field-size");
        countForWin = getPropertyInt("count-for-win");
        recountedWidth = (int) (Math.floor(getPropertyDouble("width") / fieldSize) * fieldSize);
        recountedHeight = (int) (Math.floor(getPropertyDouble("height") / fieldSize) * fieldSize);
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

        prepareLines();
        pane.getChildren().add(canvasForGrid);
        player.setPlayer(0);
    }

    private void addEvents() {
        pane.setOnMousePressed(evt -> {
            double x = Math.floor(evt.getX() / fieldSize) * fieldSize;
            double y = Math.floor(evt.getY() / fieldSize) * fieldSize;
            int index = getShapeIndex(x, y);
            if (shapes[index] == null) {
                addShape(index, x, y);
                checkWin(index);
            }
        });

        scene.setOnKeyPressed(this::shortCuts);
    }

    private void shortCuts(KeyEvent evt) {
        if (evt.getCode().equals(KeyCode.SPACE)) {
            resetGame();
        }
    }

    private void resetGame() {
        Arrays.fill(shapes, null);
        pane.getChildren().clear();
        pane.getChildren().add(canvasForGrid);
        addedShapes.clear();
    }

    private void addShape(int index, double x, double y) {
        shapes[index] = player.addShapeToPane(pane, x, y, fieldSize, fieldSize);
        addedShapes.add(shapes[index]);
        player.changePlayer();
    }

    private int getShapeIndex(double x, double y) {
        return (int) ((y * recountedWidth + fieldSize * x) / (fieldSize * fieldSize));
    }

    private void prepareLines() {
        canvasForGrid.setWidth(recountedWidth);
        canvasForGrid.setHeight(recountedHeight);
        GraphicsContext graphicsContext = canvasForGrid.getGraphicsContext2D();
        graphicsContext.setLineWidth(1);
        for (int i = 0; i <= recountedWidth; i += fieldSize) {
            graphicsContext.strokeLine(i, 0, i, recountedHeight);
        }
        for (int i = 0; i <= recountedHeight; i += fieldSize) {
            graphicsContext.strokeLine(0, i, recountedWidth, i);
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
        int fieldCounts = recountedWidth / fieldSize;
        int movingBy = 1;
        int startFrom = getStartingIndex(index, movingBy, startingIndex ->
                (index / fieldCounts) * fieldCounts == ((startingIndex - movingBy) / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                (actualIndex / fieldCounts) * fieldCounts == ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private void checkVerticalAxis(int index) {
        int movingBy = recountedWidth / fieldSize;
        int startFrom = getStartingIndex(index, movingBy, startingIndex -> true);

        checkFields(startFrom, movingBy, actualIndex -> true);
    }

    private void checkMainDiagonalAxis(int index) {
        int fieldCounts = recountedWidth / fieldSize;
        int movingBy = fieldCounts + 1;
        int startFrom = getStartingIndex(index, movingBy, startingIndex ->
                ((((startingIndex - movingBy) / fieldCounts) + 1) * fieldCounts) == (startingIndex / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                ((actualIndex / fieldCounts) + 1) * fieldCounts == ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private void checkSecondaryDiagonalAxis(int index) {
        int fieldCounts = recountedWidth / fieldSize;
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

        addedShapes.forEach(shape -> shape.setUsedName(getPropertyString("used-color")));
        winningShapes.forEach(shape -> shape.victoryBackground(getPropertyString("victory-color")));
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                properties.setProperty("width", DEFAULT_WIDTH);
                properties.setProperty("height", DEFAULT_HEIGHT);
                properties.setProperty("field-size", DEFAULT_FIELD_SIZE);
                properties.setProperty("count-for-win", DEFAULT_COUNT_FOR_WIN);
                properties.setProperty("used-color", DEFAULT_USED_COLOR);
                properties.setProperty("victory-color", DEFAULT_VICTORY_COLOR);
            } else {
                try (InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {
                    properties.load(reader);
                }
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
