package ufrpe.atividades;

import java.util.ArrayList;

import junit.framework.TestCase;
import ufrpe.entidades.Correcao;

public class EnviarCorrecaoTest extends TestCase {

	EnviarCorrecao enviarCorrecao;
	ArrayList<Correcao> correcoes;
	protected void setUp() throws Exception {
		super.setUp();
		
		Corretor corretor = new  Corretor();
		//corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		//corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		//corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabaritoExercicio3.properties");
		corretor.setRespostasFilePath("fixtures\\Exercício3.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio3_correcao.xls");
		
		enviarCorrecao = new EnviarCorrecao();
		correcoes = corretor.getCorrecoes();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testSeTextoEstaDeAcordoComTemplate(){
		String texto1 = "Caro Atila,\nsua nota da atividade 1 (Hardware de computador)\nfoi 4.0.";
		String texto2 = "Caro Joao,\nsua nota da atividade 1 (Hardware de computador)\nfoi 2.0.";
		String[] textos = new String[]{texto1,texto2};
		
		for(int i=0; i < correcoes.size(); i++) {
			String texto = enviarCorrecao.formatarTexto(correcoes.get(i));
			assertEquals(texto, textos[i]);
		}
	}
	
	public void testEnvioEmail(){
		for(int i=0; i < correcoes.size(); i++) {
			enviarCorrecao.enviar(correcoes.get(i));
		}
		
	}

}
