package lp.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lp.frontend.shape.TestBlock;

public class TestingApi extends Application {

    private Pane pane;
    private Scene scene;
    private Stage stage;

    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        pane = new Pane();
        pane.setStyle("-fx-background-color: black");
        scene = new Scene(pane, 800, 800);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
