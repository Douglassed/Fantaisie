package livre;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.NavigableSet;
import java.util.SortedSet;
import java.util.TreeSet;

import attaque.Griffe;
import attaque.PicsDeGlace;
import bataille.Bataille;
import protagoniste.Domaine;
import protagoniste.Heros;
import protagoniste.Homme;
import protagoniste.Monstre;


public class AideEcrivain {
	Bataille bataille;
	NavigableSet<Monstre<?>> monstresDeFeu = new TreeSet<>();
	NavigableSet<Monstre<?>> monstresDeGlace = new TreeSet<>();
	NavigableSet<Monstre<?>> monstresTranchants = new TreeSet<>();

	
	NavigableSet<Monstre<?>> monstresDomainesSet = 
			new TreeSet<Monstre<?>>(
					new Comparator<Monstre<?>>() {
						public int compare( Monstre<?> m1, Monstre<?> m2) {
							if (m1.getDomaine().compareTo(m2.getDomaine()) == 0){
								return m1.getNom().compareTo(m2.getNom());
							}
							return m1.getDomaine().compareTo(m2.getDomaine());
						}
					}
					);
	
	NavigableSet<Monstre<?>> monstresZoneSet = 
			new TreeSet<Monstre<?>>(
					new Comparator<Monstre<?>>() {
						public int compare( Monstre<?> m1, Monstre<?> m2) {
							if (m1.getZoneDeCombat().compareTo(m2.getZoneDeCombat()) == 0){
								if (m1.getForceDeVie() - (m2.getForceDeVie()) == 0) {
									return m1.getNom().compareTo(m2.getNom()); 
								}
								return (m2.getForceDeVie() - m1.getForceDeVie());
							}
							return m1.getZoneDeCombat().compareTo(m2.getZoneDeCombat());
						}
					}
					);
	public AideEcrivain(Bataille bataille) {
		this.bataille = bataille;
	}

	public LinkedList<Homme> visualiserForcesHumaines() {
		LinkedList<Homme> listeTriee = new LinkedList<>();
		int iHeros = 0;
		for (Object o : bataille.getCampHumains()) {
			if (o instanceof Heros) {
				listeTriee.add(iHeros,(Heros) o);
				iHeros++;
			}else {
				listeTriee.add((Homme) o);
			}
		}
		return listeTriee;
	}

	public String ordreNaturelMonstre() {
		String s = "";
		TreeSet<Monstre<?>> t = new TreeSet<>();
		for (Monstre<?> m : bataille.getCampMonstres()) {
			t.add(m);
		}
		for (Monstre<?> m : t) {
			s = s + m.getNom() + ", ";
		}
		if (s.length() > 1)
			s = s.substring(0, s.length() - 2);
		return s;
		/*String s = "";
		String ms = "";
		int index;
		boolean fini;
		Monstre<?> m;
		Iterator<?> iter = bataille.getCampMonstres().iterator();
		while(iter.hasNext()){
			fini = false;
			index = 0;
			m = (Monstre<?>) iter.next();
			ms = m.getNom() + ", ";
			while (s.substring(index).indexOf(", ") != -1 && !fini) {
				if (s.substring(index).compareTo(ms) > 0) {
					s = s.substring(0, index) + ms + s.substring(index);
					fini = true;
				}
				index = s.substring(index).indexOf(", ") + 2;
			}
			if (s.substring(index).indexOf(", ") == -1) {
				s = s + ms;
			}
		}
		return s.substring(0, s.length() - 2);*/
	}

	public void updateMonstreDomaine() {
		for (Monstre<?> m : bataille.getCampMonstres()) {
			monstresDomainesSet.add(m);
		}
	}

	public String ordreMonstreDomaine() {
		updateMonstreDomaine();
		String sMonstre = "";
		String sGlace = "", sTranchant = "";
		for(Monstre<?> m : monstresDomainesSet) {
			if (m.getDomaine().toString() == "FEU")
				sMonstre = sMonstre + m.getNom() + ", ";
			if (m.getDomaine().toString() == "GLACE") 
				sGlace = sGlace + m.getNom() + ", ";
			if (m.getDomaine().toString() == "TRANCHANT")
				sTranchant = sTranchant + m.getNom() + ", ";
		}
		if (sTranchant.length() > 1)
			sTranchant = sTranchant.substring(0, sTranchant.length() - 2);
		return "FEU :\n" + sMonstre + "\nGLACE :\n" + sGlace + "\nTRANCHANT :\n" + sTranchant;
	}

	public void updatemonstreZones() {
		for (Monstre<?> m : bataille.getCampMonstres()) {
			monstresZoneSet.add(m);
		}
	}
	
	public String ordreMonstreZone() {
		updatemonstreZones();
		String sAERIEN = "";
		String sAQUATIQUE = "", sTERRESTRE = "";
		for(Monstre<?> m : monstresZoneSet) {

			if (m.getZoneDeCombat().toString() == "AERIEN")
				sAERIEN = sAERIEN + m.getNom() + " : " + m.getForceDeVie() + ", ";
			if (m.getZoneDeCombat().toString() == "AQUATIQUE") 
				sAQUATIQUE = sAQUATIQUE + m.getNom() + " : " + m.getForceDeVie() + ", ";
			if (m.getZoneDeCombat().toString() == "TERRESTRE")
				sTERRESTRE = sTERRESTRE + m.getNom() + " : " + m.getForceDeVie() + ", ";
		}
		return "AERIEN :\n" + sAERIEN + "\nAQUATIQUE :\n" + sAQUATIQUE + "\nTERRESTRE :\n" + sTERRESTRE;
	}

	public NavigableSet<Monstre<?>> getMonstresDeFeu() {
		updateMonstreDomaine();
		return monstresDeFeu;
	}

	public NavigableSet<Monstre<?>> getMonstresDeGlace() {
		updateMonstreDomaine();
		return monstresDeGlace;
	}

	public NavigableSet<Monstre<?>> getMonstresTranchants() {
		updateMonstreDomaine();
		return monstresTranchants;
	}
	
	public Monstre<?> firstMonstreDomaine(Domaine domaine) {
		return monstresDomainesSet.ceiling(new Monstre<>("z", 0, null, domaine, null));
	}
	
	public void initMonstresDeFeu() {		
		monstresDeFeu = (NavigableSet<Monstre<?>>) monstresDomainesSet.headSet((new Monstre<>("a", 0, null, Domaine.GLACE, new PicsDeGlace(1))),false);
	}
	
	public void initMonstresDeGlace() {		
		monstresDeGlace = (NavigableSet<Monstre<?>>) monstresDomainesSet.subSet(new Monstre<>("a", 0, null, Domaine.GLACE, new PicsDeGlace(1)), true, new Monstre<>("", 0, null, Domaine.TRANCHANT, new Griffe()), false);
	}
	
	public void initMonstresTranchant() {		
		monstresTranchants = (NavigableSet<Monstre<?>>) monstresDomainesSet.tailSet(new Monstre<>("a", 0, null, Domaine.TRANCHANT, new Griffe()), true);
	}
	
	
	
}
