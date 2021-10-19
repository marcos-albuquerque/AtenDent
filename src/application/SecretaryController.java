package application;

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
    private DatePicker dateInput;

    @FXML
    private TextField nameInput;

    @FXML
    private TextField cpfInput;

    @FXML
    private TextField addressInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField scheduleInput;

    @FXML
    private RadioButton rbFemale;

    @FXML
    private ToggleGroup sexo;

    @FXML
    private RadioButton rbMale;

    @FXML
    private RadioButton rbOther;      
     
    @FXML
    private TextArea typeInput;

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
    private TableColumn<Paciente, String> scheduleColumn;

    @FXML
    private TableColumn<Paciente, String> dateColumn;

    @FXML
    private TableColumn<Paciente, String> typeColumn;

    @FXML
    private TableColumn<Paciente, String> genreColumn;

    @FXML
    private TextField cpfSearchInput;

    @FXML
    private Button searchButton;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("name"));
		cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("address"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("phoneNumber"));
		scheduleColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("schedule"));
		dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		typeColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("type"));
		genreColumn.setCellValueFactory(new PropertyValueFactory<Paciente, String>("genre"));						
						
		fillOutTableView(); // preenche tableview com os dados do aquivo .json
	}
	
	@SuppressWarnings("unchecked")
	@FXML
	void registerPacient(ActionEvent event) {
		
		if(nameInput.getText().equals("") || cpfInput.getText().equals("")
				|| addressInput.getText().equals("")
				|| phoneNumberInput.getText().equals("") || scheduleInput.getText().equals("")
				|| getDate().equals("") || typeInput.getText().equals("") || getGenrer().equals("")) 
		{
			
			warnLabel.setText("Preencha todos os campos!");
			warnLabel.setVisible(true);
		} else {
			
			Paciente paciente = new Paciente(nameInput.getText(), cpfInput.getText(),
					addressInput.getText(), phoneNumberInput.getText(),
					scheduleInput.getText(), getDate(), typeInput.getText(),
					getGenrer());			
			
			Object obj2 = getPacientFromFile(); // Obtem pacientes do arquivo .json na forma de Object
			JSONObject jsonObject = (JSONObject) obj2; // converte objeto para JSONObject
			JSONArray pacienteArray = (JSONArray) jsonObject.get("pacientes"); // obtem array de pacientes			
				
			
			ArrayList<Object> listPacients = new ArrayList<>();
			
			if(pacienteArray != null) {
				
				// iteração pelo JSONArray
				for(int i = 0; i < pacienteArray.size(); i++) {
					
					// adiciona cada elemento do JSONArray ao ArrayList
					listPacients.add(pacienteArray.get(i));
				}
				
			}
			
																	
			JSONObject obj = new JSONObject();
			JSONObject consulta = new JSONObject();
			
			obj.put("name", paciente.getName());
			obj.put("CPF", paciente.getCpf());
			obj.put("address", paciente.getAddress());
			obj.put("phoneNumber", paciente.getPhoneNumber());
			obj.put("schedule", paciente.getSchedule());
			obj.put("date", paciente.getDate());
			obj.put("type", paciente.getType());
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
	        scheduleInput.clear();
	        typeInput.clear();
	        
	        
	        warnLabel.setVisible(false);
	        
	        // Atualiza tabela
	        tableView.getItems().clear(); // limpa tabela	        	       
			fillOutTableView(); // preenche a tabela novamento com os dados atuais
		}
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
		
		if(pacienteArray != null) {
			
			// iteração pelo JSONArray
			for(int i = 0; i < pacienteArray.size(); i++) {
				
				// adiciona cada elemento do JSONArray ao ArrayList
				listPacients.add(pacienteArray.get(i));
			}
			
		}				
		
		String cpf = "";
		
		Iterator<Object> iterator = pacienteArray.iterator();
		
		for(int i = 0; i < pacienteArray.size(); i++) {
			
			Object obj = (Object) iterator.next();
			JSONObject jsonObj = (JSONObject) obj;
			cpf = (String) jsonObj.get("CPF");
			
			if(cpf.equals(selectedCPF)) {
				System.out.printf("%d: %s\n", i, cpf);
				listPacients.remove(i);
			}
		}
		
//		int index = pacienteArray.indexOf(pacienteArray.get(selectedID));
//		listPacients.remove(index);
		
		System.out.println(pacienteArray.get(selectedID));
		System.out.println(selectedCPF);		
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.addAll(listPacients);
		
		JSONObject consulta = new JSONObject();
		consulta.put("pacientes", jsonArray);
		
		savePacientInFile(consulta);
				
		tableView.getItems().clear(); // limpa tabela	 
		fillOutTableView(); // preenche a tabela novamento com os dados atuais        
	}
	
	public void fillOutTableView() {
		
		Paciente paciente;
		
		Object obj = getPacientFromFile(); // Obtem pacientes do arquivo .json na forma de Object
		JSONObject jsonObject = (JSONObject) obj; // converte objeto para JSONObject
		JSONArray pacienteArray = (JSONArray) jsonObject.get("pacientes"); // obtem array de pacientes
		
		@SuppressWarnings("unchecked")
		Iterator<Object> iterator = pacienteArray.iterator();
		
		String name, cpf, address, phoneNumber, schedule, date, type, genre;
		
		for(int i = 0; i < pacienteArray.size(); i++) {
			
			Object obj2 = (Object) iterator.next();
			JSONObject jsonObj2 = (JSONObject) obj2;
			
			name = (String) jsonObj2.get("name");
			cpf = (String) jsonObj2.get("CPF");
			address = (String) jsonObj2.get("address");
			phoneNumber = (String) jsonObj2.get("phoneNumber");
			phoneNumber = (String) jsonObj2.get("phoneNumber");
			schedule = (String) jsonObj2.get("schedule");
			date = (String) jsonObj2.get("date");
			type = (String) jsonObj2.get("type");
			genre = (String) jsonObj2.get("genre");
			
			paciente = new Paciente(name, cpf, address, phoneNumber, schedule, date, type, genre);
			
			ObservableList<Paciente> pacientes = tableView.getItems();

	        pacientes.add(paciente);
	        tableView.setItems(pacientes);
			
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
	
	public Object getPacientFromFile() {
		
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
