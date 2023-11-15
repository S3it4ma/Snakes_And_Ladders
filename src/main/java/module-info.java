module SnakesAndLadder {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.swing;
    requires javafx.base;
    requires javafx.media;
    requires javafx.graphics;
    requires javafx.web;
    requires javafx.swt;

    exports board to javafx.graphics;
    opens app;
}