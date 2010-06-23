package ufrpe.entidades;

import java.util.ArrayList;

public class Questao {
	private String key;
	
	private String enunciado;
	private ArrayList<String> colunas;
	private String gabarito;
	
	public boolean equals(Object aThat){
		if ( this == aThat ) return true;
		if ( !(aThat instanceof Questao) ) return false;
		Questao that = (Questao)aThat;
		return this.key.equals(that.key);
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getEnunciado() {
		return enunciado;
	}
	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}
	public ArrayList<String> getColunas() {
		return colunas;
	}
	public void setColunas(ArrayList<String> colunas) {
		this.colunas = colunas;
	}
	public String getGabarito() {
		return gabarito;
	}
	public void setGabarito(String gabarito) {
		this.gabarito = gabarito;
	}
}
