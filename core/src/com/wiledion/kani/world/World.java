package com.wiledion.kani.world;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * World class - Not implemented
 * 
 * @author wiledion
 * 
 */

public class World {

	ArrayList<Perso> listPerso;
	ArrayList<KObject> listObject;
	ArrayList<Place> listPlace;
	Iterator itPerso;
	Iterator itObject;
	Iterator itPlace;

	World() {
		listPerso = new ArrayList<com.wiledion.kani.world.Perso>();
		listObject = new ArrayList<com.wiledion.kani.world.KObject>();
		listPlace = new ArrayList<com.wiledion.kani.world.Place>();
		itPerso = listPerso.iterator();
		itObject = listObject.iterator();
		itPlace = listPlace.iterator();

	}

	public void init() {
		while (itPerso.hasNext())
			((Perso) itPerso.next()).init();

		while (itObject.hasNext())
			((KObject) itObject.next()).init();

		while (itPlace.hasNext())
			((Place) itPlace.next()).init();
	}

	public void update() {
	}

	public void render() {
	}

}
