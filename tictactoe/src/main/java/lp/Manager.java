package lp;

import lombok.Getter;
import lp.piskvorky.frontend.App;
import lp.piskvorky.GameSettings;
import lp.piskvorky.Player;
import lp.piskvorky.conf.Props;
import lp.piskvorky.frontend.shapes.Shape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;

@Getter
public class Manager {

    private static Manager manager;

    private final Player player = new Player();
    private final Props props = new Props();
    private final Shape[] shapes;
    private final GameSettings settings;

    private final List<Shape> addedShapes = new ArrayList<>();
    private final List<Shape> winningShapes = new ArrayList<>();
    private final List<Shape> possibleWinShapes = new ArrayList<>();

    private int currentIndex;

    public static Manager getManager() {
        if (manager == null) {
            manager = new Manager();
        }
        return manager;
    }

    private Manager() {
        settings = new GameSettings(props);
        shapes = new Shape[(getGameWidth() / getFieldSize()) * (getGameHeight() / getFieldSize())];
    }

    public int getGameWidth() {
        return settings.getRecountedWidth();
    }

    public int getGameHeight() {
        return settings.getRecountedHeight();
    }

    public int getCountForWin() {
        return settings.getCountForWin();
    }

    public int getFieldSize() {
        return settings.getFieldSize();
    }

    public String getCurrentShapeName() {
        return shapes[getCurrentIndex()].getName();
    }

    public boolean isShapeEnable(double x, double y) {
        currentIndex = getShapeIndex(x, y);
        return shapes[manager.getCurrentIndex()] == null;
    }

    public void resetGame() {
        Arrays.fill(shapes, null);
        addedShapes.clear();
    }

    public void newAddedShape(Shape shape) {
        shapes[currentIndex] = shape;
        addedShapes.add(shape);
    }

    private int getShapeIndex(double x, double y) {
        return (int) ((y * getGameWidth() + getFieldSize() * x) / (getFieldSize() * getFieldSize()));
    }

    public boolean isWin() {
        winningShapes.clear();

        checkHorizontalAxis();
        checkVerticalAxis();
        checkMainDiagonalAxis();
        checkSecondaryDiagonalAxis();

        if (!winningShapes.isEmpty()) {
            addedShapes.forEach(shape -> shape.setUsedName(props.getPropertyString("used-color")));
            winningShapes.forEach(shape -> shape.victoryBackground(props.getPropertyString("victory-color")));
            return true;
        }
        return false;
    }

    private void checkHorizontalAxis() {
        int fieldCounts = getGameWidth() / getFieldSize();
        int movingBy = 1;
        int startFrom = getStartingIndex(getCurrentIndex(), movingBy, startingIndex ->
                (getCurrentIndex() / fieldCounts) * fieldCounts == ((startingIndex - movingBy) / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                (actualIndex / fieldCounts) * fieldCounts == ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private void checkVerticalAxis() {
        int movingBy = getGameWidth() / getFieldSize();
        int startFrom = getStartingIndex(getCurrentIndex(), movingBy, startingIndex -> true);

        checkFields(startFrom, movingBy, actualIndex -> true);
    }

    private void checkMainDiagonalAxis() {
        int fieldCounts = getGameWidth() / getFieldSize();
        int movingBy = fieldCounts + 1;
        int startFrom = getStartingIndex(getCurrentIndex(), movingBy, startingIndex ->
                ((((startingIndex - movingBy) / fieldCounts) + 1) * fieldCounts) == (startingIndex / fieldCounts) * fieldCounts);

        checkFields(startFrom, movingBy, actualIndex ->
                ((actualIndex / fieldCounts) + 1) * fieldCounts == ((actualIndex + movingBy) / fieldCounts) * fieldCounts);
    }

    private void checkSecondaryDiagonalAxis() {
        int fieldCounts = getGameWidth() / getFieldSize();
        int movingBy = fieldCounts - 1;
        int startFrom = getStartingIndex(getCurrentIndex(), movingBy, startingIndex ->
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
        if (possibleWinShapes.size() >= getCountForWin()) {
            winningShapes.addAll(possibleWinShapes);
        }
    }

    public static void main(String[] args) {
        javafx.application.Application.launch(App.class);
    }
}
