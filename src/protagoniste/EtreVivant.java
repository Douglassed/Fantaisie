package protagoniste;

import bataille.Bataille;

public abstract class EtreVivant implements Comparable<EtreVivant>{
	private String nom;
	protected int forceDeVie;
	protected Bataille bataille;
	
	public EtreVivant(int forceDeVie, String nom) {
		this.nom = nom;
		this.forceDeVie = forceDeVie;
		//bataille = new Bataille();
	}
	public String getNom() {
		return nom;
	}
	public int getForceDeVie() {
		return forceDeVie;
	}
	@Override
	public String toString() {
		return "EtreVivant [nom=" + nom + ", forceDeVie=" + forceDeVie + "]";
	}
	
	public void rejointBataille(Bataille bataille) {
		
	}
	
	public abstract void mourrir();
	
	public int compareTo(EtreVivant eVToCompare) {
		return this.nom.compareTo(eVToCompare.nom);
	}
	public boolean enVie() {
		if (forceDeVie > 0 )
			return true;
		else
			return false;
	}
	
}
