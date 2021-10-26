package application;

public class Consulta extends Paciente {

	private String schedule; // horário
	private String date; // data 
	private String type; // tipo de consulta
		
	public Consulta(String name, String cpf, String address, String phoneNumber, String genre,
			String schedule, String date, String type) {
		super(name, cpf, address, phoneNumber, genre);
		this.schedule = schedule;
		this.date = date;
		this.type = type;
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