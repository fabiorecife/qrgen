package net.fabioalmeida.qrgen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

public class Teste {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		List<String> amostra = Files.readAllLines(Paths.get(System.getProperty("user.home"),"Downloads","amostra.tsv" ));
		for (String linha : amostra) {
			if (!linha.startsWith("cpf")) {
				String[] cols = linha.split("\t");
				String cpf = cols[0].replaceAll("\\D+","");
				System.out.println(cpf);
				File file = Paths.get(System.getProperty("user.home"),"Downloads","qr-codes",cpf+".jpg").toFile();
				try (FileOutputStream output = new FileOutputStream(file)) {
					QRCode.from(cpf).to(ImageType.JPG).withSize(160, 160).writeTo(output);
				}
			}
		}
	}
}
