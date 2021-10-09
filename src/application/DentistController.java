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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DentistController {
	
	// Tabela de consultas
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

    
    // para cadastro de secretária
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
    
    
    //private static Formatter output;
    private static FileOutputStream file;
	
	
	@FXML
	void registerSecretary(ActionEvent event) throws IOException {
		
		openFileSecretary();
			
		
		if(nameInput.getText().equals("") || cpfInput.getText().equals("")
				|| phoneNumberInput.getText().equals("") || emailInput.getText().equals("")
				|| passwordInput.getText().equals("")) {
			
			warnLabel.setStyle("-fx-text-fill: red;");
			warnLabel.setText("Preencha todos os campos!");
			warnLabel.setVisible(true);	
			
		} else {
			
			Secretary secretary = new Secretary(nameInput.getText(), cpfInput.getText(),
					phoneNumberInput.getText(), emailInput.getText(), passwordInput.getText());
			
			try {
				PrintWriter pr = new PrintWriter(file);
				
				pr.printf("%s\n", secretary.getName());
				pr.printf("%s\n", secretary.getCpf());
				pr.printf("%s\n", secretary.getPhoneNumber());
				pr.printf("%s\n", secretary.getEmail());
				pr.printf("%s", secretary.getPassword());
				
				warnLabel.setStyle("-fx-text-fill: green;");
				warnLabel.setText("Cadastro realizado com sucesso!");
				warnLabel.setVisible(true);				
				
				pr.close();			
				
				nameInput.clear();
				cpfInput.clear();
				phoneNumberInput.clear();
				emailInput.clear();
				passwordInput.clear();
				
			}
			catch(FormatterClosedException formatterClosedException){
				System.err.println("Error writing to file. Terminating.");
			}
			catch (NoSuchElementException elementException)
			{
				System.err.println("Invalid input. Please try again.");
			}
			
		}
		
		file.close();
		
	}
		
	public void openFileSecretary() {	
		
		try
		{
			
			file = new FileOutputStream("src/files/secretary.txt");			
			
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
	
}
