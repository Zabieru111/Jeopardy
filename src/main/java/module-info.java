module zabieru.jeopardy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;


    opens zabieru.jeopardy to javafx.fxml;
    exports zabieru.jeopardy;
}