package lp.piskvorky.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import lp.Manager;
import lp.piskvorky.conf.Texts;

public class App extends Application {

    private final Manager manager = Manager.getManager();
    private final Canvas canvasForGrid = new Canvas();
    private Pane pane;
    private Scene scene;

    @Override
    public void start(Stage stage) {
        pane = new Pane();
        scene = new Scene(pane, manager.getGameWidth(), manager.getGameHeight());
        stage.setScene(scene);
        stage.setTitle(Texts.TITLE.getText());
        stage.setOnCloseRequest(windowEvent -> System.exit(0));
        stage.setResizable(false);
        stage.show();

        prepareLines();
        pane.getChildren().add(canvasForGrid);

        addEvents();
    }

    private void addEvents() {
        pane.setOnMousePressed(evt -> {
            double x = Math.floor(evt.getX() / manager.getFieldSize()) * manager.getFieldSize();
            double y = Math.floor(evt.getY() / manager.getFieldSize()) * manager.getFieldSize();
            if (manager.isShapeEnable(x, y)) {
                addShape(x, y);
                if (manager.isWin()) {
                    win(manager.getCurrentShapeName());
                }
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
        pane.getChildren().clear();
        pane.getChildren().add(canvasForGrid);
        manager.resetGame();
    }

    private void prepareLines() {
        canvasForGrid.setWidth(manager.getGameWidth());
        canvasForGrid.setHeight(manager.getGameHeight());
        GraphicsContext graphicsContext = canvasForGrid.getGraphicsContext2D();
        graphicsContext.setLineWidth(1);
        for (int i = 0; i <= manager.getGameWidth(); i += manager.getFieldSize()) {
            graphicsContext.strokeLine(i, 0, i, manager.getGameHeight());
        }
        for (int i = 0; i <= manager.getGameHeight(); i += manager.getFieldSize()) {
            graphicsContext.strokeLine(0, i, manager.getGameWidth(), i);
        }
    }

    private void addShape(double x, double y) {
        manager.newAddedShape(manager.getPlayer().getActivePlayerShape().fillField(pane, x, y, manager.getFieldSize(), manager.getFieldSize()));
        manager.getPlayer().changePlayer();
    }

    private void win(String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(Texts.WIN.getText());
        alert.setHeaderText(Texts.VICTORY.getText());
        alert.setContentText(Texts.WINS.getText() + title);
        alert.show();
    }
}
