package bataille;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import attaque.Pouvoir;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;
import protagoniste.ZoneDeCombatNonCompatibleException;

public class Grotte {
	private LinkedHashMap<Salle,List<Salle>> planGrotte = new LinkedHashMap<Salle,List<Salle>>();
	private HashMap<Salle, Bataille> batailles = new HashMap<>();
	private HashSet<Salle> sallesExplorees = new HashSet<>();
	private int numeroSalleDecisive;
	
	public Bataille Bataille(Salle s) {
		return batailles.get(s);
	}

	public int getNumeroSalleDecisive() {
		return numeroSalleDecisive;
	}

	@SuppressWarnings("unchecked")
	public void ajouterSalle(ZoneDeCombat zdc,  Monstre<? extends Pouvoir>... monstres) throws ZoneDeCombatNonCompatibleException {
		Salle salle = new Salle(planGrotte.size() + 1, zdc);
		Bataille bataille = new Bataille();
		ArrayList<Salle> sallearray = new ArrayList<Salle>();
		sallearray.add(salle);
		for(Monstre< ? extends Pouvoir> m : monstres ) {
			if (!m.getZoneDeCombat().equals(zdc)) {
				throw new ZoneDeCombatNonCompatibleException("zone de combat incompatible, monstre : "+m.getZoneDeCombat()+" zone : "+zdc);
			}
			m.rejointBataille(bataille);
		}
		planGrotte.put(salle, sallearray);
		batailles.put(salle, bataille);
	}

	public String afficherPlanGrotte() {
		StringBuilder affichage = new StringBuilder();
		for (Map.Entry<Salle, List<Salle>> entry : planGrotte.entrySet()) {
			Salle salle = entry.getKey();
			List<Salle> acces = planGrotte.get(salle);
			affichage.append("La " + salle + ".\nelle possede " + acces.size() + " acces : ");
			for (Salle access : acces) {
				affichage.append(" vers la salle " + access);
			}
			Bataille bataille = batailles.get(salle);
			Camp<Monstre<?>> camp = bataille.getCampMonstres();
			Monstre<?> monstre = camp.selectionner();
			if (camp.nbCombattants() > 1) {
				affichage.append("\n" + camp.nbCombattants() + " monstres de type ");
			} else {
				affichage.append("\nUn monstre de type ");
			}
			affichage.append(monstre.getNom() + " la protege.\n");
			if (salle.getNumSalle() == numeroSalleDecisive) {
				affichage.append("C'est dans cette salle que se trouve la pierre de sang.\n");
			}
			affichage.append("\n");
		}
		return affichage.toString();
	}
	
	public void configurerAcces(int numeroSalle, int... salleAdj) {
		Salle origine = trouverSalle(numeroSalle);
		planGrotte.get(origine);
		ArrayList<Salle> listSalle = new ArrayList<>();
		for (int i : salleAdj) {
			listSalle.add(trouverSalle(i));
		}
		planGrotte.replace(origine, listSalle);
	}
	
	private Salle trouverSalle(int rang) {
		for(Salle s : planGrotte.keySet()) {
			if (s.getNumSalle() == rang) {
				return s;
			}
		}
		return null;
	}

	public void setNumeroSalleDecisive(int numeroSalleDecisive) {
		this.numeroSalleDecisive = numeroSalleDecisive;
	}
	
	public boolean salleDecisive(Salle s) {
		if (s.getNumSalle() == numeroSalleDecisive) {
			return true;
		}else {
			return false;
		}
	}
	
	public Salle premiereSalle() {
		Salle s = planGrotte.keySet().iterator().next();
		sallesExplorees.add(s);
		return s;
	}
	
	public Salle salleSuivante(Salle s) {
		ArrayList<Salle> salleSuivante = new ArrayList<>();
		for(Salle salle :planGrotte.get(s)) {
			if (!sallesExplorees.contains(salle))
				salleSuivante.add(salle);
		}
		if(salleSuivante.isEmpty()) {
			for(Salle suivante : planGrotte.get(s)) {
				salleSuivante.add(suivante);
			}
		}
//		if (salleSuivante.isEmpty()) {
//			for(Salle salleExploree : sallesExplorees) {
//				for(Salle stest : planGrotte.get(salleExploree)) {
//					if (!sallesExplorees.contains(stest))
//						salleSuivante.add(stest);
//				}
//			}
//		}
		double rand = Math.random() * salleSuivante.size();
		Salle suivante = salleSuivante.get((int) rand );
		sallesExplorees.add(suivante);
		return suivante;
	}
}
