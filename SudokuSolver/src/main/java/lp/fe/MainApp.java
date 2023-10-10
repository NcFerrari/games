package lp.fe;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lp.Manager;
import lp.fe.enums.NamespaceEnum;

import java.io.File;
import java.util.Objects;

public class MainApp extends Application {

    private final Manager manager = Manager.getInstance();
    private final GridPane pane = new GridPane();
    private final ObservableList<TextField> textFields = FXCollections.observableArrayList();
    private final KeyCode[] inputKeys = new KeyCode[2];

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);
        stage.setTitle(NamespaceEnum.TITLE.getText());
        stage.setOnCloseRequest(event -> System.exit(0));
        stage.setResizable(false);
        stage.show();

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getClassLoader().
                getResource(NamespaceEnum.CSS_STYLE.getText())).toExternalForm());

        getFields(stage);

        loadSudoku();
    }

    private void loadSudoku() {
        resetFields();
        int index = 0;
        for (int[] row : manager.getSudoku().getData()) {
            for (int value : row) {
                if (value != 0) {
                    textFields.get(index).setText(String.valueOf(value));
                    textFields.get(index).setEditable(false);
                    textFields.get(index).setId(NamespaceEnum.RED_COLOR_STYLE.getText());
                }
                index++;
            }
        }
    }

    private void resetFields() {
        textFields.forEach(textField -> {
            textField.clear();
            textField.setEditable(true);
            textField.setId(NamespaceEnum.BLACK_COLOR_STYLE.getText());
        });
    }

    private void getFields(Stage stage) {
        for (int j = 0; j < manager.getSudokuRowsCount(); j++) {
            for (int i = 0; i < manager.getSudokuColumnsCount(); i++) {
                addTextField(stage, i, j);
            }
        }
    }

    private void addTextField(Stage stage, int i, int j) {
        int part = (int) Math.sqrt(manager.getSudokuRowsCount());
        TextField textField = new TextField();
        if (i > 0 && i % part == 0 && j > 0 && j % part == 0) {
            textField.getStyleClass().add(NamespaceEnum.RIGHT_UP_STYLE.getText());
        } else if (i > 0 && i % part == 0) {
            textField.getStyleClass().add(NamespaceEnum.RIGHT_STYLE.getText());
        } else if (j > 0 && j % part == 0) {
            textField.getStyleClass().add(NamespaceEnum.UP_STYLE.getText());
        }
        textField.setPrefSize(stage.getWidth() / manager.getSudokuColumnsCount(),
                stage.getHeight() / manager.getSudokuRowsCount());
        textField.setAlignment(Pos.CENTER);
        setTextFieldListeners(textField);
        textFields.add(textField);
        pane.add(textField, i, j);
    }

    private void setTextFieldListeners(TextField textField) {
        textPropertyListener(textField);
        keyPressedListener(textField);
    }

    private void keyPressedListener(TextField textField) {
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
                case ALT:
                    inputKeys[0] = KeyCode.ALT;
                    break;
                case ADD:
                    inputKeys[1] = KeyCode.ADD;
                    break;
                case N:
                    inputKeys[1] = KeyCode.N;
                    break;
                default:
                    inputKeys[0] = null;
                    inputKeys[1] = null;
            }
            loadFile();
            showResult();
        });
    }

    private void loadFile() {
        if (KeyCode.ALT.equals(inputKeys[0]) && KeyCode.N.equals(inputKeys[1])) {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(null);
            manager.getSudoku().loadSudoku(file);
            loadSudoku();
            inputKeys[0] = null;
            inputKeys[1] = null;
        }
    }

    private void textPropertyListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 0) {
                textField.textProperty().set(String.valueOf(newValue.charAt(newValue.length() - 1)));
            }
        });
    }

    private void showResult() {
        if (KeyCode.ALT.equals(inputKeys[0]) && KeyCode.ADD.equals(inputKeys[1])) {
            manager.getSudoku().process();
            String[] data = manager.getSudoku().output();
            for (int i = 0; i < data.length; i++) {
                textFields.get(i).setText(data[i]);
            }
            inputKeys[0] = null;
            inputKeys[1] = null;
        }
    }

    private void setNewValue(TextField textField, int value, KeyEvent evt) {
        textFields.get(textFields.indexOf(textField) + value).requestFocus();
        evt.consume();
    }
}
