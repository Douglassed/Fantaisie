package protagoniste;

import java.util.Arrays;
import java.util.Iterator;

import attaque.Pouvoir;
import bataille.Bataille;

public class Monstre<T extends Pouvoir> extends EtreVivant{
	T[] attaques;
	private ZoneDeCombat zoneDeCombat;
	private Domaine domaine;
	
	@SafeVarargs
	public Monstre(String nom, int forceDeVie, ZoneDeCombat zoneDeCombat , Domaine domaine, T... attaques) {
		super(forceDeVie, nom);
		this.domaine = domaine;
		this.zoneDeCombat = zoneDeCombat;
		this.attaques = attaques;
	}
	
	private class GestionAttaque implements Iterator<T> {
		private T[] attaquesPossibles = attaques;
		private int nbAttaquesPossibles = attaques.length;
		@Override
		public boolean hasNext() {
			if (nbAttaquesPossibles == 0)
				return false;
			
			if (!attaquesPossibles[0].isOperationel() ) {
				T temp = attaquesPossibles[0];
				attaquesPossibles[0] = attaquesPossibles[nbAttaquesPossibles - 1];
				attaquesPossibles[nbAttaquesPossibles - 1] = temp;
				nbAttaquesPossibles--;
			}
			return true;
		}

		public T next() {
			int rand = (int) (Math.random() * nbAttaquesPossibles);
			return attaquesPossibles[rand];
		}
		
	}
	
	public ZoneDeCombat getZoneDeCombat() {
		return zoneDeCombat;
	}

	public Domaine getDomaine() {
		return domaine;
	}
	
	public void entreEnCombat() {
		for(T attaque : attaques) {
			attaque.regenerPouvoir();
		}
		GestionAttaque ga = new GestionAttaque();
	}
	
	public T attaque() {
		GestionAttaque iter = new GestionAttaque();
		if (iter.hasNext()) {
			return iter.next();
		}
		return null;
	}
	
	public Iterator<?> iterator(){
		return new GestionAttaque();
	}

	@Override
	public String toString() {
		return "Monstre [getNom()=" + getNom() + ", attaques=" + Arrays.toString(attaques) + ", domaine=" + domaine
				+ ", forceDeVie=" + forceDeVie + "]";
	}

	public void rejointBataille(Bataille bataille) {
		bataille.ajouter(this);
		this.bataille = bataille;
	}
	
	public void mourrir() {
		bataille.eliminer(this);
	}
	
	public void subitAttaque(int pointDeDegat) {
		forceDeVie-= pointDeDegat;
		if (forceDeVie < 1) {
			mourrir();
		}
	}
	
	public boolean attaqueHomme(Homme h, Pouvoir p) {
		int degats = p.utiliser();
		boolean attaqueReussi = Math.random() < 0.5;
		if (attaqueReussi) {
			h.subitAttaque(degats);
		}
		return attaqueReussi;
	}
	
}
