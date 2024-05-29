module ucr.lab.practicabst_avl {
    requires javafx.controls;
    requires javafx.fxml;


    opens ucr.lab.practicabst_avl to javafx.fxml;
    exports ucr.lab.practicabst_avl;
    exports controller;
    opens controller to javafx.fxml;
}