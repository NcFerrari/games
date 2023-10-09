package lp.fe;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lp.fe.enums.NamespaceEnum;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Pane pane = new Pane();
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);
        stage.setTitle(NamespaceEnum.TITLE.getText());
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();
    }
}
