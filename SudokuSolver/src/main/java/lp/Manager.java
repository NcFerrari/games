package lp;

import lp.fe.MainApp;

public class Manager {

    private static Manager manager;

    public static Manager getInstance() {
        if (manager == null) {
            manager = new Manager();
            javafx.application.Application.launch(MainApp.class);
        }
        return manager;
    }

    private Manager() {
    }

    public static void main(String[] args) {
        getInstance();
    }
}
