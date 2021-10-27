package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class SecretaryController implements Initializable {
	
	@FXML
	private Label warnLabel;
	
	@FXML
    private Button submitButton;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField phoneNumberInput;
    
    @FXML
    private ToggleGroup sexo;

    @FXML
    private RadioButton rbFemale;
    
    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbOther;
    
    @FXML
    private Button logoutButton;    

    @FXML
    private TableView<Paciente> tableView;

    @FXML
    private TableColumn<Paciente, String> nameColumn;

    @FXML
    private TableColumn<Paciente, String> cpfColumn;

    @FXML
    private TableColumn<Paciente, String> addressColumn;

    @FXML
    private TableColumn<Paciente, String> phoneColumn;

    @FXML
    private TableColumn<Paciente, String> genreColumn;

    @FXML
    private TextField cpfSearchInput;

    @FXML
    private Button searchButton;
    
    /* Consulta */
    
    @FXML
	private Label warnLabel2;
    
    @FXML
    private TableView<Consulta> tableView2;

    @FXML
    private TableColumn<Consulta, String> nameColumn2;

    @FXML
    private TableColumn<Consulta, String> scheduleColumn;

    @FXML
    private TableColumn<Consulta, String> dateColumn;

    @FXML
    private TableColumn<Consulta, String> typeColumn;
    
    @FXML
    private Label nameLabel;

    @FXML
    private Label cpfLabel;

    @FXML
    private Label addressLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private TextField scheduleInput;

    @FXML
    private DatePicker dateInput;

    @FXML
    private TextArea typeInput;
    
    @FXML
    private Tab regAppointmentTab;
    
    @FXML
    private Tab appointmentTab;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private Button regAppointment;
    
    @FXML
    private Button regAppointment1;

    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// inicializa dados da tabela de pacientes
		nameColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("name"));
		cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("address"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("phoneNumber"));
		genreColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("genre"));	
		
		// inicializa dados da tabela de consultas
		nameColumn2.setCellValueFactory(new PropertyValueFactory<Consulta, String>("name"));
		scheduleColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("schedule"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("date"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Consulta, String>("type"));
						
		fillOutPacientTableView(); // preenche tableview dos pacientes com os dados do aquivo .json
		fillOutAppointmentTableView(); // preenche tableview das consultas com os dados do aquivo .json
				
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	void registerPacient(ActionEvent event) {
			
		if(nameInput.getText().equals("") || cpfInput.getText().equals("")
				|| addressInput.getText().equals("") || phoneNumberInput.getText().equals("") 
				|| getGenrer().equals("")) 
		{
			
			warnLabel.setText("Preencha todos os campos!");
			warnLabel.setVisible(true);
		} else {
			
			Paciente paciente = new Paciente(nameInput.getText(), cpfInput.getText(),
					addressInput.getText(), phoneNumberInput.getText(),getGenrer());			
			
			Object obj2 = getPacientFromFile(); // Obtem pacientes do arquivo .json na forma de Object
			JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
			JSONArray pacienteArray = (JSONArray) jsonObject.get("pacientes"); // obtem array de pacientes							
			
			ArrayList<Object> listPacients = new ArrayList<>();
			
			listPacients.addAll(pacienteArray);	
																	
			JSONObject obj = new JSONObject();
			JSONObject consulta = new JSONObject();
			
			obj.put("name", paciente.getName());
			obj.put("CPF", paciente.getCpf());
			obj.put("address", paciente.getAddress());
			obj.put("phoneNumber", paciente.getPhoneNumber());
			obj.put("genre", paciente.getGenre());
			
			listPacients.add(obj);
			
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(listPacients);
			
			consulta.put("pacientes", jsonArray);						
			
			// salva no arquivo
			savePacientInFile(consulta);
	        
	        // limpa os campos de entrada (os textFields)
	        nameInput.clear();
	        cpfInput.clear();
	        addressInput.clear();
	        phoneNumberInput.clear();
	        	        
	        warnLabel.setVisible(false);
	        
	        // Atualiza tabela
	        tableView.getItems().clear(); // limpa tabela	        	       
	        fillOutPacientTableView(); // preenche a tabela novamento com os dados atuais
		}
	}
	
	@SuppressWarnings("unchecked")
	@FXML
    void registerAppointment(ActionEvent event) {
		
		// Se todos os campos não foram preenchidos
		if(scheduleInput.getText().equals("") || getDate().equals("") || typeInput.getText().equals("")) {
			warnLabel2.setText("Preencha todos os campos!");
			warnLabel2.setVisible(true);
		}
		else { // Se todos os campos foram preenchidos
			
			Consulta consulta = new Consulta(nameLabel.getText(), cpfLabel.getText(),
					addressLabel.getText(), phoneLabel.getText(), genreLabel.getText(),
					scheduleInput.getText(), getDate(), typeInput.getText());
			
			Object obj2 = getAppointmentFromFile(); // Obtem consultas do arquivo .json na forma de Object
			JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
			JSONArray appointmentArray = (JSONArray) jsonObject.get("appointments"); // obtem array de consultas
		
			ArrayList<Object> listAppointments = new ArrayList<>();
			
			listAppointments.addAll(appointmentArray);
			
			JSONObject obj = new JSONObject();
			JSONObject appointment = new JSONObject();
			
			obj.put("name", consulta.getName());
			obj.put("CPF", consulta.getCpf());
			obj.put("address", consulta.getAddress());
			obj.put("phoneNumber", consulta.getPhoneNumber());
			obj.put("genre", consulta.getGenre());
			obj.put("schedule", consulta.getSchedule());
			obj.put("date", consulta.getDate());
			obj.put("type", consulta.getType());
			
			listAppointments.add(obj);
			
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(listAppointments);
			
			appointment.put("appointments", jsonArray);						
			
			// salva no arquivo
			saveAppointmentInFile(appointment);
			
			// limpa os campos			
			warnLabel2.setVisible(false);
			scheduleInput.clear();
			typeInput.clear();
			
			// desativar a regAppointmentTab
			regAppointmentTab.setDisable(true);
			
			// trocar para a tab de Dados da contulta
			tabPane.getSelectionModel().select(appointmentTab);
			
			// Atualiza tabela
	        tableView2.getItems().clear(); // limpa tabela de consultas       	       
	        fillOutAppointmentTableView(); // preenche a tabela novamento com os dados atuais
		}
    }
	
	@FXML
    void changeTab(ActionEvent event) {
		// obter dados do paciente selecionado
		int selectedID = tableView.getSelectionModel().getSelectedIndex();
		
		String name = nameColumn.getCellData(selectedID);
		String cpf = cpfColumn.getCellData(selectedID);
		String address = addressColumn.getCellData(selectedID);
		String phone = phoneColumn.getCellData(selectedID);
		String genre = genreColumn.getCellData(selectedID);
		
		// preenche os campos do paciente
		nameLabel.setText(name);
		cpfLabel.setText(cpf);
		addressLabel.setText(address);
		phoneLabel.setText(phone);
		genreLabel.setText(genre);
		
		regAppointmentTab.setDisable(false);			
		tabPane.getSelectionModel().select(regAppointmentTab);

    }
	
	
	
	@FXML
    void logout(ActionEvent event) throws IOException {
		
		DentistController d = new DentistController();
		
		d.logout(event);
		
    }
	
	@SuppressWarnings("unchecked")
	@FXML
	void removePacient(ActionEvent event) {
		int selectedID = tableView.getSelectionModel().getSelectedIndex();
		
		String selectedCPF = cpfColumn.getCellData(selectedID);
		
		Object obj2 = getPacientFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
		JSONArray pacienteArray = (JSONArray) jsonObject.get("pacientes"); // obtem array de pacientes			
					
		ArrayList<Object> listPacients = new ArrayList<>();		
		
		listPacients.addAll(pacienteArray);
		
		String cpf = "";
		
		Iterator<Object> iterator = pacienteArray.iterator();
		
		for(int i = 0; i < pacienteArray.size(); i++) {
			
			Object obj = (Object) iterator.next();
			JSONObject jsonObj = (JSONObject) obj;
			cpf = (String) jsonObj.get("CPF");
			
			if(cpf.equals(selectedCPF)) {
				System.out.printf("%d: %s\n", i, cpf);
				if(selectedCPF != null)
					listPacients.remove(i);
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(listPacients);
		
		JSONObject consulta = new JSONObject();
		consulta.put("pacientes", jsonArray);
		
		savePacientInFile(consulta);
				
		tableView.getItems().clear(); // limpa tabela	 
		fillOutPacientTableView(); // preenche a tabela novamento com os dados atuais        
	}
	
	@SuppressWarnings("unchecked")
	@FXML
    void removeAppointment(ActionEvent event) {
		int selectedID = tableView2.getSelectionModel().getSelectedIndex();		
		String selectedName = nameColumn2.getCellData(selectedID);
		String selectedSchedule = scheduleColumn.getCellData(selectedID);
		String selectedDate = dateColumn.getCellData(selectedID);
		
		Object obj2 = getAppointmentFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
		JSONArray appointmentArray = (JSONArray) jsonObject.get("appointments"); // obtem array de pacientes			
					
		ArrayList<Object> listAppointments = new ArrayList<>();		
		
		listAppointments.addAll(appointmentArray);
		
		String name = "";
		String schedule = "";
		String date = "";
		
		System.out.println(selectedName);
		System.out.println(selectedSchedule);
		System.out.println(selectedDate);
		
		Iterator<Object> iterator = appointmentArray.iterator();
	
		for(int i = 0; i < appointmentArray.size(); i++) {
			
			Object obj = (Object) iterator.next();
			JSONObject jsonObj = (JSONObject) obj;
			name = (String) jsonObj.get("name");
			schedule = (String) jsonObj.get("schedule");
			date = (String) jsonObj.get("date");
			
			if(name.equals(selectedName) && schedule.equals(selectedSchedule) && date.equals(selectedDate)) {
				listAppointments.remove(i);
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(listAppointments);
		
		JSONObject consulta = new JSONObject();
		consulta.put("appointments", jsonArray);
		
		saveAppointmentInFile(consulta);
				
		tableView2.getItems().clear(); // limpa tabela 
		fillOutAppointmentTableView(); // preenche a tabela de consultas novamento com os dados atuais 
    }

	
	public void fillOutPacientTableView() {
		
		Paciente paciente;
		
		Object obj = getPacientFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj; // converte objeto para JSONObject
		JSONArray pacienteArray = (JSONArray) jsonObject.get("pacientes"); // obtem array de pacientes
		
		@SuppressWarnings("unchecked")
		Iterator<Object> iterator = pacienteArray.iterator();
		
		String name, cpf, address, phoneNumber, genre;
		
		for(int i = 0; i < pacienteArray.size(); i++) {
			
			Object obj2 = (Object) iterator.next();
			JSONObject jsonObj2 = (JSONObject) obj2;
			
			name = (String) jsonObj2.get("name");
			cpf = (String) jsonObj2.get("CPF");
			address = (String) jsonObj2.get("address");
			phoneNumber = (String) jsonObj2.get("phoneNumber");
			genre = (String) jsonObj2.get("genre");
			
			paciente = new Paciente(name, cpf, address, phoneNumber, genre);
			
			ObservableList<Paciente> pacientes = tableView.getItems();

	        pacientes.add(paciente);
	        tableView.setItems(pacientes);
			
			System.out.printf("Name %d: %s\n", i, name);
			System.out.printf("CPF %d: %s\n", i, cpf);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void fillOutAppointmentTableView() {
		Consulta consulta;
		
		Object obj = getAppointmentFromFile();
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
			
			ObservableList<Consulta> consultas = tableView2.getItems();

			consultas.add(consulta);
	        tableView2.setItems(consultas);
			
			System.out.printf("Name %d: %s\n", i, name);
			System.out.printf("CPF %d: %s\n", i, cpf);
		}
		
	}
	
	public void savePacientInFile( JSONObject consulta ) {
		try (FileWriter file = new FileWriter("src/files/pacients.json")) { 
			file.write(consulta.toString());
			file.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveAppointmentInFile( JSONObject consulta ) {
		try (FileWriter file = new FileWriter("src/files/appointments.json")) { 
			file.write(consulta.toString());
			file.flush();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Object getPacientFromFile() {
		
		File fileInputFile = new File("src/files/pacients.json");
		
		if(fileInputFile.length() == 0) {
		
			ArrayList<Object> listPacients = new ArrayList<>();
			
			JSONObject obj2 = new JSONObject();
			JSONObject consulta = new JSONObject();
			
			obj2.put("name", "");
			obj2.put("CPF", "");
			obj2.put("address", "");
			obj2.put("phoneNumber", "");
			obj2.put("genre", "");
			
			listPacients.add(obj2);
			
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(listPacients);
							
			consulta.put("pacientes", jsonArray);						
			
			// salva no arquivo
			savePacientInFile(consulta);
		}
		
		JSONParser parser = new JSONParser();				
		
		Object obj = null;
		
		try {
			obj = parser.parse(new FileReader("src/files/pacients.json"));			
			
		}
		catch(FileNotFoundException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }		
		
		return obj;
	}	
	
	@SuppressWarnings("unchecked")
	public Object getAppointmentFromFile() {
		File fileInputFile = new File("src/files/appointments.json");
		
		if(fileInputFile.length() == 0) {
		
			ArrayList<Object> listPacients = new ArrayList<>();
			
			JSONObject obj2 = new JSONObject();
			JSONObject consulta = new JSONObject();
			
			obj2.put("name", "");
			obj2.put("CPF", "");
			obj2.put("address", "");
			obj2.put("phoneNumber", "");
			obj2.put("genre", "");
			obj2.put("schedule", "");
			obj2.put("date", "");
			obj2.put("type", "");
			
			listPacients.add(obj2);
			
			JSONArray jsonArray = new JSONArray();
			jsonArray.addAll(listPacients);
							
			consulta.put("appointments", jsonArray);						
			
			// salva no arquivo
			saveAppointmentInFile(consulta);
		}
		
		JSONParser parser = new JSONParser();				
		
		Object obj = null;
		
		try {
			obj = parser.parse(new FileReader("src/files/appointments.json"));			
			
		}
		catch(FileNotFoundException e) { e.printStackTrace(); }
		catch(IOException e) { e.printStackTrace(); }
		catch(ParseException e) { e.printStackTrace(); }		
		
		return obj;
	}
	
	private String getDate() {
		LocalDate date = dateInput.getValue();
		return date.toString();
	}
	
	private String getGenrer() {
		String genrer = "";
		
		if(rbMale.isSelected()) {
			genrer = "Maculino";
		}
		else if(rbFemale.isSelected()) {
			genrer = "Feminino";
		}
		else {
			genrer = "Outro";
		}
		
		return genrer;
	}

}
