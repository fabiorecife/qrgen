package net.fabioalmeida.qrgen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class Qrgen {
	
	String[] args ;
	private Options options;
	private HelpFormatter formatter;
	private CommandLine cmd;
	String arquivo;
	String saida;
	String formato = "jpg";
	String tamanho = "160,160";
	String separador = "\t";
	String coluna ;
	String regex = "\\D+";
	String substituir = "";
	
	public Qrgen(String[] args) {
		this.args = args;
	}
	
	public void execute() throws ParseException, IOException{
		createOptions();
		
		if (! hasArgs()) {
			printAjuda();
			return ;
		}
		createCmd();

		if (cmd.hasOption("ajuda") || ! ( cmd.hasOption("arquivo") && cmd.hasOption("saida"))) {
			printAjuda();
			return;
		}
		
		arquivo = cmd.getOptionValue("arquivo");
		saida = cmd.getOptionValue("saida");
		formato = cmd.getOptionValue("formato", "jpg");
		separador = cmd.getOptionValue("separador", "\t");
		tamanho = cmd.getOptionValue("tamanho", "160,160");
		coluna = cmd.getOptionValue("coluna", "0");
		regex = cmd.getOptionValue("regex", "\\D+");
		substituir = cmd.getOptionValue("substituir", "");
		
		doExecute();
		
		
	}

	private void doExecute() throws IOException {
		List<String> amostra = Files.readAllLines(Paths.get(arquivo));
		int icol = Integer.valueOf(coluna);
		ImageType imageType = formato.equals("jpg") ? ImageType.JPG : ImageType.PNG; 
		String[] xy = tamanho.split(",");
		int x = Integer.valueOf(xy[0]);
		int y = Integer.valueOf(xy[1]);
		boolean firstLine = true;
		System.out.println("Gerando:");
		for (String linha : amostra) {
			if (! firstLine) {
				String[] cols = linha.split(separador);
				String cpf = cols[icol].replaceAll(regex,substituir);
				System.out.println(cpf+".jpg");
				File file = Paths.get(saida,cpf+".jpg").toFile();
				try (FileOutputStream output = new FileOutputStream(file)) {
					QRCode.from(cpf).to(imageType).withSize(x,y).writeTo(output);
				}
			} else {
				firstLine = false;
			}
		}	
		System.out.println("Arquivos gravados em:" + saida);
	}

	private boolean hasArgs() {
		return args.length != 0;
	}

	private void createCmd() throws ParseException {
		CommandLineParser parser = new DefaultParser();
		cmd = parser.parse( options, args);
	}

	private void printAjuda() {
		formatter = new HelpFormatter();
		formatter.printHelp( "qrgen\r\n(obs: a primeira linha é ignorada)\r\nversão:1.0.0", options );
	}

	private void createOptions() {
		options = new Options();
		options.addOption("arquivo", true, "(obrigatório) arquivo com a amostra a ser gerada pelo qr-code");
		options.addOption("saida", true, "(obrigatório) diretorio de saida dos arquivos");
		options.addOption("separador", true, "separador usado o default é tab");
		options.addOption("formato", true, "formato de saida:jpg, png");
		options.addOption("regex", true, "expressão regular para selecionar texto a ser substituido");
		options.addOption("substituir", true, "valor que substitui a seleção da expressao regular");
		options.addOption("tamanho", true, "tamanho em pixel, usar x,y, exemplo: 160,160");
		options.addOption("coluna", true, "coluna com o valor ser colocado no qr-code e nome do arquivo");
		options.addOption("ajuda", false, "ajuda");
	}

	public static void main(String[] args) throws ParseException, IOException {
		
		Qrgen app = new Qrgen(args);
		app.execute();
		
	}
	//-arquivo /Users/fabio/Downloads/amostra.tsv -saida /Users/fabio/Downloads/qr-codes
	
}
