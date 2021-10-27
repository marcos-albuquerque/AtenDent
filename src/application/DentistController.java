package application;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.FormatterClosedException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@SuppressWarnings("unused")
public class DentistController implements Initializable {
	
	// variáveis para Tabela de consultas
	@FXML
    private TableView<Consulta> tableView;

    @FXML
    private TableColumn<Consulta, String> nameColumn;

    @FXML
    private TableColumn<Consulta, String> scheduleColumn;

    @FXML
    private TableColumn<Consulta, String> dateColumn;

    @FXML
    private TableColumn<Consulta, String> typeColumn;

    
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
	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	// inicializa dados da tabela de consultas
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("name"));
    	scheduleColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("schedule"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("date"));
    	typeColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("type"));
    	    
    	fillOutAppointmentTableView();
	}
    
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
    		
    		// Criar um novo método para isso
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
	
	
	@SuppressWarnings("unchecked")
	public void fillOutAppointmentTableView() {
		Consulta consulta;
		
		SecretaryController sc = new SecretaryController();
		
		Object obj = sc.getAppointmentFromFile();
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray appointmentArray = (JSONArray) jsonObject.get("appointments");
		
		Iterator<Object> iterator = appointmentArray.iterator();
		
		String name, cpf, address, phoneNumber, genre, schedule, date, type;
		
		for(int i = 0; i < appointmentArray.size(); i++) {
			
			Object obj2 = (Object) iterator.next();
			JSONObject jsonObj2 = (JSONObject) obj2;
			
			name = (String) jsonObj2.get("name");
			cpf = (String) jsonObj2.get("CPF");
			address = (String) jsonObj2.get("address");
			phoneNumber = (String) jsonObj2.get("phoneNumber");
			genre = (String) jsonObj2.get("genre");
			schedule = (String) jsonObj2.get("schedule");
			date = (String) jsonObj2.get("date");
			type = (String) jsonObj2.get("type");
			
			consulta = new Consulta(name, cpf, address, phoneNumber, genre, schedule, date, type);
			
			ObservableList<Consulta> consultas = tableView.getItems();

			consultas.add(consulta);
	        tableView.setItems(consultas);
			
			System.out.printf("Name %d: %s\n", i, name);
			System.out.printf("CPF %d: %s\n", i, cpf);
		}
		
	}
	
	
	// FALTA IMPLEMENTAR:
	// tabela de consultas
	//		buscar de arquivo json
	
	
}
