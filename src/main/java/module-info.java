module com.kirillpolyakov.printthreadsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;


    opens com.kirillpolyakov.printthreadsfx to javafx.fxml;
    exports com.kirillpolyakov.printthreadsfx;
    exports com.kirillpolyakov.printthreadsfx.controllers to javafx.fxml;
    opens com.kirillpolyakov.printthreadsfx.controllers to javafx.fxml;
}