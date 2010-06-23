package ufrpe;

import ufrpe.atividades.Corretor;

public class Main {

	public static void main(String[] args) {
		if (args.length > 0) {
			if (args[0].equals("-c")) {
				if (args.length == 3) {
					gerarXlsCorrecoes(args[1], args[2]);
				} else {
					throw new RuntimeException(
							"O parametro -c precisa de 2 argumentos: <gabarito> <respostas.xls>");
				}
			}
		} else {
			System.out
					.println("nenhum paramentro passado:\n os parametros aceitos são: -c <gabarito> <respostas.xls>\n -e <arquivo xls com notas e dos alunos>");
		}
	}

	public static void gerarXlsCorrecoes(String gabarito, String respostas) {
		Corretor corretor = new Corretor();
		corretor.setGabaritoFilePath(gabarito);
		corretor.setRespostasFilePath(respostas);
		corretor.setCorrecaoFilePath("CORRECAO_" + respostas);

		corretor.gerarXlsCorrecoes();
	}

}
