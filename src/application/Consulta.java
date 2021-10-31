package application;

public class Consulta {

	private Paciente paciente;
	private String schedule; // horário
	private String date; // data 
	private String type; // tipo de consulta
		
	public Consulta(String name, String cpf, String address, String phoneNumber, String genre,
			String schedule, String date, String type) {
		paciente = new Paciente(name, cpf, address, phoneNumber, genre);
		this.schedule = schedule;
		this.date = date;
		this.type = type;
	}
	
	public Consulta(Paciente paciente, String schedule, String date, String type) {
		this.paciente = paciente;
		this.schedule = schedule;
		this.date = date;
		this.type = type;
	}
	
	public String getName() {
		return paciente.getName();
	}
	
	public void setName(String name) {
		paciente.setName(name);
	}
	
	public String getCpf() {
		return paciente.getCpf();
	}
	
	public void setCpf(String cpf) {
		paciente.setCpf(cpf);
	}
	
	public String getPhoneNumber() {
		return paciente.getPhoneNumber();
	}
	
	public void setPhoneNumber(String phoneNumber) {
		paciente.setPhoneNumber(phoneNumber);
	}
	
	public String getGenre() {
		return paciente.getGenre();
	}
	
	public void setGenre(String genre) {
		paciente.setGenre(genre);
	}
	
	public String getAddress() {
		return paciente.getAddress();
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}