package attaque;

public abstract class Pouvoir extends ForceDeCombat{
	private int nbUtilisationPouvoir;
	private int nbUtilisationPouvoirInitial;

	public Pouvoir(int pointDeDegat, String nom, int nbUtilisationPouvoir, int nbUtilisationPouvoirInitial) {
		super(pointDeDegat, nom);
		this.nbUtilisationPouvoir = nbUtilisationPouvoir;
		this.nbUtilisationPouvoirInitial = nbUtilisationPouvoirInitial;
	}

	public void regenerPouvoir() {
		nbUtilisationPouvoir = nbUtilisationPouvoirInitial;
	}

	public int utiliser() {
		if (nbUtilisationPouvoir < 2)
			super.operationel = false;
		nbUtilisationPouvoir = nbUtilisationPouvoir - 1;
		return 50;
	}
	public String afficherPouvoir() {
		StringBuilder affichage = new StringBuilder();
		if (!operationel)
			affichage.append("Il ne pourra plus utiliser " + getNom() + " pendant longtemps.");
		else
			affichage.append("Il peut encore utiliser ce pouvoir " + nbUtilisationPouvoir + " fois.");
		return affichage.toString();
	}
}
