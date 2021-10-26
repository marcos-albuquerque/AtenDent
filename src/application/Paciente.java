package application;

public class Paciente {
	
	private String name;
	private String cpf;
	private String address;
	private String phoneNumber;
	private String genre;
	
	
	public Paciente(String name, String cpf, String address, String phoneNumber, String genre) {
		this.name = name;
		this.cpf = cpf;
		this.address = address;
		this.phoneNumber = phoneNumber;
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
				
	public String getGenre() {
		return genre;
	}
	
	public void setGenre(String genre) {
		this.genre = genre;
	}
		
}