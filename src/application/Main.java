package application;

import java.io.File;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) {
		try {
			
			Parent root;
			
			if(hasDentist()) {
				root = FXMLLoader.load(getClass().getResource("/scenes/InitialScene.fxml"));
			}
			else {
				root = FXMLLoader.load(getClass().getResource("/scenes/DentistRegister.fxml"));				
			}
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean hasDentist() {
		File fileInput = new File("src/files/dentist.txt");
    	
    	if(fileInput.length() == 0) {
    		return false;
    	}
    	
    	return true;
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
