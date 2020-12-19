package attaque;

import protagoniste.ZoneDeCombat;

public class Arc extends Arme{
	private int nbFlechesRestantes;

	public Arc(int nbFlechesRestantes) {
		super(50, "arc", ZoneDeCombat.AQUATIQUE, ZoneDeCombat.AERIEN, ZoneDeCombat.TERRESTRE);
		this.nbFlechesRestantes = nbFlechesRestantes;
	}
	
	public int utiliser() {
		if (operationel)
			nbFlechesRestantes = nbFlechesRestantes - 1;
		if (nbFlechesRestantes < 1) {
			operationel = false;
			return 0;
		}
		return super.utiliser();
	}

	
}
