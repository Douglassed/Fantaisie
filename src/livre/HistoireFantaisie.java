package livre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import attaque.Arc;
import attaque.Arme;
import attaque.Boomerang;
import attaque.BouleDeFeu;
import attaque.Eclair;
import attaque.Epee;
import attaque.Feu;
import attaque.Glace;
import attaque.Griffe;
import attaque.LameAcier;
import attaque.LancePierre;
import attaque.Lave;
import attaque.Morsure;
import attaque.PicsDeGlace;
import attaque.Pouvoir;
import attaque.Tornade;
import attaque.Tranchant;
import bataille.Bataille;
import bataille.Camp;
import bataille.Grotte;
import bataille.GroupeHommes;
import bataille.Salle;
import protagoniste.Domaine;
import protagoniste.Heros;
import protagoniste.Homme;
import protagoniste.Monstre;
import protagoniste.ZoneDeCombat;
import protagoniste.ZoneDeCombatNonCompatibleException;

public class HistoireFantaisie {
	protected Livre livre;

	private Map<String, List<Monstre<? extends Pouvoir>>> monstres = new HashMap<>();
	private Map<String, Homme> compagnie = new HashMap<>();
	private GroupeHommes groupeHommes = new GroupeHommes();
	private Salle salle;
	private Grotte grotte = new Grotte();

	public void initialisationParDefaut(HistoireFantaisie histoireFantaisie) {
		// Affectation de la sortie (ecran ou fichier)
		histoireFantaisie.configurationSortieDefaut();
		// Création des monstres
		histoireFantaisie.creationMonstresDefaut();
		// création de la grotte
		histoireFantaisie.creationGrotteDefaut();
		// configuration de la grotte
		histoireFantaisie.configurationGrotteDefaut();
		// Création du groupe d'hommes armés
		histoireFantaisie.creationCompagnieDefaut();
		histoireFantaisie.affectationArmesDefaut();
	}

	private void configurationSortieDefaut() {
		livre = new Ecran();
	}

	public void creationMonstresDefaut() {
		List<Monstre<?>> listeDragotenebre = new ArrayList<>();
		listeDragotenebre.add(new Monstre<Feu>("dragotenebre", 200, ZoneDeCombat.AERIEN, Domaine.FEU, new BouleDeFeu(4),
				new Lave(1), new Eclair(3)));
		monstres.put("dragotenebre", listeDragotenebre);

		List<Monstre<?>> listeVampirien = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			listeVampirien.add(new Monstre<Tranchant>("vampirien" + i, 10, ZoneDeCombat.AERIEN, Domaine.TRANCHANT,
					new Morsure(10)));
		}
		monstres.put("vampirien", listeVampirien);

		List<Monstre<?>> listeMarinsangant = new ArrayList<>();
		listeMarinsangant.add(new Monstre<Glace>("marinsangant", 150, ZoneDeCombat.AQUATIQUE, Domaine.GLACE,
				new PicsDeGlace(10), new Tornade(1)));
		monstres.put("marinsangant", listeMarinsangant);

		List<Monstre<?>> listeGuillotimort = new ArrayList<>();
		listeGuillotimort.add(new Monstre<Tranchant>("guillotimort", 80, ZoneDeCombat.TERRESTRE, Domaine.TRANCHANT,
				new LameAcier(10), new Griffe()));
		monstres.put("guillotimort", listeGuillotimort);

		List<Monstre<?>> listeGivrogolem = new ArrayList<>();
		listeGivrogolem.add(new Monstre<Glace>("givrogolem", 200, ZoneDeCombat.TERRESTRE, Domaine.GLACE,
				new PicsDeGlace(10), new Tornade(1)));
		monstres.put("givrogolem", listeGivrogolem);

		List<Monstre<?>> listeAqualave = new ArrayList<>();
		listeAqualave.add(new Monstre<Feu>("aqualave", 30, ZoneDeCombat.AQUATIQUE, Domaine.FEU, new Lave(5)));
		monstres.put("aqualave", listeAqualave);

