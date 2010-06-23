package ufrpe.atividades;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import ufrpe.entidades.Correcao;

public class EnviarCorrecao {

	String template = "Olá <NOME_ALUNO>,\nsua nota Exercício 3 - " +
	"Hardware de Computadores foi <NOTA>.\n\n" +
	"Atila Bezerra\n" +
	"disciplina Elementos de Informática - UFRPE";

	public String formatarTexto(Correcao correcao) {
		String texto = template;
		texto = texto.replace("<NOME_ALUNO>", correcao.getAluno().getPrimeiroNome());
		System.out.println(correcao.getAluno().getNome());
		texto = texto.replace("<NOTA>", String.valueOf(correcao.getNota()));
		return texto;
	}
	
	
	
	public String enviar(Correcao correcao) {
		SimpleEmail email = new SimpleEmail();
		try {
			email.setDebug(true);
			email.setHostName("smtp.gmail.com");
			email.setAuthentication("atila.ufrpe@gmail.com", "at180983");
			email.setSSL(true);
			email.addTo(correcao.getAluno().getEmail());
			email.setFrom("atila.ufrpe@gmail.com");
			email.setSubject("Nota da atividade");
			email.setMsg(this.formatarTexto(correcao));
			email.send();
		} catch (EmailException e) { System.out.println(e.getMessage()); }
		
		return null;
	}

}
