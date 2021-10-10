package application;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

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
		
	}
	
	@FXML
	void registerPacient(ActionEvent event) {
		
		if(nameInput.getText().equals("") || cpfInput.getText().equals("")
				|| addressInput.getText().equals("")
				|| phoneNumberInput.getText().equals("") || scheduleInput.getText().equals("")
				|| getDate().equals("") || getGenrer().equals("")) 
		{
			
			warnLabel.setText("Preencha todos os campos!");
			warnLabel.setVisible(true);
		} else {
			
			Paciente paciente = new Paciente(nameInput.getText(), cpfInput.getText(),
					addressInput.getText(), phoneNumberInput.getText(),
					scheduleInput.getText(), getDate(), typeInput.getText(),
					getGenrer());
			
	        ObservableList<Paciente> pacientes = tableView.getItems();
	        
	        pacientes.add(paciente);
	        tableView.setItems(pacientes);
	        
	        warnLabel.setVisible(false);
	        
	        // limpa os campos de entrada (os textFields)
	        nameInput.clear();
	        cpfInput.clear();
	        addressInput.clear();
	        phoneNumberInput.clear();
	        scheduleInput.clear();
	        typeInput.clear();
		}
	}
	
	@FXML
	void removePacient(ActionEvent event) {
		int selectedID = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().remove(selectedID);
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
	
	@FXML
    void logout(ActionEvent event) throws IOException {
		
		DentistController d = new DentistController();
		
		d.logout(event);
		
    }
	
	// FALTA IMPLEMENTAR:
	// 	salvar e recuperar dados da tabela de consulta a partir de um arquivo json

}
