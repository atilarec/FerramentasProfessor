package ufrpe.entidades;

import java.util.TreeMap;

public class Aluno {
	public static final String DATA_HORA = "Indicação de data e hora";
	public static final String NOME_ALUNO = "Nome do Aluno";
	public static final String EMAIL_ALUNO = "Email do Aluno";
	
	private String data;
	private String nome;
	private String primeiroNome;
	private String email;
	private TreeMap<String, String> respostas = new TreeMap<String, String>();
	private double nota;
	
	public Aluno(){}
	
	public Aluno(String data, String nome, String email){
		this.data = data;
		this.nome = nome;
		this.email = email;
	}
	
	public String getPrimeiroNome() {
		return primeiroNome;
	}

	public void setPrimeiroNome(String primeiroNome) {
		this.primeiroNome = primeiroNome;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public TreeMap<String, String> getRespostas() {
		return respostas;
	}
	public void setRespostas(TreeMap<String, String> respostas) {
		this.respostas = respostas;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
}
