package bataille;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import protagoniste.EtreVivant;

public class Camp<T extends EtreVivant> implements Iterable<T>{

	LinkedList<T> list = new LinkedList<>();

	public void ajoute(T etrevivant) {
		boolean contains = false;
		for (int i = 0 ; i < list.size() ; i++) {
			if (etrevivant.getNom() == list.get(i).getNom())
				contains = true;
		}
		if (!contains)
			list.add(etrevivant);
	}

	public void elimine(T etrevivant) {
		list.remove(etrevivant);
	}

	public String toString() {
		return list.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return list.iterator();
	}

	public int nbCombattants() {
		return list.size();
	}

	public T selectionner() {
		Random randomGenerateur = new Random();
		int numeroCombattant = randomGenerateur.nextInt(list.size());
		return list.get(numeroCombattant);
	}
	
	public boolean estVide() {
		return list.isEmpty();
	}


}
