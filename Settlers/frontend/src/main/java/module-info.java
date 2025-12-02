module frontend {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires static lombok;

    exports cz.games.lp.frontend.api;
    exports cz.games.lp.frontend.components;
    exports cz.games.lp.frontend.panes;
    exports cz.games.lp.frontend.actions;
    exports cz.games.lp.frontend;
}