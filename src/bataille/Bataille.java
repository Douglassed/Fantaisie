package bataille;

import attaque.Pouvoir;
import protagoniste.Homme;
import protagoniste.Monstre;

public class Bataille  {
	Camp<Homme> campHumains = new Camp<>();
	Camp<Monstre<? extends Pouvoir>> campMonstres = new Camp<>();

	public void ajouter(Homme homme) {
		campHumains.ajoute(homme);
	}
	public void ajouter(Monstre<? extends Pouvoir> monstre) {
		campMonstres.ajoute(monstre);
	}
	public void eliminer(Homme homme) {
		campHumains.elimine(homme);
	}
	public void eliminer(Monstre<? extends Pouvoir> monstre) {
		campMonstres.elimine(monstre);
	}
	public Camp<Homme> getCampHumains() {
		return campHumains;
	}
	public Camp<Monstre<? extends Pouvoir>> getCampMonstres() {
		return campMonstres;
	}

}
