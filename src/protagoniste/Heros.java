package protagoniste;

public class Heros extends Homme{

	public Heros(String nom) {
		super(nom);
		forceDeVie = 100;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Heros [getNom()=" + getNom() + ", getForceDeVie()=" + getForceDeVie() + "]";
	}

}
