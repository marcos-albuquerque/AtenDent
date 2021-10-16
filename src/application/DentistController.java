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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class DentistController {
	
	// variáveis para Tabela de consultas
	@FXML
    private TableView<Paciente> tableView;

    @FXML
    private TableColumn<Paciente, String> nameColumn;

    @FXML
    private TableColumn<Paciente, String> scheduleColumn;

    @FXML
    private TableColumn<Paciente, String> dateColumn;

    @FXML
    private TableColumn<Paciente, String> typeColumn;

    
    // variáveis para cadastro de secretária
    @FXML
    private TextField nameInput;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField emailInput;

    @FXML
    private TextField passwordInput;
    
    @FXML
    private Label warnLabel;
        
    private static FileOutputStream file;
	
	
	@SuppressWarnings("unchecked")
	@FXML
	void registerSecretary(ActionEvent event) throws IOException {		
		
		if(nameInput.getText().equals("") || cpfInput.getText().equals("")
				|| phoneNumberInput.getText().equals("") || emailInput.getText().equals("")
				|| passwordInput.getText().equals("")) {
			
			warnLabel.setStyle("-fx-text-fill: red;");
			warnLabel.setText("Preencha todos os campos!");
			warnLabel.setVisible(true);	
			
		} else {
						
			Secretary secretary = new Secretary(nameInput.getText(), cpfInput.getText(),
					phoneNumberInput.getText(), emailInput.getText(), passwordInput.getText());
			
			JSONObject obj = new JSONObject();
    		
    		obj.put("name", secretary.getName());
    		obj.put("CPF", secretary.getCpf());
    		obj.put("phoneNumber", secretary.getPhoneNumber());
    		obj.put("email", secretary.getEmail());
    		obj.put("password", secretary.getPassword());
    		
    		try (FileWriter file = new FileWriter("src/files/secretary.json")) {
    			file.write(obj.toString());
    			file.flush();
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}		
    		
    		nameInput.clear();
			cpfInput.clear();
			phoneNumberInput.clear();
			emailInput.clear();
			passwordInput.clear();
		}
		
	}		
	
	@FXML
    void logout(ActionEvent event) throws IOException {
		Stage stage;
		Scene scene;
		Parent root;
		
		root = FXMLLoader.load(getClass().getResource("/scenes/InitialScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
    }
	
	// FALTA IMPLEMENTAR:
	// tabela de consultas
	//		buscar de arquivo json
	
	
}
