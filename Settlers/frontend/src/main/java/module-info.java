module frontend {

    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.desktop;
    requires static lombok;

    exports cz.games.lp.api;
    exports cz.games.lp;
    exports cz.games.lp.enums;
    exports cz.games.lp.components;
    exports cz.games.lp.panes;
    exports cz.games.lp.actions;
}