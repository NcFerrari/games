package lp.fe;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import lp.Manager;
import lp.fe.enums.NamespaceEnum;

import java.util.Objects;

public class MainApp extends Application {

    private final Manager manager = Manager.getInstance();
    private final GridPane pane = new GridPane();
    private final ObservableList<TextField> textFields = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);
        stage.setTitle(NamespaceEnum.TITLE.getText());
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.show();

        scene.getStylesheets().add(
                Objects.requireNonNull(getClass().getClassLoader()
                        .getResource(NamespaceEnum.CSS_STYLE.getText())).toExternalForm());

        getFields(stage);
    }

    private void getFields(Stage stage) {
        for (int j = 0; j < manager.getSudokuRowsCount(); j++) {
            for (int i = 0; i < manager.getSudokuColumnsCount(); i++) {
                addTextField(stage, i, j);
            }
        }
    }

    private void addTextField(Stage stage, int i, int j) {
        TextField textField = new TextField();
        textField.setPrefSize(stage.getWidth() / manager.getSudokuColumnsCount(),
                stage.getHeight() / manager.getSudokuRowsCount());
        textField.setAlignment(Pos.CENTER);
        setTextFieldListeners(textField);
        textFields.add(textField);
        pane.add(textField, i, j);
    }

    private void setTextFieldListeners(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> textField.textProperty().set(
                String.valueOf(newValue.charAt(newValue.length() - 1))));
        textField.setOnKeyPressed(evt -> {
            switch (evt.getCode()) {
                case UP:
                    if (textFields.indexOf(textField) > manager.getSudokuColumnsCount() - 1) {
                        setNewValue(textField, -manager.getSudokuColumnsCount(), evt);
                    }
                    break;
                case DOWN:
                    if (textFields.indexOf(textField) < textFields.size() - manager.getSudokuColumnsCount()) {
                        setNewValue(textField, manager.getSudokuColumnsCount(), evt);
                    }
                    break;
                case LEFT:
                    if (textFields.indexOf(textField) >= 1) {
                        setNewValue(textField, -1, evt);
                    }
                    break;
                case RIGHT:
                    if (textFields.indexOf(textField) < textFields.size() - 1) {
                        setNewValue(textField, 1, evt);
                    }
                    break;
                default:
            }
        });
    }

    private void setNewValue(TextField textField, int value, KeyEvent evt) {
        textFields.get(textFields.indexOf(textField) + value).requestFocus();
        evt.consume();
    }
}
