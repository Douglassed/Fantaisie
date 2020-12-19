package bataille;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import attaque.Arme;
import attaque.Pouvoir;
import protagoniste.Homme;
import protagoniste.Monstre;

public class GroupeHommes  {
	public TreeSet<Homme> groupe = new TreeSet<>(((h1, h2) -> {
		if (h1.getForceDeVie() != h2.getForceDeVie())
			return h2.getForceDeVie() - h1.getForceDeVie();
		return h1.compareTo(h2);}));

	public class ComparateurHommes implements Comparator<Homme>{
		@Override
		public int compare(Homme h1, Homme h2) {
			if (h1.getForceDeVie() != h2.getForceDeVie())
				return h2.getForceDeVie() - h1.getForceDeVie();
			return h1.getNom().compareTo(h2.getNom());
		}

	}

	public class ComparateurArmes implements Comparator<Arme>{
		private Monstre<? extends Pouvoir> monstre;
		public ComparateurArmes(Monstre<? extends Pouvoir> m) {
			monstre = m;
		}
		@Override
		public int compare(Arme a1, Arme a2) {
			if(a1.getPointDeDegat() != a2.getPointDeDegat()) {
				TreeMap<Integer,Arme> classementForce = new TreeMap<>();
				classementForce.put(a1.getPointDeDegat(), a1);
				classementForce.put(a2.getPointDeDegat(), a2);
				if (a2.getPointDeDegat() > a1.getPointDeDegat()) {
					if (a2.getPointDeDegat() <= monstre.getForceDeVie())
						return 1;
					else 
						return -1;
				}else if (a1.getPointDeDegat() <= monstre.getForceDeVie()){
					return -1;
				}else {
					return 1;
				}
			}
			return a2.getNom().compareTo(a1.getNom());
		}
	}

	public void ajouterHommes(Homme...hommes) {
		for(Homme h : hommes) {
			groupe.add(h);
		}
	}
	
	private void trieSet() {
		List<Homme> l = new ArrayList<>(groupe);
		groupe.clear();
		for(Homme h : l) {
			groupe.add(h);
		}
	}

	public List<Homme> choixCampHomme(Bataille bataille) {
		trieSet();
		Monstre<? extends Pouvoir> m = bataille.campMonstres.selectionner();
		TreeMap<Arme,TreeSet<Homme>> hommeArmes = new TreeMap<>(new ComparateurArmes(m));
		for(Homme h : groupe) {
			Arme a = h.choisirArme(m);
			if (! (a == null) ) {
				if (hommeArmes.containsKey(a)) 
					hommeArmes.get(a).add(h);
				else {
					hommeArmes.put(a, (new TreeSet<Homme>((h1, h2) -> {
						if (h1.getForceDeVie() != h2.getForceDeVie())
							return h2.getForceDeVie() - h1.getForceDeVie();
						return h1.compareTo(h2);})));
					hommeArmes.get(a).add(h);
				}
			}
		}
		List<Homme> ensembleHomme = new ArrayList<>();
		for(Arme arme : hommeArmes.keySet()){
			for(Homme homme : hommeArmes.get(arme)) {
				if (ensembleHomme.size() < 3){
					if (!ensembleHomme.contains(homme)) {
						ensembleHomme.add(homme);
						homme.rejointBataille(bataille);
					}
				}
			}
		}
		return ensembleHomme;
	}
	public void supprimerHomme(Homme h) {
		trieSet();
		if (!groupe.remove(h))
			System.out.println(h.getNom() + " ne fait pas partie du groupe des hommes.");
	}
	
	public boolean resteCombattant() {
		boolean hommeUtile = false;
		for(Iterator<Homme> it = groupe.iterator(); it.hasNext();) {
			Homme h = it.next();
			if (h.resteArmeUtile()) {
				hommeUtile = true;
			}else {
				it.remove();
			}
		}
		return hommeUtile;
	}
	
	public boolean estVide() {
		return groupe.isEmpty();
	}
}
