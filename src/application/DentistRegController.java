package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;

import org.json.simple.JSONObject;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("unused")
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
    
    @SuppressWarnings("unchecked")
	@FXML
    void registerDentist(ActionEvent event) throws IOException {    	    	
    	
    	if(nameInput.getText().equals("") || cpfInput.getText().equals("")
    			|| passwordInput.getText().equals("")) {
    		    	
    		warnLabel.setStyle("-fx-text-fill: red;");
    		warnLabel.setText("Preencha todos os campos!");
    		warnLabel.setVisible(true);
    		
    	}
    	else {    		    		
    		
    		Dentist dentist = new Dentist(nameInput.getText(),
    				cpfInput.getText(), passwordInput.getText());
    		
    		JSONObject obj = new JSONObject();
    		
    		obj.put("name", dentist.getName());
    		obj.put("CPF", dentist.getCpf());
    		obj.put("password", dentist.getPassword());
    		
    		try (FileWriter file = new FileWriter("src/files/dentist.json")) {
    			file.write(obj.toString());
    			file.flush();
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}
        	    		        
        	nameInput.clear();
        	cpfInput.clear();
        	passwordInput.clear();
        	    		
    		DentistController dc = new DentistController();
			dc.logout(event);
    	
    	}    	
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
