package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.FormatterClosedException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
       
    // Conclusão de consulta
    @FXML
    private TableView<Consulta> tableView1;

    @FXML
    private TableColumn<Consulta, String> nameColumn1;

    @FXML
    private TableColumn<Consulta, String> scheduleColumn1;

    @FXML
    private TableColumn<Consulta, String> dateColumn1;

    @FXML
    private TableColumn<Consulta, String> typeColumn1;

	
    @Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	// inicializa dados da tabela de consultas
    	nameColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("name"));
    	scheduleColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("schedule"));
    	dateColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("date"));
    	typeColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("type"));
    	
    	nameColumn1.setCellValueFactory(new PropertyValueFactory<Consulta, String>("name"));
    	scheduleColumn1.setCellValueFactory(new PropertyValueFactory<Consulta, String>("schedule"));
    	dateColumn1.setCellValueFactory(new PropertyValueFactory<Consulta, String>("date"));
    	typeColumn1.setCellValueFactory(new PropertyValueFactory<Consulta, String>("type"));
    	
    	fillOutAppointmentTableView();
    	fillOutHistoryTableView();
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
	@FXML
    void appointmentDone(ActionEvent event) {
		// pegar dados da tableview da consulta OK
		// salvar em arquivo		
		
		int selectedID = tableView.getSelectionModel().getSelectedIndex();		
		String selectedName = nameColumn.getCellData(selectedID);
		String selectedSchedule = scheduleColumn.getCellData(selectedID);
		String selectedDate = dateColumn.getCellData(selectedID);
		String selectedType = typeColumn.getCellData(selectedID);
		
		Consulta consulta = new Consulta(selectedName, "", "", "", "",
				selectedSchedule, selectedDate, selectedType);		

		Object obj2 = getHistoryFromFile(); // Obtem consultas do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
		JSONArray historyArray = (JSONArray) jsonObject.get("history"); // obtem array de consultas
	
		ArrayList<Object> listHistory = new ArrayList<>();
		
		listHistory.addAll(historyArray);
		
		JSONObject obj = new JSONObject();
		JSONObject history = new JSONObject();
		
		obj.put("name", consulta.getName());		
		obj.put("schedule", consulta.getSchedule());
		obj.put("date", consulta.getDate());
		obj.put("type", consulta.getType());
		
		listHistory.add(obj);
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(listHistory);
		
		history.put("history", jsonArray);
		
		saveHistoryInFile(history);
		
		tableView1.getItems().clear();     	       
		fillOutHistoryTableView();
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
	
	@SuppressWarnings("unchecked")
	public void fillOutHistoryTableView() {
		Consulta history;
		
		Object obj = getHistoryFromFile();
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray historyArray = (JSONArray) jsonObject.get("history");
		
		Iterator<Object> iterator = historyArray.iterator();
		
		String name, cpf, address, phoneNumber, genre, schedule, date, type;
		
		for(int i = 0; i < historyArray.size(); i++) {
			
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
			
			history = new Consulta(name, cpf, address, phoneNumber, genre, schedule, date, type);
			
			ObservableList<Consulta> histories = tableView1.getItems();

			histories.add(history);
	        tableView1.setItems(histories);
			
			System.out.printf("Name %d: %s\n", i, name);
			System.out.printf("CPF %d: %s\n", i, cpf);
		}
	}
	
	public void saveHistoryInFile( JSONObject history ) {
		try (FileWriter file = new FileWriter("src/files/history.json")) { 
			file.write(history.toString());
			file.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getHistoryFromFile() {
		File fileInputFile = new File("src/files/history.json");
		
		if(fileInputFile.length() == 0) {
		
			ArrayList<Object> listHistory = new ArrayList<>();
			
			JSONObject obj2 = new JSONObject();
			JSONObject history = new JSONObject();			
			
			listHistory.add(obj2);
			
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(listHistory);
							
			history.put("history", jsonArray);						
			
			// salva no arquivo
			saveHistoryInFile(history);
		}
		
		JSONParser parser = new JSONParser();				
		
		Object obj = null;
		
		try {
			obj = parser.parse(new FileReader("src/files/history.json"));			
			
		}
		catch(FileNotFoundException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }		
		
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	@FXML
    void removeHistory(ActionEvent event) {
		int selectedID = tableView1.getSelectionModel().getSelectedIndex();		
		String selectedName = nameColumn1.getCellData(selectedID);
		String selectedSchedule = scheduleColumn1.getCellData(selectedID);
		String selectedDate = dateColumn1.getCellData(selectedID);
		
		Object obj2 = getHistoryFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
		JSONArray historyArray = (JSONArray) jsonObject.get("history"); // obtem array de pacientes			
					
		ArrayList<Object> listHistory = new ArrayList<>();		
		
		listHistory.addAll(historyArray);
		
		String name = "";
		String schedule = "";
		String date = "";		
		
		Iterator<Object> iterator = historyArray.iterator();
	
		for(int i = 0; i < historyArray.size(); i++) {
			
			Object obj = (Object) iterator.next();
			JSONObject jsonObj = (JSONObject) obj;
			name = (String) jsonObj.get("name");
			schedule = (String) jsonObj.get("schedule");
			date = (String) jsonObj.get("date");
			
			if(name.equals(selectedName) && schedule.equals(selectedSchedule) && date.equals(selectedDate)) {
				listHistory.remove(i);
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(listHistory);
		
		JSONObject history = new JSONObject();
		history.put("history", jsonArray);
		
		saveHistoryInFile(history);
				
		tableView1.getItems().clear(); // limpa tabela 
		fillOutHistoryTableView(); 
    }
	
	@FXML
    void updateSecretary(ActionEvent event) {
		JSONParser parser = new JSONParser();
		
		String name = "";
		String cpf = "";
		String phone = "";
		String email = "";
		String password = "";
		
		try {
			Object obj = parser.parse(new FileReader("src/files/secretary.json"));
			JSONObject jsonObject = (JSONObject) obj;
			name = (String) jsonObject.get("name");
			cpf = (String) jsonObject.get("CPF");
			phone = (String) jsonObject.get("phoneNumber");
			email = (String) jsonObject.get("email");
			password = (String) jsonObject.get("password");
		}
		catch(FileNotFoundException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }
		
		nameInput.setText(name);
		cpfInput.setText(cpf);
		phoneNumberInput.setText(phone);
		emailInput.setText(email);
		passwordInput.setText(password);
    }
	
	public Object getSecretaryFromFile() {
		JSONParser parser = new JSONParser();				
		
		Object obj = null;
		
		try {
			obj = parser.parse(new FileReader("src/files/secretary.json"));			
			
		}
		catch(FileNotFoundException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }		
		
		return obj;
	}
		
}
