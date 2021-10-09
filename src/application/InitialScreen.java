package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

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
	
	public void login(ActionEvent event) throws IOException {
		
		String cpf = cpfTextField.getText();
		String password = passwordField.getText();
		
		// se a checkBox estiver selecionada
		if(checkBox.isSelected()) {
								
			FileInputStream   file  = new FileInputStream("src/files/dentist.txt");
			InputStreamReader input = new InputStreamReader(file);
			BufferedReader    br    = new BufferedReader(input);
			
			String line;
			String[] words = new String[3];			
			
			for(int i = 0; i < 3; i++) {
				line = br.readLine();
				words[i] = line;				
			}
			
			if(cpf.equals(words[1]) && password.equals(words[2])) {
				root = FXMLLoader.load(getClass().getResource("/scenes/DentistaScene.fxml"));
			}
			else {				
				warnLabel.setText("CPF e/ou senha incorretos!");
				warnLabel.setVisible(true);
			}
			
			br.close();
		}
		else { // Se a checkBox não estiver selecionada			
		
			FileInputStream   file  = new FileInputStream("src/files/secretary.txt");
			InputStreamReader input = new InputStreamReader(file);
			BufferedReader    br    = new BufferedReader(input);			
			int ab = file.available();
			
			String line;
			String[] words = new String[5];			
			
			for(int i = 0; i < 5; i++) {
				line = br.readLine();
				words[i] = line;				
			}					
			
			if(ab == 0) {
				warnLabel.setText("Secretário(a) não cadastrodo(a)!");
				warnLabel.setVisible(true);
			}
			// É preciso realizar o cadastro do(a) secretário(a) ainda
			else if(cpf.equals(words[1]) && password.equals(words[4])) {
				root = FXMLLoader.load(getClass().getResource("/scenes/SecretaryScene.fxml"));
			}
			else {
				warnLabel.setText("CPF e/ou senha incorretos!");
				warnLabel.setVisible(true);
			}
			
			br.close();
		}
		
		if(root != null) {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
	}		
}
