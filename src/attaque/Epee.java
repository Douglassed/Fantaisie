package attaque;

import protagoniste.ZoneDeCombat;

public class Epee extends Arme{
	private String nomEpee;
	
	public Epee(String nomEpee) {
		super(80, nomEpee, ZoneDeCombat.AQUATIQUE, ZoneDeCombat.TERRESTRE);
		this.nomEpee = nomEpee;
	}

	@Override
	public String toString() {
		return "[nomEpee=" + nomEpee + ", pointDeDegat=" + getPointDeDegat() + "]";
	}

}
