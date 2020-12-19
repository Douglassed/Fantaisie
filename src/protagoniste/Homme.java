package protagoniste;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.NavigableSet;
import java.util.TreeSet;

import attaque.Arme;
import attaque.KeyArme;
import attaque.Pouvoir;
import bataille.Bataille;

public class Homme extends EtreVivant{

	private EnumMap<ZoneDeCombat,List<Arme>> armes = new EnumMap<>(ZoneDeCombat.class);
	private Arme armeChoisie;

	public Arme getArmeChoisie() {
		return armeChoisie;
	}

	public Homme(String nom) {
		super(70, nom);
		for (ZoneDeCombat zdc : ZoneDeCombat.values()) {
			armes.put(zdc, new ArrayList<>());
		}
	}

	public void rejointBataille(Bataille bataille) {
		bataille.ajouter(this);
		this.bataille = bataille;
	}

	@Override
	public void mourrir() {
		bataille.eliminer(this);
	}

	@Override
	public String toString() {
		return "Homme [getNom()=" + getNom() + ", getForceDeVie()=" + 70 + "]";
	}

	public void ajouterArmes(Arme...armes ) {
		for(Arme ar : armes) {
			for(ZoneDeCombat zdc : ar.getZonesDeCombat()) {
				this.armes.get(zdc).add(ar);
			}
		}
	}

	public void supprimerArme(Arme arme) {
		List<Arme> alArme;
		for(ZoneDeCombat zdc : ZoneDeCombat.values()) {
			alArme = armes.get(zdc);
			alArme.remove(arme);
		}
	}


	public Arme choisirArme(Monstre<? extends Pouvoir> m) {
		List<Arme> alArmeChoisi = armes.get(m.getZoneDeCombat());
		NavigableSet<Arme> armesTriees = new TreeSet<Arme>();
		if(alArmeChoisi.isEmpty())
			return null;
		for(Arme ar : alArmeChoisi) {
			armesTriees.add(ar);
		}
		NavigableSet<Arme> armesAdaptees = armesTriees.tailSet(new KeyArme(m.forceDeVie), true);

		if (!armesAdaptees.isEmpty()) {
			armeChoisie = armesAdaptees.first();
		}else {
			armeChoisie = armesTriees.last();
		}
		return armeChoisie;
	}
	private boolean attaqueReussi() {
		double reussi = Math.random() * 20;
		if (this instanceof Heros) {
			return reussi < 14;
		}else {
			return reussi < 10;
		}
	}
	public boolean attaqueMonstre(Monstre<? extends Pouvoir> m) {
		int damage = 0;
		boolean attaqueReussi = attaqueReussi();
		if (attaqueReussi) {
			damage = this.choisirArme(m).utiliser();
		}
		m.subitAttaque(damage);
		return attaqueReussi;
	}
	
	public void subitAttaque(int pointDeDegat) {
		forceDeVie-= pointDeDegat;
		if (forceDeVie < 1) {
			mourrir();
		}
	}
	
	public boolean resteArmeUtile() {
		boolean armeUtile = true;
		for(ZoneDeCombat zdc : armes.keySet()) {
			for(int i=0; i < armes.get(zdc).size(); i++) {
				Arme a = armes.get(zdc).get(i);
				if (!(a.isOperationel())){
					supprimerArme(a);
					i--;
					armeUtile = false;
				}
			}
		}
		return armeUtile;
	}
}
