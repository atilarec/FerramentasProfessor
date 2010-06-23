package ufrpe.entidades;

import java.util.ArrayList;
import java.util.TreeMap;

public class Correcao {
	private Aluno aluno;
	private TreeMap<String, Double> correcoes = new TreeMap<String, Double>();
	private double nota;
	
	public Correcao() {}
	
	public String toString(){
		if (aluno.getEmail() != null && !aluno.getEmail().equals("")){
			return aluno.getEmail();
		}
		return "";
	}
	
	public boolean equals(Object obj){
		if (obj.toString() == this.toString()){
			return true;
		}
		return false;
	}
	
	public Correcao(Aluno aluno, TreeMap<String, Double> correcoes, double nota){
		this.aluno = aluno;
		this.correcoes = correcoes;
		this.nota = nota;
	}
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public TreeMap<String, Double> getCorrecoes() {
		return correcoes;
	}
	public void setCorrecoes(TreeMap<String, Double> correcoes) {
		this.correcoes = correcoes;
	}
	public double getNota() {
		return nota;
	}
	public void setNota(double nota) {
		this.nota = nota;
	}
}