		List<Monstre<?>> listeRequispectre = new ArrayList<>();
		listeRequispectre.add(
				new Monstre<Tranchant>("requispectre", 200, ZoneDeCombat.AQUATIQUE, Domaine.TRANCHANT, new Griffe()));
		monstres.put("requispectre", listeRequispectre);

		List<Monstre<?>> listeSoufflemort = new ArrayList<>();
		listeSoufflemort
		.add(new Monstre<Glace>("soufflemort", 120, ZoneDeCombat.AERIEN, Domaine.GLACE, new Tornade(8)));
		monstres.put("soufflemort", listeSoufflemort);

		List<Monstre<?>> listeCramombre = new ArrayList<>();
		listeCramombre.add(new Monstre<Feu>("cramombre", 80, ZoneDeCombat.TERRESTRE, Domaine.FEU, new BouleDeFeu(2),
				new Lave(1), new Eclair(1)));
		monstres.put("cramombre", listeCramombre);
	}

	public void creationGrotteDefaut() {
		ajouterSalle(ZoneDeCombat.TERRESTRE, "guillotimort");
		ajouterSalle(ZoneDeCombat.AERIEN, "dragotenebre");
		ajouterSalle(ZoneDeCombat.TERRESTRE, "cramombre");
		ajouterSalle(ZoneDeCombat.TERRESTRE, "givrogolem");
		ajouterSalle(ZoneDeCombat.AERIEN, "vampirien");
		ajouterSalle(ZoneDeCombat.AQUATIQUE, "aqualave");
		ajouterSalle(ZoneDeCombat.AQUATIQUE, "marinsangant");
		ajouterSalle(ZoneDeCombat.AQUATIQUE, "requispectre");
		ajouterSalle(ZoneDeCombat.AERIEN, "soufflemort");
	}

	private void ajouterSalle(ZoneDeCombat zoneDeCombat, String nomMonstre) {
		List<Monstre<? extends Pouvoir>> listeMonstres = monstres.get(nomMonstre);
		int tailleTableau = listeMonstres.size();
		Monstre<? extends Pouvoir>[] tableauMonstre = new Monstre<?>[tailleTableau];
		tableauMonstre = listeMonstres.toArray(tableauMonstre);
		try {
			grotte.ajouterSalle(zoneDeCombat, tableauMonstre);
		} catch (ZoneDeCombatNonCompatibleException e) {
			e.printStackTrace();
		}
	}

	public void configurationGrotteDefaut() {
		grotte.configurerAcces(1, 2, 6);
		grotte.configurerAcces(2, 1, 3, 5);
		grotte.configurerAcces(3, 2, 4);
		grotte.configurerAcces(4, 3, 5, 9);
		grotte.configurerAcces(5, 2, 4, 6, 8);
		grotte.configurerAcces(6, 1, 5, 7);
		grotte.configurerAcces(7, 6, 8);
		grotte.configurerAcces(8, 5, 7, 9);
		grotte.configurerAcces(9, 4, 8);

		grotte.setNumeroSalleDecisive(9);
		salle = grotte.premiereSalle();
	}

	public void creationCompagnieDefaut() {
		Homme thomas = new Homme("Thomas");
		compagnie.put("thomas", thomas);
		Homme louis = new Homme("Louis");
		compagnie.put("louis", louis);
		Heros arthur = new Heros("Arthur");
		compagnie.put("arthur", arthur);
		Heros roland = new Heros("Roland");
		compagnie.put("roland", roland);

		groupeHommes.ajouterHommes(thomas, louis, arthur, roland);
	}

	public void affectationArmesDefaut() {
		Homme thomas = compagnie.get("thomas");
		thomas.ajouterArmes(new LancePierre(), new Boomerang());
		Homme louis = compagnie.get("louis");
		louis.ajouterArmes(new LancePierre(), new Arc(5));
		Homme arthur = compagnie.get("arthur");
		arthur.ajouterArmes(new Epee("excalibur"), new Arc(8));
		Homme roland = compagnie.get("roland");
		roland.ajouterArmes(new Epee("durandal"), new Arc(3));
	}
	
	public String afficherCampMonstres(Camp<Monstre<? extends Pouvoir>> campMonstres){
		Monstre<? extends Pouvoir> monstre = campMonstres.iterator().next();
		String chaine = "";
		String nomMonstre = monstre.getNom();
		int nombreMonstre = campMonstres.nbCombattants();
		if (nombreMonstre > 1) {
			chaine+= nombreMonstre + " monstres" + nomMonstre;
		}else {
			chaine+=nomMonstre + ", monstre";
		}
		chaine+= " de type "+ monstre.getDomaine();
		return chaine;
	}
	
	private String affichageHommes(List<Homme> groupeHumain) {
		String chaine = "";
		for (Homme h : groupeHumain) {
			chaine+="\n-"+h.getNom()+" possédant "+h.getForceDeVie()+" points de force de vie et armé avec "+h.getArmeChoisie();
		}
		return chaine;
		
	}
	
	private boolean debutCombat(Camp<Monstre<? extends Pouvoir>> campMonstres, List<Homme> groupeHumain, Bataille bataille) {
		boolean combatPossible = true;
		int nombreMonstres = campMonstres.nbCombattants();
		for (Monstre<? extends Pouvoir> m : campMonstres) {
			m.entreEnCombat();
		}
		while (!groupeHumain.isEmpty() && !campMonstres.estVide()) {
			hommeAttaque(campMonstres, groupeHumain);
			if (!groupeHumain.isEmpty() && !campMonstres.estVide()) {
				monstreAttaque(campMonstres, groupeHumain);
			}
		}
		if (campMonstres.estVide()) {
			if (nombreMonstres > 1) {
				System.out.println("\nTous les monstres sont mort.");
			}else {
				System.out.println("\nLe monstre est mort");
			}
		}else {
			System.out.println("\nMalheureuseument tous les hommes engagés dans la battaille sont morts.");
			poursuiteDuCombat(campMonstres, bataille);
			combatPossible = campMonstres.estVide();
		}
		return combatPossible;
	}
	private void hommeAttaque(Camp<Monstre<? extends Pouvoir>> campMonstres, List<Homme> groupeHumain) {
		Homme h = selectionHomme(groupeHumain);
		Monstre<? extends Pouvoir> m = campMonstres.selectionner();
		Arme a = h.getArmeChoisie();
		boolean attaqueReussi = h.attaqueMonstre(m);
		System.out.println("\n"+h.getNom()+" attaque "+m.getNom()+" avec son "+h.getArmeChoisie().getNom());
		if (attaqueReussi){
			System.out.println("et il le touche.");
		}else {
			System.out.println("mais il le manque de peu");
		}
		if (!a.isOperationel()) {
			System.out.println("\n"+h.getNom()+" ne peut plus utiliser son "+a.getNom());
			if (!h.resteArmeUtile()) {
				System.out.println("\nN'ayant plus d'arme adaptée il quitte le champs de bataille");
				groupeHumain.remove(h);
			}else {
				h.choisirArme(m);
			}
		}
	}

	private Homme selectionHomme(List<Homme> groupeHumain) {
		int rand = (int)(Math.random() * groupeHumain.size());
		return groupeHumain.get(rand);
	}

	private void monstreAttaque(Camp<Monstre<? extends Pouvoir>> campMonstres, List<Homme> groupeHumain) {
		Monstre<? extends Pouvoir> m = campMonstres.selectionner();
		Homme h = selectionHomme(groupeHumain);
		Pouvoir pouvoir = m.attaque();
		if (pouvoir != null) {
			boolean attaqueReussi = m.attaqueHomme(h, pouvoir);
			System.out.println("\n"+m.getNom()+" attaque "+h.getNom()+" avec "+pouvoir.getNom());
			String chaine = pouvoir.afficherPouvoir();
			if (attaqueReussi) {
				System.out.println("et le touche.");
				System.out.println("\n"+chaine);
				if (!h.enVie()) {
					System.out.println("\nMalheureusement "+h.getNom()+" ne survit pas à ses blessures.");
					groupeHumain.remove(h);
					groupeHommes.supprimerHomme(h);
				}
			}else {
				System.out.println("mais heureseument il le loupe.");
				System.out.println("\n"+chaine);
			}
		}
	}

	private void poursuiteDuCombat(Camp<Monstre<? extends Pouvoir>> campMonstres, Bataille bataille) {
		if (!groupeHommes.estVide()) {
			List<Homme> groupeCombattants = groupeHommes.choixCampHomme(bataille);
			if (!groupeCombattants.isEmpty()) {
				System.out.println(", mais ce n'était pas les seuls du groupe des humains à être armés pour combattre.");
				System.out.println(affichageHommes(groupeCombattants));
				System.out.println("\nMalheureseument le monstre a pu régénérer ses pouvoirs");
				Monstre<? extends Pouvoir> m = campMonstres.selectionner();
				System.out.println("\nLe combat contre "+m.getNom()+" continue.");
				debutCombat(campMonstres, groupeCombattants, bataille);
			}else {
				System.out.println("et aucun autre homme n'est armé pour combattre.");
				System.out.println("\nLes monstres ont gagné cette bataille mais les survivants reviendront combattre avec d'autres hommes courageux.");
			}
		}else {
			System.out.println("\nAucun des hommes qui sont entrés dans la grotte n'ont survécu.\nIl faudra attendre logntemps avant de pouvoir reformer un groupe d'humain aussi courageux. Les monstres ont gagné.");
		}
	}

	public boolean affrontement() {
		Bataille bataille = grotte.Bataille(salle);
		Camp<Monstre<? extends Pouvoir>> campMonstres = bataille.getCampMonstres();
		boolean combatPossilbe = true;
		boolean campMonstresVides = campMonstres.estVide();
		if (campMonstresVides) {
			System.out.println("\nLes humains sont déjà passés dans cette salle, il n'y a plus de monstre.");
		}else {
			String chaine = afficherCampMonstres(campMonstres);
			System.out.println("\nLa grotte est gardée par "+ chaine);
		}
		List<Homme> groupeHumain = groupeHommes.choixCampHomme(bataille);
		System.out.println(groupeHumain);
		if (groupeHumain.isEmpty()) {
			System.out.println("\nAucun membre du groupe ne possède les armes pour lutter contre ce monstre.\nLa quête est finie, les hommes ont perdu.");
		}else {
			System.out.println("\nLes hommes les mieux préparés pour ce combat sont :"+affichageHommes(groupeHumain)+"\nCe sera donc eux qui se battront contre le monstre.");
		}
		combatPossilbe = debutCombat(campMonstres, groupeHumain, bataille);
		return combatPossilbe;
	}

	public void ecrireHistoire() {
		boolean combatPossible = true;
		boolean salleDecisive = grotte.salleDecisive(salle);
		int numSalle;
		while(combatPossible && !salleDecisive) {
			numSalle = salle.getNumSalle();
			ZoneDeCombat zdc = salle.getZoneDeCombat();
			
			System.out.println("\nLe groupe entre dans la salle n°" + numSalle + " qui est propice au combat " + zdc);
			combatPossible = affrontement();
			boolean resteCombattans = groupeHommes.resteCombattant();
			Bataille bataille = grotte.Bataille(salle);
			Camp<Monstre<? extends Pouvoir>> campMonstres = bataille.getCampMonstres();
			boolean monstreMort = campMonstres.estVide();
			salleDecisive = grotte.salleDecisive(salle);
			
			if (monstreMort && resteCombattans && salleDecisive) {
				System.out.println("les humains peuvent détruire la pierre de sang. Les monstres privés de cette énergie vont tous mourrir.\nCes hommes courageux, qui pour certains seront morts au combat, auront sauvé l'humanité.");
			}
			if (combatPossible && !salleDecisive) {
				salle = grotte.salleSuivante(salle);
				System.out.println("\n\nLe groupe peut poursuivre sa quête.");
			}
		}
	}

	public static void main(String[] args) {

		HistoireFantaisie histoireFantaisie = new HistoireFantaisie();
		// Initialisation par defaut
		histoireFantaisie.initialisationParDefaut(histoireFantaisie);
		// Début de l'affrontement
		histoireFantaisie.ecrireHistoire();

	}

}
