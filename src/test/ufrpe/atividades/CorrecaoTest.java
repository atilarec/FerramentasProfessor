package ufrpe.atividades;

import java.util.ArrayList;
import java.util.TreeMap;

import junit.framework.TestCase;
import ufrpe.entidades.Aluno;
import ufrpe.entidades.Correcao;

public class CorrecaoTest extends TestCase {

	Corretor corretor;
	
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	// testa se esta lendo o arquivo de gabarito e trasendo os dados corretamente
	public void testGabaritoCorreto(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		TreeMap<String, String> gabaritoCerto = new TreeMap<String, String> ();
		gabaritoCerto.put("1", "a");
		gabaritoCerto.put("2", "b");
		gabaritoCerto.put("3", "c");
		gabaritoCerto.put("4", "d");
		TreeMap<String, String> gabarito = corretor.getGabarito();
		
		//testa se a key existe
		for(String key: gabaritoCerto.keySet()){
			assertTrue(gabarito.keySet().contains(key));
		}
		
		//testa valores
		for(String key: gabaritoCerto.keySet()){
			assertTrue(gabarito.get(key).equals(gabaritoCerto.get(key)));
		}
	}
	
	// testa se quando eh questao de coluna ele coloca: 1a,1b,1c,1d ...
	public void testchaveNasQuestoesDeColunas(){
		
	}
	
	// testa se esta pegando respostas acetuadas corretamente
	public void testGabaritoCorretoComTextoEAcento(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabaritoComTextoEAcento.properties");
		corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		TreeMap<String, String> gabaritoCerto = new TreeMap<String, String> ();
		gabaritoCerto.put("3", "sistema binário");
		gabaritoCerto.put("4", "teclado, mouse, câmera");
		gabaritoCerto.put("5", "as respostas I e II estão corretas");
		TreeMap<String, String> gabarito = corretor.getGabarito();
		
		for(String key: gabaritoCerto.keySet()){
			assertTrue(gabarito.get(key).equals(gabaritoCerto.get(key)));
		}
	}
	
	
	//testa se esta lendo todos as celulas do xls corretamente, inclusive com as respostas
	public void testGetRespostas(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		Aluno aluno1 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "Atila", "atilarec@gmail.com");
		TreeMap<String, String> respostas1 = new TreeMap<String, String> ();
		respostas1.put("1", "a");
		respostas1.put("2", "b");
		respostas1.put("3", "c");
		respostas1.put("4", "d");
		aluno1.setRespostas(respostas1);

