package livre;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Fichier implements Livre{

	@Override
	public void ecrire(String s) {
//		try (InputStream input = new FileInputStream(in.txt);
//		OutputStream output = new FileOutputStream(out.txt)){
//			
//			byte[] buf = new byte[8192];
//			int len;
//			while((len=input.read(buf)) >= 0) {
//				output.write(buf, 0, len);
//			}catch (IOException e) {
//				System.err.println("Erreur");
//				e.printStackTrace();
//			}
//		}
		try {
			String chemin = "./src/livre/histoire.txt";
			File writer = new File(chemin);
			FileWriter fichier = null;
			try {
				fichier = new FileWriter(writer, true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			fichier.write(s);
			fichier.close();

		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}