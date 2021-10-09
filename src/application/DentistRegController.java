package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DentistRegController {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
    private TextField nameInput;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField passwordInput;   
    
    @FXML
    private Label warnLabel;
	
    private static FileOutputStream file;
    
    @FXML
    void registerDentist(ActionEvent event) throws IOException {    	
    	
    	openFileDentist();
    	
    	if(nameInput.getText().equals("") || cpfInput.getText().equals("")
    			|| passwordInput.getText().equals("")) {
    		    	
    		warnLabel.setStyle("-fx-text-fill: red;");
    		warnLabel.setText("Preencha todos os campos!");
    		warnLabel.setVisible(true);
    		
    	}
    	else {
    		
    		Dentist dentist = new Dentist(nameInput.getText(),
    				cpfInput.getText(), passwordInput.getText());
        	
        	try {
        		
        		PrintWriter pw = new PrintWriter(file);    		
        		
        		pw.printf("%s\n", dentist.getName());
    			pw.printf("%s\n", dentist.getCpf());
    			pw.printf("%s", dentist.getPassword());
    			
    			pw.close();
        		
        	}
        	catch(FormatterClosedException formatterClosedException){
    			System.err.println("Error writing to file. Terminating.");
    		}
    		catch (NoSuchElementException elementException)
    		{
    			System.err.println("Invalid input. Please try again.");
    		}
        	
        	nameInput.clear();
        	cpfInput.clear();
        	passwordInput.clear();
        	    		
    		root = FXMLLoader.load(getClass().getResource("/scenes/InitialScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
    	
    	}
    	
    	file.close();
    }         
    
    public void openFileDentist() {	
		
		try
		{
			
			file = new FileOutputStream("src/files/dentist.txt");
			
			
		}
		catch (SecurityException securityException)
		{		
			System.err.println("Write permission denied. Terminating.");
			System.exit(1); 	 
		}
		catch (FileNotFoundException fileNotFoundException)
		{
			System.err.println("Error opening file. Terminating.");
			System.exit(1); 
		} 
	}    
    
}
