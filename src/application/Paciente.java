package application;

public class Paciente {
	
	private String name;
	private String cpf;
	private String address;
	private String phoneNumber;
	private String schedule;
	private String date;
	private String type;
	private String genre;
	
	public Paciente(String name, String cpf, String address, String phoneNumber, String schedule,
			String date, String type, String genre) {
		super();
		this.name = name;
		this.cpf = cpf;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.schedule = schedule;
		this.date = date;
		this.type = type;
		this.genre = genre;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
		
}
