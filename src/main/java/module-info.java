module me.croshaw.simulationspreadviraldisease {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.croshaw.simulationspreadviraldisease to javafx.fxml;
    exports me.croshaw.simulationspreadviraldisease;
}