		Aluno aluno2 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "Joao", "atila.ufrpe@gmail.com");
		TreeMap<String, String> respostas2 = new TreeMap<String, String>();
		respostas2.put("1", "b");
		respostas2.put("2", "a");
		respostas2.put("3", "c");
		respostas2.put("4", "d");
		aluno2.setRespostas(respostas2);
		
		ArrayList<Aluno> alunosCorreto = new ArrayList<Aluno>();
		alunosCorreto.add(aluno1);
		alunosCorreto.add(aluno2);
		
		ArrayList<Aluno> alunos =  corretor.getRespostas();
		
		for(int i=0; i < 2; i++){
			Aluno aluno = alunos.get(i);
			Aluno alunoCorreto = alunosCorreto.get(i);
			System.out.println(aluno.getData());
			assertEquals(aluno.getData(), alunoCorreto.getData());
			assertEquals(aluno.getNome(), alunoCorreto.getNome());
			assertEquals(aluno.getEmail(), alunoCorreto.getEmail());
			
			for (String key : aluno.getRespostas().keySet()) {
				String respostaAluno = aluno.getRespostas().get(key);
				String respostaAlunoCorreto = alunoCorreto.getRespostas().get(key);
				assertEquals(respostaAluno, respostaAlunoCorreto);
			}
		}
		
	}
	// testa se esta pegando o primeiro nome do aluno corretamente
	public void testPegandoPrimeiroNome(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\pegandoPrimeiroNomeCorretamente.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		Aluno aluno1 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "Atila", "atilarec@gmail.com");
		aluno1.setPrimeiroNome("Atila");
		Aluno aluno2 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "Joao", "atila.ufrpe@gmail.com");
		aluno2.setPrimeiroNome("Joao");
		Aluno aluno3 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "Lucas", "atila.ufrpe@gmail.com");
		aluno3.setPrimeiroNome("Lucas");
		
		ArrayList<Aluno> alunosCorreto = new ArrayList<Aluno>();
		alunosCorreto.add(aluno1);
		alunosCorreto.add(aluno2);
		alunosCorreto.add(aluno3);
		
		ArrayList<Aluno> alunos =  corretor.getRespostas();
		
		for(int i=0; i < 2; i++){
			Aluno aluno = alunos.get(i);
			Aluno alunoCorreto = alunosCorreto.get(i);
			assertEquals(aluno.getPrimeiroNome(), alunoCorreto.getPrimeiroNome());
		}
	}
	
	
	//testa se nao nao esta trazendo linhas em branco e alunos sem email.
	public void testGetRespostasComLinhaEmBranco(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\respostasComLinhaEmBranco.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
			
		ArrayList<Aluno> alunos =  corretor.getRespostas();
		assertEquals(alunos.size(), 2);
	}
	
	// testa se esta corrigindo a atividade corretamente
	public void testGetCorrecao(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		Aluno aluno1 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "eu", "atilarec@gmail.com");
		TreeMap<String, String> respostas1 = new TreeMap<String, String>();
		respostas1.put("1", "a");
		respostas1.put("2", "b");
		respostas1.put("3", "c");
		respostas1.put("4", "d");
		aluno1.setRespostas(respostas1);
		
		TreeMap<String, Double> correcoes1 = new TreeMap<String, Double>();
		correcoes1.put("1", new Double(1));
		correcoes1.put("2", new Double(1));
		correcoes1.put("3", new Double(1));
		correcoes1.put("4", new Double(1));
		Correcao correcao1 = new Correcao(aluno1, correcoes1, 4);
		
		Aluno aluno2 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "tu", "atila.ufrpe@gmail.com");
		TreeMap<String, String> respostas2 = new TreeMap<String, String>();
		respostas2.put("1", "b");
		respostas2.put("2", "a");
		respostas2.put("3", "c");
		respostas2.put("4", "d");
		aluno2.setRespostas(respostas2);
		
		TreeMap<String, Double> correcoes2 = new TreeMap<String, Double>();
		correcoes2.put("1", new Double(0));
		correcoes2.put("2", new Double(0));
		correcoes2.put("3", new Double(1));
		correcoes2.put("4", new Double(1));
		Correcao correcao2 = new Correcao(aluno1, correcoes1, 2);
		
		ArrayList<Correcao> correcoesCorretas = corretor.getCorrecoes();
		correcoesCorretas.add(correcao1);
		correcoesCorretas.add(correcao2);
		
		ArrayList<Correcao> correcoes = corretor.getCorrecoes();
		
		for(int i=0; i < correcoes.size(); i++){
			Correcao correcao = correcoes.get(i);
			Correcao correcaoCorreta = correcoesCorretas.get(i);
			assertEquals(correcao.getNota(), correcaoCorreta.getNota());
			
			for(String key: correcao.getCorrecoes().keySet()){
				Double correcaoAluno = correcao.getCorrecoes().get(key);
				Double correcaoAlunoCorreto = correcaoCorreta.getCorrecoes().get(key);
				assertEquals(correcaoAluno, correcaoAlunoCorreto);
			}
		}
	}
	
	public void testGetCorrecaoSomenteUmaEntradaPorAluno(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		Aluno aluno1 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "eu", "atilarec@gmail.com");
		TreeMap<String, String> respostas1 = new TreeMap<String, String>();
		respostas1.put("1", "a");
		respostas1.put("2", "b");
		respostas1.put("3", "c");
		respostas1.put("4", "d");
		aluno1.setRespostas(respostas1);
		
		TreeMap<String, Double> correcoes1 = new TreeMap<String, Double>();
		correcoes1.put("1", new Double(1));
		correcoes1.put("2", new Double(1));
		correcoes1.put("3", new Double(1));
		correcoes1.put("4", new Double(1));
		Correcao correcao1 = new Correcao(aluno1, correcoes1, 4);
		
		Aluno aluno2 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "tu", "atila.ufrpe@gmail.com");
		TreeMap<String, String> respostas2 = new TreeMap<String, String>();
		respostas2.put("1", "b");
		respostas2.put("2", "a");
		respostas2.put("3", "c");
		respostas2.put("4", "d");
		aluno2.setRespostas(respostas2);
		
		ArrayList<Double> correcoes2 = new ArrayList<Double>();
		correcoes2.add(new Double(0));
		correcoes2.add(new Double(0));
		correcoes2.add(new Double(1));
		correcoes2.add(new Double(1));
		Correcao correcao2 = new Correcao(aluno1, correcoes1, 2);
		
		ArrayList<Correcao> correcoesCorretas = corretor.getCorrecoes();
		correcoesCorretas.add(correcao1);
		correcoesCorretas.add(correcao2);
		
		ArrayList<Correcao> correcoes = corretor.getCorrecoes();
		
		assertEquals(correcoes.size(), 2);
		
		int contadorAtila = 0;
		for(int i=0; i < correcoes.size(); i++){
			if (correcoes.get(i).getAluno().getNome().equals("Atila")){
				contadorAtila++;
				assertEquals(correcoes.get(i).getNota(), 4.0);
			}
		}
		assertEquals(1, contadorAtila);
	}
	
	// Verifica se quando o aluno nao coloca respostas ele nao ganha nota no item em branco e se nao da erro
	public void testGetCorrecaoAlunoSemResposta(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		corretor.setRespostasFilePath("fixtures\\alunoSemRespostas.xls");
		corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		Aluno aluno1 = new Aluno("Mon Jan 11 00:00:00 GMT-03:00 2010", "Atila", "atilarec@gmail.com");
		TreeMap<String,String> respostas1 = new TreeMap<String,String>();
		aluno1.setRespostas(respostas1);
		
		TreeMap<String,Double> correcoes1 = new TreeMap<String,Double>();
		correcoes1.put("1", new Double(0));
		correcoes1.put("2", new Double(0));
		correcoes1.put("3", new Double(0));
		correcoes1.put("4", new Double(0));
		Correcao correcao1 = new Correcao(aluno1, correcoes1, 4);
		
		ArrayList<Correcao> correcoesCorretas = corretor.getCorrecoes();
		correcoesCorretas.add(correcao1);
		
		ArrayList<Correcao> correcoes = corretor.getCorrecoes();
		
		assertEquals(correcoes.get(0).getNota(), 0.0);
	}
	
	// testa se as questoes que de relacionar colunas o calculo o ponto da questao � dividido por 1
	public void testCorrigirQuestoesDeRelacionarColunas(){
		
		
	}
	
	public void testSeIndexDeQuestoesCorreto(){
		
	}
	
	public void testgerarXlsCorrecoes(){
		corretor = new  Corretor();
		corretor.setGabaritoFilePath("fixtures\\gabarito");
		corretor.setRespostasFilePath("fixtures\\respostas.xls");
		corretor.setCorrecaoFilePath("fixtures\\respostas_correcao.xls");
		
		//corretor.setGabaritoFilePath("fixtures\\gabarito.properties");
		//corretor.setRespostasFilePath("fixtures\\exercicio1.xls");
		//corretor.setCorrecaoFilePath("fixtures\\exercicio1_correcao.xls");
		
		corretor.gerarXlsCorrecoes();
	}
	
}
