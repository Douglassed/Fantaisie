package attaque;

public abstract class ForceDeCombat{
	private int pointDeDegat;
	private String nom;
	protected boolean operationel;
	
	
	public ForceDeCombat(int pointDeDegat, String nom) {
		super();
		this.pointDeDegat = pointDeDegat;
		this.nom = nom;
		operationel = true;
	}
	
	
	public int getPointDeDegat() {
		return pointDeDegat;
	}

	public String getNom() {
		return nom;
	}
	
	public boolean isOperationel() {
		return operationel;
	}
	
	@Override
	public String toString() {
		return "[nom=" + nom + ", pointDeDegat=" + pointDeDegat + "]";
	}
	
	public int utiliser() {
			return pointDeDegat;
	}


}
