module AtenDent {
	requires javafx.controls;
	requires javafx.fxml;
	requires json.simple;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
