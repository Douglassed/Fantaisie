package attaque;

import java.util.HashSet;

import protagoniste.ZoneDeCombat;

public class Arme extends ForceDeCombat implements Orderable<Arme>{
	private HashSet<ZoneDeCombat> zonesDeCombat = new HashSet<>();

	public Arme(int pointDeDegat, String nom, ZoneDeCombat... zdc) {
		super(pointDeDegat, nom);
		for(ZoneDeCombat zdcc : zdc) {
			zonesDeCombat.add(zdcc);
		}
	}


	public HashSet<ZoneDeCombat> getZonesDeCombat() {
		return zonesDeCombat;
	}


	@Override
	public int compareTo(Arme a1) {
		if (a1.isOperationel() && this.isOperationel()) {
			if (this.getPointDeDegat() != a1.getPointDeDegat()) {
				return this.getPointDeDegat() - a1.getPointDeDegat();
			}
		}
		if (a1.isOperationel() && !this.isOperationel())
			return -1;
		if (!a1.isOperationel() && this.isOperationel())
			return 1;
		return this.getNom().compareTo(a1.getNom());
	}
	
}
