package ufrpe.atividades;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import ufrpe.entidades.Aluno;
import ufrpe.entidades.Correcao;

public class Corretor {

	public Corretor() {
	}

	private String gabaritoFilePath = "gabarito.properties";
	private String respostasFilePath = "respostas.xls";
	private String correcaoFilePath = "respostas_correcao.xls";

	public String getCorrecaoFilePath() {
		return correcaoFilePath;
	}

	public void setCorrecaoFilePath(String correcaoFilePath) {
		this.correcaoFilePath = correcaoFilePath;
	}

	public String getRespostasFilePath() {
		return respostasFilePath;
	}

	public void setRespostasFilePath(String respostasFilePath) {
		this.respostasFilePath = respostasFilePath;
	}

	public String getGabaritoFilePath() {
		return gabaritoFilePath;
	}

	public void setGabaritoFilePath(String gabaritoFilePath) {
		this.gabaritoFilePath = gabaritoFilePath;
	}

	// Le arquivo de propriedade e pega configuracao para corrigir atividades
	public TreeMap<String, String> getGabarito() {
		
		TreeMap<String, String> retorno = new TreeMap<String, String>();
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(gabaritoFilePath));
			Enumeration gabarito = properties.propertyNames();
			
			ArrayList<String> gabaritoKeys = new ArrayList<String>();
			while (gabarito.hasMoreElements()) {
				gabaritoKeys.add((String)gabarito.nextElement());
			}
			Collections.sort(gabaritoKeys);
			for (int i=0; i < gabaritoKeys.size(); i++) {
				retorno.put(gabaritoKeys.get(i), (String) properties.get(gabaritoKeys.get(i)));
				//String resposta = (String) properties.get(gabaritoKeys.get(i));
				//retorno.add(resposta);
			}
		} catch (IOException e) { e.printStackTrace(); }

		//Collections.reverse(retorno);
		return retorno;
	}
	
	public ArrayList<Aluno> getRespostas() {
		ArrayList<Aluno> alunos = new ArrayList<Aluno>();
		TreeMap<String, Integer> colunasInfos = getColunasInfos();
		TreeMap<String, Integer> colunasQuestoes = getColunasQeustoes();
		
		try {
			InputStream inp;
			inp = new FileInputStream(respostasFilePath);

			Workbook wb;
			wb = WorkbookFactory.create(inp);
			Sheet sheet1 = wb.getSheetAt(0);

			boolean primeira = true;
			for (Row row : sheet1) {
				if (primeira) { // cabecalho do xls
					primeira = false;
				} else {
					// testa se nao eh uma linha em branco e se o aluno colocou email
					if (!this.getAsString(row.getCell(colunasInfos.get(Aluno.DATA_HORA).intValue())).equals("")) {
						if(!this.getAsString(row.getCell(colunasInfos.get(Aluno.EMAIL_ALUNO).intValue())).equals("")){
							Aluno aluno = new Aluno();
							aluno.setData(this.getAsString(row.getCell(colunasInfos.get(Aluno.DATA_HORA).intValue())));
							aluno.setNome(this.getAsString(row.getCell(colunasInfos.get(Aluno.NOME_ALUNO).intValue())));
							aluno.setPrimeiroNome(this.getAsString(row.getCell(1)).split(" ")[0]);
							aluno.setEmail(this.getAsString(row.getCell(colunasInfos.get(Aluno.EMAIL_ALUNO).intValue())));

							TreeMap<String, String> respostas = new TreeMap<String, String>();
							
							for (String key: colunasQuestoes.keySet()) {
								respostas.put(key, this.getAsString(row.getCell(colunasQuestoes.get(key).intValue())));
							}
							aluno.setRespostas(respostas);
							alunos.add(aluno);
	
						} else {
							if (!this.getAsString(row.getCell(1)).equals("")){
								System.out.println("O aluno " + this.getAsString(row.getCell(1)) + " não colocou email e sua atividade nao foi corrigida");
							}
						}
					}
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return alunos;
	}
	
	//Le xls e pelo nome da colunas ele retorna qual coluna esta
	//data, nome, email
	private TreeMap<String, Integer> getColunasInfos() {
		TreeMap<String, Integer> colunas = new TreeMap<String, Integer>();

		try {
			InputStream inp;
			inp = new FileInputStream(respostasFilePath);

			Workbook wb;
			wb = WorkbookFactory.create(inp);
			Sheet sheet1 = wb.getSheetAt(0);
			
			//pega somenente a primeira linha.
			Row row0 = sheet1.getRow(0);
			for (int i = 0; i < row0.getLastCellNum(); i++) {
				if (this.getAsString(row0.getCell(i)).equals(Aluno.DATA_HORA)){
					colunas.put(Aluno.DATA_HORA, new Integer(i));
				} else if (this.getAsString(row0.getCell(i)).equals(Aluno.NOME_ALUNO)){
					colunas.put(Aluno.NOME_ALUNO, new Integer(i));
				} else if (this.getAsString(row0.getCell(i)).equals(Aluno.EMAIL_ALUNO)){
					colunas.put(Aluno.EMAIL_ALUNO, new Integer(i));
				} 
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return colunas;
	}
	
	// questos[1,2,3,4,5,5a,5b,5c,6,7, ...]
	private TreeMap<String, Integer> getColunasQeustoes() {
		TreeMap<String, Integer> questoes = new TreeMap<String, Integer>();
		String[] letras = {"a","b", "c", "d", "e"};
		int letrasIndice = 1;

		try {
			InputStream inp;
			inp = new FileInputStream(respostasFilePath);

			Workbook wb;
			wb = WorkbookFactory.create(inp);
			Sheet sheet1 = wb.getSheetAt(0);
			
			//pega somenente a primeira linha.
			Row row0 = sheet1.getRow(0);
			String questaoColuna = null;
			
			for (int i = 0; i < row0.getLastCellNum(); i++) {
				Pattern pattern = Pattern.compile("\\d+\\.");
				Matcher matcher = pattern.matcher(this.getAsString(row0.getCell(i)));
				
				if (matcher.find()) {
					String mached = matcher.group(0).replace(".", "");
					System.out.print("Start index: " + matcher.start());
					System.out.print("End index: " + matcher.end() + " ");
					System.out.println(mached);
					
					if (!questoes.containsKey(mached)){
						questoes.put(mached, new Integer(i));
						// leva em consideracao as questoes estao ordenadas
						if (questaoColuna != null) {
							letrasIndice = 1;
							questoes.put(questaoColuna + letras[0], questoes.get(questaoColuna));
							letrasIndice++;
							questoes.remove(questaoColuna);
							questaoColuna = null;
						}
					} else {
						questaoColuna = mached;
						questoes.put(mached + letras[letrasIndice], new Integer(i));
						letrasIndice++;
					}
					
					System.out.println("� uma questao (\\d.)\n" + row0.getCell(i));
				}
			}
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return questoes;
	}

	private String getAsString(Cell cell) {
		String retorno = "";
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				retorno = cell.getRichStringCellValue().getString();
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (DateUtil.isCellDateFormatted(cell)) {
					retorno = cell.getDateCellValue().toString();
				} else {
					retorno = String.valueOf(cell.getNumericCellValue());
				}
				break;
			default:
				System.out.println();
			}
		} else {
			retorno = "";
		}
		return retorno;
	}

	public ArrayList<Correcao> getCorrecoes() {
		ArrayList<Correcao> correcoes = new ArrayList<Correcao>();

		TreeMap<String, String> gabarito = getGabarito();
		ArrayList<Aluno> alunos = getRespostas();

		for (int i = 0; i < alunos.size(); i++) {
			Aluno aluno = alunos.get(i);
			Correcao correcao = new Correcao();
			correcao.setAluno(aluno);

			TreeMap<String, Double> correcoesMap = new TreeMap<String, Double>();
			
			for (String key : gabarito.keySet()) {
				String resposta = aluno.getRespostas().get(key);
				if (resposta.equals(gabarito.get(key))) {
					correcoesMap.put(key, new Double(1));
				} else {
					correcoesMap.put(key, new Double(0));
				}
			}
			correcao.setCorrecoes(correcoesMap);
			correcao.setNota(this.getNotaCorrecao(correcoesMap));
			correcoes.add(correcao);
		}
		return deletaAtividadeDuplicada(correcoes);
	}

	private double getNotaCorrecao(TreeMap<String, Double> correcoesMap) {
		//ArrayList<Double> keysQuestaoColuna = new ArrayList<Double>();
		TreeMap<String, ArrayList<Double>> questaoColuna = new TreeMap<String, ArrayList<Double>>();
		
		double total = 0.0;
		for (String key : correcoesMap.keySet()){
			Pattern pattern = Pattern.compile("(\\d+)(\\w)");
			Matcher matcher = pattern.matcher(key);
			
			if (matcher.find()) {
				if (!questaoColuna.containsKey(matcher.group(1))){
					questaoColuna.put(matcher.group(1), new ArrayList<Double>());
				}
				questaoColuna.get(matcher.group(1)).add(correcoesMap.get(key));
			} else {
				total += correcoesMap.get(key);
			}
		}
		//soma questoes colunas
		
		for (String key : questaoColuna.keySet()){
			ArrayList<Double> pontos = questaoColuna.get(key);
			double somaPontos = 0.0;
			for (Double ponto : pontos){
				somaPontos += ponto;
			}
			DecimalFormat decimal = new DecimalFormat( "0.00" );
			
			total += Double.parseDouble(decimal.format(somaPontos / pontos.size()).replace(",", "."));
		}
		return total;
	}

	// Deleta atividades de alunos que estejam duplicadas. Apaga a que tiver
	// a menor nota.
	private ArrayList<Correcao> deletaAtividadeDuplicada(
			ArrayList<Correcao> correcoes) {
		for (int i = 0; i < correcoes.size(); i++) {
			for (int j = i + 1; j < correcoes.size(); j++) {
				if (correcoes.get(i).equals(correcoes.get(j))) {
					if (correcoes.get(i).getNota() > correcoes.get(j).getNota()) {
						correcoes.remove(j);
					} else {
						correcoes.set(i, correcoes.get(j));
						correcoes.remove(j);
					}
				}
			}
		}
		return correcoes;
	}

	public void gerarXlsCorrecoes() {
		this.gerarXlsCorrecoes(this.correcaoFilePath);
	}

	public void gerarXlsCorrecoes(String xlsSaidaPath) {
		ArrayList<Correcao> correcoes = getCorrecoes();

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet1 = wb.createSheet("Correcao");

		for (int j = 0; j < correcoes.size(); j++) {
			Correcao correcao = correcoes.get(j);
			HSSFRow row = sheet1.createRow(j);
			row.createCell(0).setCellValue(correcao.getAluno().getData());
			row.createCell(1).setCellValue(correcao.getAluno().getNome());
			row.createCell(2).setCellValue(correcao.getAluno().getEmail());
			
			int i = 0;
			for (String key : correcao.getCorrecoes().keySet()) {
				row.createCell(correcoes.size() + i).setCellValue(
						correcao.getCorrecoes().get(key));
				i++;
			}
			// total
			row.createCell(row.getLastCellNum()).setCellValue(
					correcao.getNota());
		}
		// Write out the workbook
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(xlsSaidaPath);
			wb.write(fileOut);
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
