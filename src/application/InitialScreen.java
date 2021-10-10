package application;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InitialScreen {
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	@FXML
	private CheckBox checkBox;
	@FXML
	private ImageView iconImageView;
	@FXML
	private Label userLabel;
	@FXML
	private Label warnLabel;
	@FXML
	private Button loginButton;
	@FXML
	private TextField cpfTextField;
	@FXML
	private PasswordField passwordField;
	
	Image image1 = new Image(getClass().getResourceAsStream("/img/dentist.png"));
	Image image2 = new Image(getClass().getResourceAsStream("/img/secretary.png"));	
	
	public void change(ActionEvent event) {
		
		if(checkBox.isSelected()) {
			userLabel.setText("Dentista");
			iconImageView.setImage(image1);
		}
		else {
			userLabel.setText("Secretário(a)");
			iconImageView.setImage(image2);
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	public void login(ActionEvent event) throws IOException {
		
		String cpfInput = cpfTextField.getText();
		String passwordInput = passwordField.getText();
		
		// se a checkBox estiver selecionada
		if(checkBox.isSelected()) {								
			
			JSONParser parser = new JSONParser();
			
			String cpf = "";
			String password = "";
			
			try {
				Object obj = parser.parse(new FileReader("src/files/dentist.json"));
				JSONObject jsonObject = (JSONObject) obj;
				cpf = (String) jsonObject.get("CPF");
				password = (String) jsonObject.get("password");
				
			}
			catch(FileNotFoundException e) { e.printStackTrace(); }
			catch(IOException e) { e.printStackTrace(); }
			catch(ParseException e) { e.printStackTrace(); }
			
			if(cpfInput.equals(cpf) && passwordInput.equals(password)) {
				root = FXMLLoader.load(getClass().getResource("/scenes/DentistaScene.fxml"));
			}
			else {				
				warnLabel.setText("CPF e/ou senha incorretos!");
				warnLabel.setVisible(true);
			}
			
		}
		else { // Se a checkBox não estiver selecionada			
		
			JSONParser parser = new JSONParser();
			
			String cpf = "";
			String password = "";
			boolean a = false;
			
			try {
				Object obj = parser.parse(new FileReader("src/files/secretary.json"));
				JSONObject jsonObject = (JSONObject) obj;
				cpf = (String) jsonObject.get("CPF");
				password = (String) jsonObject.get("password");
				a = ((HashMap) obj).isEmpty();
			}
			catch(FileNotFoundException e) { e.printStackTrace(); }
			catch(IOException e) { e.printStackTrace(); }
			catch(ParseException e) { e.printStackTrace(); }								
			
			
			if( a ) { // arquivo secretary está vazio
				warnLabel.setText("Secretário(a) não cadastrodo(a)!");
				warnLabel.setVisible(true);
			}
			else if(cpfInput.equals(cpf) && passwordInput.equals(password)) { // autenticalção
				root = FXMLLoader.load(getClass().getResource("/scenes/SecretaryScene.fxml"));
			}
			else {
				warnLabel.setText("CPF e/ou senha incorretos!");
				warnLabel.setVisible(true);
			}			
		}
		
		if(root != null) {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}		
}
