package DAO;

import java.util.ArrayList;
import java.util.List;

import Models.Corporations;
import Models.Makers;
import Models.TypeMaker;

public class DAOMakers extends DAOGeneric<Makers, Integer>{

	public DAOMakers() {
		super(Makers.class);
	}
	
	//Genera un nuevo Maker y lo guarda en la bbdd
	public void generateMaker(String name, int maxneightbours, TypeMaker typeMaker) {
		Makers newMaker = new Makers(name, maxneightbours, typeMaker);
		this.Persistir(newMaker);
	}
	
	
	public void generateMaker(String name, int maxneighbours) {
		Makers newMaker = new Makers(name, maxneighbours); 
		System.out.println("Se genera el maker " + newMaker.toString());
		this.Persistir(newMaker);
	}

	/* Genera el tablero con 61 makers. Comprueba qué tipo de casilla es (esquina, borde o interior) y le asigna 
	 * el valor de las casillas vecinas que tiene. Para ello, usa un constructor donde le pasas el maxneighbours */
	public void generarTablero() {
		int makersTotales = 61;
		for(int i = 0; i < makersTotales; i++) {
			if(i < 6)
			{
				this.generateMaker("Maker "+(i+1),3);
			}
			else if(i >= 6 && i < 24)
			{
				this.generateMaker("Maker "+(i+1),4);
			}
			else 
			{
				this.generateMaker("Maker "+(i+1),6);
			}
		}
	}
	/*
	 * Recupera todas las casillas de la base de datos y con un FOREACH comprueba si pertenecen a la corporacion 
	 * pasada, si son de ella las remueve de la lista. Despues envia la lista con solo casillas de esa misma corporacion
	 */
	public List<Makers> getMakersByCoorp(Corporations cor) {
		List<Makers> mk = Llistar();
		List<Makers> mkCoorp = new ArrayList<Makers>();
		System.out.println("aqu estoy GGET BY COORP");
		for (Makers makers : mk) {
			if (makers.getMakerOwner() != null) {
				System.out.println("TENGO COORPORACION " + makers.getName());
				if (makers.getMakerOwner().getName().equals(cor.getName()))
					mkCoorp.add(makers);
			}
		}
		return mkCoorp;
	}
	/*
	 * Recupera todas las casillas y comprueba cuales de ellas no tienen asignada una corporacion, devuelve una lista
	 * con todas las casillas libres de corporaciones. Funciona igual que getMakersByCoorp()
	 */
	public List<Makers> getMarkersNoCoorp() {
		List<Makers> mk = Llistar();
		List<Makers> mkNoCoorp = new ArrayList<Makers>();
		for (Makers makers : mk) {
			if (makers.getMakerOwner() == null) {
				mkNoCoorp.add(makers);
			}
		}
		return mkNoCoorp;
	}
}
