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

    // Variáveis para edição de dentista
    @FXML
    private TextField nameInput1;

    @FXML
    private TextField cpfInput1;

    @FXML
    private TextField passwordInput1;
   
    @FXML
    private Label warnLabel1;
    
    @FXML
    private TextField filterField;
	
    @FXML
    private TextField filterField2;
    
    @FXML
    private Label notFoundLabel;
    
    @FXML
    private Label notFoundLabel2;
    
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
    void appointmentFilter(ActionEvent event) {
		String valueSearched = filterField.getText().toLowerCase();
		
		SecretaryController sc = new SecretaryController();
		
		Object obj2 = sc.getAppointmentFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
		JSONArray appointmentArray = (JSONArray) jsonObject.get("appointments"); // obtem array de pacientes			
					
		ArrayList<Object> listAppointments = new ArrayList<>();		
		ArrayList<Consulta> c = new ArrayList<>();
		ObservableList<Consulta> appointments = tableView.getItems();
		
		listAppointments.addAll(appointmentArray);	
		
		String name, schedule, date, type;
		
		Consulta consulta;
		
		Iterator<Object> iterator = appointmentArray.iterator();
		
		for(int i = 0; i < appointmentArray.size(); i++) {
			
			Object obj = (Object) iterator.next();
			JSONObject jsonObj = (JSONObject) obj;
			name = (String) jsonObj.get("name");
			schedule = (String) jsonObj.get("schedule");
			date = (String) jsonObj.get("date");
			type = (String) jsonObj.get("type");
						
			if( name.toLowerCase().equals(valueSearched) ||
				schedule.toLowerCase().equals(valueSearched) ||
				date.toLowerCase().equals(valueSearched) ||
				type.toLowerCase().equals(valueSearched)) 
			{
					consulta = new Consulta(name, "", "", "", "", schedule, date, type);				
					c.add(consulta);
			}
		}
		
		if(c.size() != 0) {
			tableView.getItems().clear();
			appointments.addAll(c);
			tableView.setItems(appointments);
		}
		else {
			notFoundLabel.setText("Desculpe, não encontramos nenhum resultado para a sua pesquisa");
			notFoundLabel.setVisible(true);
		}
		
		filterField.clear();
    }
    
    
    @SuppressWarnings("unchecked")
	@FXML
    void historyFilter(ActionEvent event) {
    	String valueSearched = filterField2.getText().toLowerCase();
		
		Object obj2 = getHistoryFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
		JSONArray appointmentArray = (JSONArray) jsonObject.get("history"); // obtem array de pacientes			
					
		ArrayList<Object> listHistories = new ArrayList<>();		
		ArrayList<Consulta> hc = new ArrayList<>();
		ObservableList<Consulta> histories = tableView1.getItems();
		
		listHistories.addAll(histories);	
		
		String name, schedule, date, type;
		
		Consulta consulta;
		
		Iterator<Object> iterator = appointmentArray.iterator();
		
		for(int i = 0; i < appointmentArray.size(); i++) {
			
			Object obj = (Object) iterator.next();
			JSONObject jsonObj = (JSONObject) obj;
			name = (String) jsonObj.get("name");
			schedule = (String) jsonObj.get("schedule");
			date = (String) jsonObj.get("date");
			type = (String) jsonObj.get("type");
						
			if( name.toLowerCase().equals(valueSearched) ||
				schedule.toLowerCase().equals(valueSearched) ||
				date.toLowerCase().equals(valueSearched) ||
				type.toLowerCase().equals(valueSearched)) 
			{
					consulta = new Consulta(name, "", "", "", "", schedule, date, type);				
					hc.add(consulta);
			}
		}
		
		if(hc.size() != 0) {
			tableView1.getItems().clear();
			histories.addAll(hc);
			tableView1.setItems(histories);
		}
		else {
			notFoundLabel2.setText("Desculpe, não encontramos nenhum resultado para a sua pesquisa");
			notFoundLabel2.setVisible(true);
		}
		
		filterField2.clear();
    }
    
	@SuppressWarnings("unchecked")
	@FXML
	void registerSecretary(ActionEvent event) throws IOException {		
		
		if(nameInput.getText().equals("") || cpfInput.getText().equals("")
				|| phoneNumberInput.getText().equals("") || emailInput.getText().equals("")
				|| passwordInput.getText().equals("")) {
			
			warnLabel.setStyle("-fx-text-fill: red;");
			warnLabel.setText("Por favor, preencha todos os campos!");
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
	
	@SuppressWarnings("unchecked")
	@FXML
    void registerDentist(ActionEvent event) {
		if(nameInput1.getText().equals("") || cpfInput1.getText().equals("")				
				|| passwordInput1.getText().equals("")) {
			
			warnLabel1.setStyle("-fx-text-fill: red;");
			warnLabel1.setText("Por favor, preencha todos os campos!");
			warnLabel1.setVisible(true);
			
		} else {
			Dentist dentist = new Dentist(nameInput1.getText(), cpfInput1.getText(),
					passwordInput1.getText());
			
			JSONObject obj = new JSONObject();
    		
    		obj.put("name", dentist.getName());
    		obj.put("CPF", dentist.getCpf());
    		obj.put("password", dentist.getPassword());
    		
    		// Criar um novo método para isso
    		try (FileWriter file = new FileWriter("src/files/dentist.json")) {
    			file.write(obj.toString());
    			file.flush();
    		}
    		catch (IOException e) {
    			e.printStackTrace();
    		}		
    		
    		nameInput1.clear();
			cpfInput1.clear();
			passwordInput1.clear();
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
		
		int selectedID = tableView.getSelectionModel().getSelectedIndex();
		
		if(selectedID != -1) {
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
			fillOutHistoryTableView();
		}
    }
	
	@SuppressWarnings("unchecked")
	public void fillOutAppointmentTableView() {
		Consulta consulta;
		
		SecretaryController sc = new SecretaryController();
		
		Object obj = sc.getAppointmentFromFile();
		JSONObject jsonObject = (JSONObject) obj;
		JSONArray appointmentArray = (JSONArray) jsonObject.get("appointments");
		
		Iterator<Object> iterator = appointmentArray.iterator();
		
		tableView.getItems().clear();
		
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
	        notFoundLabel.setVisible(false);
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
		
		tableView1.getItems().clear();
		
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
	        notFoundLabel2.setVisible(false);
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
		
		if(selectedID != -1) {
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
			fillOutHistoryTableView(); 
		}
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
	
	@FXML
    void updateDentist(ActionEvent event) {
		JSONParser parser = new JSONParser();
		
		String name = "";
		String cpf = "";
		String password = "";
		
		try {
			Object obj = parser.parse(new FileReader("src/files/dentist.json"));
			JSONObject jsonObject = (JSONObject) obj;
			name = (String) jsonObject.get("name");
			cpf = (String) jsonObject.get("CPF");
			password = (String) jsonObject.get("password");
		}
		catch(FileNotFoundException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }
		
		nameInput1.setText(name);
		cpfInput1.setText(cpf);
		passwordInput1.setText(password);
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
