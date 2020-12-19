package bataille;

import protagoniste.ZoneDeCombat;

public class Salle {
	private int numSalle;
	private ZoneDeCombat zoneDeCombat;
	
	public Salle(int numSalle, ZoneDeCombat zdc) {
		this.numSalle = numSalle;
		this.zoneDeCombat = zdc;
	}
	

	public int getNumSalle() {
		return numSalle;
	}

	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}
	
	public String toString() {
		return "Salle n°" + numSalle + " de type combat " + zoneDeCombat;
	}
}
